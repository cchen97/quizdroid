package edu.washington.manjic.quizdroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import edu.washington.manjic.quizdroid.R.id.listView_topics
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.R
import android.widget.FrameLayout


class MainActivity : AppCompatActivity() {

    private val topics = listOf("Science!", "Mathematics", "Marvel Super Heroes")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var topicListView = findViewById<ListView>(listView_topics)
        var adapter = ArrayAdapter<String>(this, R.layout.simple_list_item_1, topics)
        topicListView.adapter = adapter

        topicListView.setOnItemClickListener { _, _ , position, id ->
            var currentView: ListView = findViewById(listView_topics)
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

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val MenuItem = menu?.findItem(R.id.preferenceContainer)
        val rootView = MenuItem?.actionView as FrameLayout
        return super.onPrepareOptionsMenu(menu)
    }
}
