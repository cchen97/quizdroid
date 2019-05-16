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
        Log.i("list", topicName)
        Log.i("list", _topics.toString())
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
        _topics = topics
        return topics
    }
//    fun loadData(json: String){
//        val array = JSONArray(json)
//        var topicList = ArrayList<Topic>()
//        for (i in 0 .. array.length()-1) {
//            val quizItem = array.getJSONObject(i)
//            val titleTopic = quizItem.getString("title")
//
//            val desc = quizItem.getString("desc")
//
//            val questionsList = quizItem.getJSONArray("questions")
//            val quizList = ArrayList<Quiz>()
//            for (j in 0..questionsList.length()-1){
//                val questionObject = questionsList.getJSONObject(j)
//                val text = questionObject.getString("text")
//                val answer =  questionObject.getString("answer").toInt()
//                val answerList = questionObject.getJSONArray("answers")
//                val quiz = Quiz(text, answerList.get(0).toString(), answerList.get(1).toString(), answerList.get(2).toString(),answerList.get(3).toString(),answer)
//                quizList.add(quiz)
//            }
//            val Topic = Topic(titleTopic,desc," ", quizList)
//            topicList.add(Topic)
//        }
//        _topics = topicList
//    }

//    fun DownloadData(url: String, context:Context){
//        var urlString = ""
//
//        try {
//            urlString = "https://tednewardsandbox.site44.com/questions.json"
//            Log.v("TEST", urlString);
//        } catch (uee: UnsupportedEncodingException) {
//            Log.e("downloading Data", uee.toString())
//            return
//        }
//
//        val request = JsonArrayRequest(Request.Method.GET, urlString, null,
//            Response.Listener { response ->
//                var topicList = ArrayList<Topic>()
//
//                try {
//                    //parse the JSON results
//                    val array = response
////                    val array =
//                    for (i in 0 .. array.length()-1) {
//                        val quizItem = array.getJSONObject(i)
//                        val titleTopic = quizItem.getString("title")
//                        Log.i("dataaaa", titleTopic)
//                        val desc = quizItem.getString("desc")
//
//                        val questionsList = quizItem.getJSONArray("questions")
//                        val quizList = ArrayList<Quiz>()
//                        for (j in 0..questionsList.length()-1){
//                            val questionObject = questionsList.getJSONObject(j)
//                            val text = questionObject.getString("text")
//                            val answer =  questionObject.getString("answer").toInt()
//                            val answerList = questionObject.getJSONArray("answers")
//                            val quiz = Quiz(text, answerList.get(0).toString(), answerList.get(1).toString(), answerList.get(2).toString(),answerList.get(3).toString(),answer)
//                            quizList.add(quiz)
//                        }
//                        val Topic = Topic(titleTopic,desc," ", quizList)
//                        topicList.add(Topic)
//                        _topics = topicList
//                        Log.i("test", "finish download")
//
//
//                    }
//                } catch (e: JSONException) {
//                    e.printStackTrace()
//                }
//
//            }, Response.ErrorListener { error -> Log.e("download data", error.toString()) })
//
//        val queue = Volley.newRequestQueue(context)
//        queue.add(request)
//    }


    init{


//        val topic1 = Topic("Math",
//            "Yo its Math.",
//            "Trivia and tricky equations.")
//        topic1.addQuestion(Quiz(
//            "Sine, cosine, and what?",
//            "Quotient",
//            "Tangent",
//            "Agent",
//            "Potient",
//            2
//        ))
//        topic1.addQuestion(Quiz(
//            "What is 36 times 7?",
//            "256",
//            "264",
//            "246",
//            "252",
//            4
//        ))
//        topic1.addQuestion(Quiz(
//            "What does (9 + 9 / 3) resolve to?",
//            "6",
//            "12",
//            "9",
//            "15",
//            2
//        ))
//        val topic2 = Topic("Physics",
//            "Hated physics",
//            "Theories, laws & Physics history")
//        topic2.addQuestion(Quiz(
//            "What was Newton's first law?",
//            "The law of motion",
//            "The law of gravity",
//            "The law of movement",
//            "The law of order",
//            2
//        ))
//        topic2.addQuestion(Quiz(
//            "What is the force that opposes the relative motion of two bodies that are in contact?",
//            "Normal Force",
//            "Magnetism",
//            "Friction",
//            "Gravity",
//            3
//        ))
//        val topic3 = Topic("Marvel Super Heroes",
//            "End game was nice",
//            "MCU & Marvel Comics trivia")
//        topic3.addQuestion(Quiz(
//            "What powers Iron Man's suit?",
//            "He was bit by a robot",
//            "An Arc Reactor",
//            "A rare Unobtainium core",
//            "Pure Russian Titanium",
//            2
//        ))
//        topic3.addQuestion(Quiz(
//            "In the comics, who else can lift Thor's hammer?",
//            "Wade Wilson",
//            "Miles Morales",
//            "Stephen Strange",
//            "Natasha Romanoff",
//            4
//        ))
//        _topics.add(topic1)
//        _topics.add(topic2)
//        _topics.add(topic3)
    }
}