package edu.washington.manjic.quizdroid

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

private const val TAG = "BroadcastReceiver"

class BroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        when (intent?.action) {
            MainActivity.BROADCAST -> {
                var link = intent.extras[LINK] as String
                Log.i("toasst wokr",link)
                Toast.makeText(context, "Downloading... $link", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val LINK = "edu.washington.manjic.quizdroid.LINK"
    }
}

