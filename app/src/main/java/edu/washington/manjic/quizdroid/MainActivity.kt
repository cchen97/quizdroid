package edu.washington.manjic.quizdroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import edu.washington.manjic.quizdroid.R.id.listView_topics
import android.content.Intent

class MainActivity : AppCompatActivity() {
    val topics = listOf("Math", "Physics", "Marvel Super Heroes")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var listView = findViewById<ListView>(listView_topics)
        var arrayAdapter= ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, topics)

        listView.adapter = arrayAdapter

        listView.setOnItemClickListener { parent, view, position, id ->
            var currListView: ListView = findViewById(R.id.listView_topics)
            var item = currListView.getItemAtPosition(position)

            val intent = Intent(this, SummaryActivity::class.java)
                .putExtra(SummaryActivity.TOPIC, item.toString())
                .putExtra(SummaryActivity.DESCRIPTION, item.toString())
                .putExtra(SummaryActivity.TOPIC_NUM, position)


            startActivity(intent)
        }
    }

    companion object {
        const val TAG: String = "ActivityMain"
    }
}
