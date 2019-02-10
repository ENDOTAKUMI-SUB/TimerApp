package com.endo_takumi.kotlin_001

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    val handler = Handler()
    var timeValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textViewTime = findViewById(R.id.textViewTime) as TextView
        val buttonStart = findViewById(R.id.buttonStart) as Button
        val buttonStop = findViewById(R.id.buttonStop) as Button
        val buttonReset = findViewById(R.id.buttonReset) as Button

        val runnable = object : Runnable {
            override fun run() {
                timeValue++

                timeToText(timeValue)?.let { textViewTime.text = it }
                handler.postDelayed(this, 1000)
            }

        }

        buttonStart.setOnClickListener {
            handler.post(runnable)
        }

        buttonStop.setOnClickListener {
            handler.removeCallbacks(runnable)
        }

        buttonReset.setOnClickListener {
            handler.removeCallbacks(runnable)
            timeValue = 0
            timeToText()?.let {
                textViewTime.text = it
            }
        }
    }

    private fun timeToText(time: Int = 0): String? {
        return if (time < 0) {
            null
        } else if (time == 0) {
            "00:00:00"
        } else {
            val h = time / 3600
            val m = time % 3600 / 60
            val s = time % 60
            "%1$02d:%2$02d:%3$02d".format(h, m, s)
        }
    }

}
