package edu.washington.manjic.quizdroid

import android.app.Activity
import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.SimpleAdapter


class MainActivity : AppCompatActivity() {

//    private val topics = listOf("Science!", "Mathematics", "Marvel Super Heroes")
    private val instance = QuizApp.getSingletonInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        builder.show()

        return super.onOptionsItemSelected(item)
    }

}
