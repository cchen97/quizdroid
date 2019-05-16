package edu.washington.manjic.quizdroid

import android.app.Activity
import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.UnsupportedEncodingException
import java.util.ArrayList
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.JsonObject




class TopicRepository() {
    private  var _topics = mutableListOf<Topic>()

    fun getTopic(topicName: String): Topic {
        val res = (_topics.filter { it.topicTitle == topicName })
        return res.first()
    }

    fun getTopics(): List<Topic> {
        return _topics.toList()
    }

    fun parseData(array: JSONArray): ArrayList<Topic>{


        var topics = ArrayList<Topic>()
        val array = array
        for (i in 0 .. array.length()-1) {
            val quizItem = array.getJSONObject(i)
            val titleTopic = quizItem.getString("title")
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
        }
        _topics = topics
        return topics
    }
}