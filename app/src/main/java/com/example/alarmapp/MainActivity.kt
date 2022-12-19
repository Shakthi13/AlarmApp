package com.example.alarmapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var pendingIntent: PendingIntent? = null
    private lateinit var alarmManager :AlarmManager
    var seconds = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val alarmButton = findViewById<Button>(R.id.startAlarm)
        val stopButton = findViewById<Button>(R.id.stopAlarm)
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmButton.setOnClickListener{
            startAlert()
            runTimer()
        }
        stopButton.setOnClickListener{
            if (pendingIntent != null) {
                alarmManager.cancel(pendingIntent)

                Toast.makeText(this, "Repeated Alarm Stopped", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun runTimer() {
        val txtView=findViewById<TextView>(R.id.timer)
        val handler = Handler(Looper.getMainLooper())
        val runnable=object:Runnable {
            override fun run() {
                val hours = seconds/3600
                val mins=(seconds%3600)/60
                val secs = seconds%60
                val time = String.format("%02d:%02d:%02d",hours,mins,secs)
                txtView.text=time
                seconds++
                handler.postDelayed(this,1000)
            }
        }

        handler.post(runnable)
    }

    private fun startAlert() {
        val intent = Intent(this, MyBroadcastReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(
            this.applicationContext, 13, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+150000
            ,3*60000,pendingIntent)
    }
}