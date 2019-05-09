package edu.washington.manjic.quizdroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*


class MainActivity : AppCompatActivity() {

//    private val topics = listOf("Science!", "Mathematics", "Marvel Super Heroes")
    private val instance = QuizApp.getSingletonInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Variables
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
//        val topicDataStored = sharedPref.contains(getString(R.string.sharedPrefs_storedTopicData))
//        val emptyTopicDataString = TopicData("","", listOf(QuestionData("", -1, arrayOf("","","","")))).toString()

        // Check if user preferences have been initialized.
        val userPrefsStored = sharedPref.contains(getString(R.string.sharedPrefs_storedUserPrefs_url))
                && sharedPref.contains(getString(R.string.sharedPrefs_storedUserPrefs_refresh))

        // If no stored user preferences were found, set them to the default.
        if (!userPrefsStored) {
            try {
                with(sharedPref.edit()) {
                    putString(getString(R.string.sharedPrefs_storedUserPrefs_url), "http://tednewardsandbox.site44.com/questions.json")
                    putString(getString(R.string.sharedPrefs_storedUserPrefs_refresh), "5")
                    commit()
                }
            }
            catch(e: Exception) {
                Log.e(TAG, e.toString())
            }
        }

        val listOfTopics = instance.repository.getTopics()
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

        val lv: ListView = findViewById(R.id.listView_topics)
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


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.preference, menu)
//        val item = menu.findItem(R.id.preferenceContainer)
//        item.actionView.showContextMenu()

        return true
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.preferenceContainer -> {
            val intent = Intent(applicationContext, PreferencesActivity::class.java)
            startActivity(intent)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
            false
        }
    }

//    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
//        R.id.preferenceContainer -> {
//            val intent = Intent(applicationContext, PreferencesActivity::class.java)
//            startActivity(intent)
//            true
//        }
//
//        else -> {
//            super.onOptionsItemSelected(item)
//        }
//    }
companion object {
    const val TAG: String = "MainActivity"
    const val URL_DOWNLOAD_REQUEST_CODE = 42
}
}
