package edu.washington.manjic.quizdroid

import android.app.Application
import android.nfc.Tag
import android.util.Log

class QuizApp : Application(){
    val repository = TopicRepository()

    override fun onCreate(){
        super.onCreate()

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