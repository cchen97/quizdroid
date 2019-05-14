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
    private var jsonURL = ""
    private val JSON_KEY = "Json_Link"
    private var alarmManager: AlarmManager? = null



    override fun onCreate(){

        alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val filter = IntentFilter()
        filter.addAction(BROADCAST)
        val receiver = BroadcastReceiver()
        registerReceiver(receiver, filter)

        sharedPreferences = getSharedPreferences(USER_PREF_KEY, Context.MODE_PRIVATE)
        super.onCreate()
        sharedPreferences
            .edit()
            .putString(JSON_KEY, jsonURL)
            .apply()

        val  inputStream = assets.open("questions.json")
        val json = inputStream.bufferedReader().use{it.readText()}
        repository.loadData(json)

        // put alarm
        val intent = Intent(BROADCAST)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager!!.setRepeating(
            AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
            (1 * 60 * 1000),
            pendingIntent
        )
        Log.i(TAG, "Quiz was loaded successfully.")
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