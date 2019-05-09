package edu.washington.manjic.quizdroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import edu.washington.manjic.quizdroid.R.id.listView_topics
import android.content.Intent
import android.util.Log
import java.io.InputStream
import com.google.gson.Gson;
import org.json.JSONArray
import java.util.ArrayList


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
}
