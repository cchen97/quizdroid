package edu.washington.manjic.quizdroid

import android.app.Activity
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Environment
import android.provider.Settings
import android.support.constraint.ConstraintLayout
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.webkit.URLUtil
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_preferences.view.*
import org.json.JSONArray
import org.json.JSONException
import java.io.File
import java.io.FileOutputStream
import java.io.UnsupportedEncodingException
import java.net.URL
import java.util.ArrayList


class MainActivity : AppCompatActivity() {

//    private val topics = listOf("Science!", "Mathematics", "Marvel Super Heroes")
    private val instance = QuizApp.getSingletonInstance()
    var urlString = ""
    private var alarmManager: AlarmManager? = null
    private var topicList =  ArrayList<Topic>()
    private lateinit var sharePreference:SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val retryBtn = findViewById<Button>(R.id.retry_btn)
        retryBtn.visibility = View.GONE
        retryBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        if (isAirplaneModeOn(this)){
            findViewById<ProgressBar>(R.id.progress_loader).visibility = View.GONE
            Toast.makeText(this, "You don't have access to the internet!", Toast.LENGTH_SHORT).show()
            val builder = AlertDialog.Builder(this)
            builder.setMessage("It looks like Airplane Mode is on! Would you like to go to Settings?")
                .setTitle("Uh oh!")
                .setNegativeButton("No"
                ) { _, _ ->
                    retryBtn.visibility = View.VISIBLE
                }
                .setPositiveButton("Yes"
                ) { _, _ ->
                    val intent = Intent(android.provider.Settings.ACTION_AIRPLANE_MODE_SETTINGS)
                    startActivity(intent)
                }
            val dialog = builder.create()
            dialog.show()
        }else if(!isNetworkAvailable()){
            findViewById<ProgressBar>(R.id.progress_loader).visibility = View.GONE
            Toast.makeText(this, "You don't have access to the internet!", Toast.LENGTH_SHORT).show()
            retryBtn.visibility = View.VISIBLE
        }else {
            sharePreference =  this.getSharedPreferences(
                instance.USER_PREF_KEY, Context.MODE_PRIVATE
            )

            urlString = sharePreference.getString("Json_Link", "")!!
            broadcastLink()
        }

    }

    private fun broadcastLink (){

        alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager


        val filter = IntentFilter()
        filter.addAction(BROADCAST)
        val receiver = BroadcastReceiver()
        registerReceiver(receiver, filter)
        val intent = Intent(BROADCAST)
            .putExtra("link", this.urlString)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager!!.setRepeating(
            AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
            (10 * 60 * 1000),
            pendingIntent
        )
    }

    private inner class BroadcastReceiver : android.content.BroadcastReceiver()  {
        val LINK = "edu.washington.manjic.quizdroid.LINK"
        override fun onReceive(context: Context, intent: Intent) {

            when (intent.action) {
                BROADCAST -> {
                    var link = intent.extras["link"] as String
                    Log.i("zr",link)
                    downloadData(link)
                    Toast.makeText(context, "Downloading... $link", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun renderListView(listOfTopics:List<Topic>){
        Log.i("View", "Rendering LIST VIEW")
        val listOfTopicNames = listOfTopics.map { it ->
            it.topicTitle
        }
        val listOfTopicNamesToDescriptions = listOfTopics.map { it ->
            arrayOf(it.topicTitle, it.topicShortDescription)
        }
        val list: List<HashMap<String, String>> = listOfTopicNamesToDescriptions.map { it ->
            val item: HashMap<String, String> = HashMap()
            item["line1"] = it[0]
            item["line2"] = it[1]
            item
        }

        Log.i("get urll", "render UI")
        val lv = findViewById<ListView>(R.id.listView_topics)
        val simpleAdapter = SimpleAdapter(this,
            list, R.layout.two_line_list_item, arrayOf("line1", "line2"),
            intArrayOf(R.id.line_a, R.id.line_b))
        lv.adapter = simpleAdapter
        Toast.makeText(this, "Download Success！Enjoy the quiz! ", Toast.LENGTH_SHORT).show()
        lv.setOnItemClickListener { _, _, position, _ ->
            val item = listOfTopicNames[position]

            val intent = Intent(this, ControllerActivity::class.java)
                .putExtra(ControllerActivity.TOPIC_NAME, item)
            startActivity(intent)
        }

    }



    fun downloadData(url:String){
        val request = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    val array = response
                    topicList = instance.repository.parseData(array)
                    findViewById<ProgressBar>(R.id.progress_loader).visibility = View.GONE
                    findViewById<ConstraintLayout>(R.id.listContainer).visibility = View.VISIBLE
                    val gson = Gson()
                    var jsonString:String = gson.toJson(topicList)

                    openFileOutput("questions.json", Context.MODE_PRIVATE).use {
                        it.write(jsonString.toByteArray())
                    }

                    renderListView(topicList.toList())
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Download failed. Please try again! ", Toast.LENGTH_SHORT).show()

                }

            }, Response.ErrorListener { error -> Log.e("download data", error.toString())
                Toast.makeText(this, "Download failed. Please try again! ", Toast.LENGTH_SHORT).show()
            })

        val queue = Volley.newRequestQueue(this)
        queue.add(request)
        broadcastLink ()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.preference, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val inflater: LayoutInflater = this.layoutInflater
        val rootView = inflater.inflate(R.layout.activity_preferences, null)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Input your own quiz").setView(rootView)
        val alert = builder.show()


        rootView.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.i("change urll", s.toString())
                if (URLUtil.isValidUrl(s.toString())) {
                    urlString = s.toString()
                }
            }
            override fun afterTextChanged(s: Editable) {}
        })

        rootView.comfirmInput.setOnClickListener {
            Log.i("rerender urll", urlString)
            downloadData(this.urlString)
            sharePreference.edit().putString("Json_Link", urlString).apply()
            alert.cancel()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    private fun isAirplaneModeOn(context: Context): Boolean {

        return Settings.System.getInt(
            context.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON, 0
        ) !== 0
    }

    companion object{
        const val BROADCAST = "edu.washington.manjic.quizBoard.BROADCAST"
    }
}
