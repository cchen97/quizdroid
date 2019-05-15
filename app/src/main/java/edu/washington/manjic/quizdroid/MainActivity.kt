package edu.washington.manjic.quizdroid

import android.app.Activity
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.content.Context
import android.content.IntentFilter
import android.support.constraint.ConstraintLayout
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.webkit.URLUtil
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.SimpleAdapter
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_preferences.view.*
import org.json.JSONArray
import org.json.JSONException
import java.io.UnsupportedEncodingException
import java.net.URL
import java.util.ArrayList


class MainActivity : AppCompatActivity() {

//    private val topics = listOf("Science!", "Mathematics", "Marvel Super Heroes")
    private val instance = QuizApp.getSingletonInstance()
    private var urlString = "https://tednewardsandbox.site44.com/questions.json"
    private var alarmManager: AlarmManager? = null
    private var topicList =  ArrayList<Topic>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val filter = IntentFilter()
        filter.addAction(QuizApp.BROADCAST)
        val receiver = BroadcastReceiver()
        registerReceiver(receiver, filter)
        val intent = Intent(QuizApp.BROADCAST)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager!!.setRepeating(
            AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
            (1 * 60 * 1000),
            pendingIntent
        )
        downloadData(this.urlString)

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

        lv.setOnItemClickListener { _, _, position, _ ->
            val item = listOfTopicNames[position]

            val intent = Intent(this, ControllerActivity::class.java)
                .putExtra(ControllerActivity.TOPIC_NAME, item)
            startActivity(intent)
        }

    }

    fun parseData (array: JSONArray) : ArrayList<Topic> {
        var topics = ArrayList<Topic>()
        val array = array
        for (i in 0 .. array.length()-1) {
            val quizItem = array.getJSONObject(i)
            val titleTopic = quizItem.getString("title")
            Log.i("dataaaa", titleTopic)
            val desc = quizItem.getString("desc")

            val questionsList = quizItem.getJSONArray("questions")
            val quizList = ArrayList<Quiz>()
            for (j in 0..questionsList.length()-1){
                val questionObject = questionsList.getJSONObject(j)
                val text = questionObject.getString("text")
                val answer =  questionObject.getString("answer").toInt()
                val answerList = questionObject.getJSONArray("answers")
                val quiz = Quiz(text, answerList.get(0).toString(), answerList.get(1).toString(), answerList.get(2).toString(),answerList.get(3).toString(),answer)
                quizList.add(quiz)
            }
            val Topic = Topic(titleTopic,desc," ", quizList)
            topics.add(Topic)
            Log.i("test", "finish download")
        }
        return topics
    }

    fun downloadData(url:String){
        val request = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    val array = response
                    topicList = parseData(array)
                    findViewById<ProgressBar>(R.id.progress_loader).setVisibility(View.GONE)
                    findViewById<ConstraintLayout>(R.id.listContainer).setVisibility(View.VISIBLE)

                    renderListView(topicList.toList())
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }, Response.ErrorListener { error -> Log.e("download data", error.toString()) })

        val queue = Volley.newRequestQueue(this)
        queue.add(request)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.preference, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val inflater: LayoutInflater = this.getLayoutInflater()
        val rootView = inflater.inflate(R.layout.activity_preferences, null)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Input your own quiz").setView(rootView)
        val alert = builder.show()


//        builder.show()

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
            alert.cancel()
        }

        return super.onOptionsItemSelected(item)
    }
}
