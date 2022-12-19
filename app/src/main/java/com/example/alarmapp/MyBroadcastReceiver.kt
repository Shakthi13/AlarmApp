package com.example.alarmapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.util.Log
import android.widget.Toast


class MyBroadcastReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {
        Log.d("AlarmApp","AlarmTriggered")

        Toast.makeText(context, "Alarm! Triggered", Toast.LENGTH_LONG).show()
        var alarmUri: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }

        // setting default ringtone
        val ringtone = RingtoneManager.getRingtone(context, alarmUri)
        // play ringtone
        ringtone.play()
        android.os.Handler().postDelayed({
            ringtone.stop()
        },10000)
    }

}
