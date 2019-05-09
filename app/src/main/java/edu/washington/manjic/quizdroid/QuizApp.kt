package edu.washington.manjic.quizdroid

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log


class QuizApp : Application(){
    private val USER_PREF_KEY = "USER_PREFERENCES_KEY"
    val repository = TopicRepository()
    private lateinit var sharedPreferences: SharedPreferences
    private var jsonURL = ""
    private val JSON_KEY = "Json_Link"



    override fun onCreate(){
        sharedPreferences = getSharedPreferences(USER_PREF_KEY, Context.MODE_PRIVATE)
        super.onCreate()
        jsonURL = "http://tednewardsandbox.site44.com/questions.json"
        sharedPreferences
            .edit()
            .putString(JSON_KEY, jsonURL)
            .apply()

        val  inputStream = assets.open("questions.json")
        val json = inputStream.bufferedReader().use{it.readText()}
        repository.loadData(json)
        Log.i(TAG, "Quiz was loaded successfully.")
    }

    companion object{
        const val TAG = "Application"
        private var instance: QuizApp? = null

        fun getSingletonInstance(): QuizApp{
            return instance!!
        }


    }

    init{
        instance = this
    }
}