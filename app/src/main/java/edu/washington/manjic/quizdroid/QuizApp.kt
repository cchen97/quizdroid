package edu.washington.manjic.quizdroid

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.*
import android.util.Log


class QuizApp : Application(){
    private val USER_PREF_KEY = "USER_PREFERENCES_KEY"
    val repository = TopicRepository()
    private lateinit var sharedPreferences: SharedPreferences
    private val JSON_KEY = "Json_Link"



    override fun onCreate(){

        sharedPreferences = getSharedPreferences(USER_PREF_KEY, Context.MODE_PRIVATE)
        super.onCreate()
        sharedPreferences
            .edit()
            .putString(JSON_KEY, jsonURL)
            .apply()

        val  inputStream = assets.open("questions.json")
        val json = inputStream.bufferedReader().use{it.readText()}
        Log.i(TAG, "start download")
        repository.DownloadData(jsonURL, this)
    }

    companion object{
        const val TAG = "Application"
        private var instance: QuizApp? = null
        var jsonURL = "http://tednewardsandbox.site44.com/questions.json"
        const val BROADCAST = "edu.washington.manjic.quizBoard.BROADCAST"

        fun getSingletonInstance(): QuizApp{
            return instance!!
        }
    }

    init{
        instance = this
    }
}