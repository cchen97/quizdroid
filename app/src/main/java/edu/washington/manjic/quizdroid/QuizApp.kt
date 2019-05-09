package edu.washington.manjic.quizdroid

import android.app.Application
import android.util.Log


class QuizApp : Application(){
    val repository = TopicRepository()

//    init{
//
//    }


    override fun onCreate(){
        super.onCreate()
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