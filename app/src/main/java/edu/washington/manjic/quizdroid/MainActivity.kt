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

    private val topics = listOf("Math", "Physics", "Marvel Super Heroes")

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



        val  inputStream = application.assets.open("questions.json")
        val json = inputStream.bufferedReader().use{it.readText()}
        val array = JSONArray(json)

        var topicList = ArrayList<Topic>()


        for (i in 0 until array.length()-1) {
            val quizItem = array.getJSONObject(i)
            val titleTopic = quizItem.getString("title")
            val desc = quizItem.getString("desc")
            val questionsList = quizItem.getJSONArray("questions")
            val quizList = ArrayList<Quiz>()
            for (j in 0 until questionsList.length()-1){
                val questionObject = questionsList.getJSONObject(i)
                val text = questionObject.getString("text")
                val answer =  questionObject.getString("answer").toInt()
                val answerList = questionObject.getJSONArray("answers")
                val quiz = Quiz(text, answerList.get(0).toString(), answerList.get(1).toString(), answerList.get(2).toString(),answerList.get(3).toString(),answer)
                quizList.add(quiz)
            }
            val Topic = Topic(titleTopic,desc," ", quizList)
            topicList.add(Topic)
        }
    }
}
