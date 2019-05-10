package edu.washington.manjic.quizdroid

import android.app.Activity
import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import edu.washington.manjic.quizdroid.R.id.listView_topics
import android.content.Intent
import android.content.SharedPreferences
import android.content.Context
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.LinearLayout


class MainActivity : AppCompatActivity() {

    private val topics = listOf("Science!", "Mathematics", "Marvel Super Heroes")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var topicListView = findViewById<ListView>(listView_topics)
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, topics)
        topicListView.adapter = adapter

        topicListView.setOnItemClickListener { _, _ , position, id ->
            var currentView: ListView = findViewById(R.id.listView_topics)
            var chosenItem = currentView.getItemAtPosition(position)
            val intent = Intent(this, ControllerActivity::class.java)
                .putExtra(ControllerActivity.TOPIC_NAME, chosenItem.toString())
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.preference, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val inflater : LayoutInflater = this.getLayoutInflater()
        val rootView  = inflater.inflate(R.layout.activity_preferences ,null)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Input your own quiz").setView(rootView)
        builder.show()


        return super.onOptionsItemSelected(item)
    }
}
