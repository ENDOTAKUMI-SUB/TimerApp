package com.endo_takumi.kotlin_001

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val handler = Handler()
    var timeValue = 0
    var status = false
    var timeLap = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Execute processing every second
        val runnable = object : Runnable {
            override fun run() {
                // Increment one second
                timeValue++

                // Update TextView
                timeToText(timeValue)?.let { textViewTime.text = it }
                handler.postDelayed(this, 1000)
            }

        }

        // Operation when START button is pushed
        buttonStart.setOnClickListener {
            if (!status) {
                handler.post(runnable)
                status = true
                buttonReset.text = getString(R.string.buttonResetLap)
            }
        }

        // Operation when STOP button is pushed
        buttonStop.setOnClickListener {
            if (status) {
                handler.removeCallbacks(runnable)
                status = false
                buttonReset.text = getString(R.string.buttonReset)
            }
        }

        // Operation when RESET button is pushed
        buttonReset.setOnClickListener {
            if (!status) {
                // Reset
                handler.removeCallbacks(runnable)
                timeValue = 0
                timeToText()?.let {
                    textViewTime.text = it
                }

                timeLap = ""
                textViewLapTime.text = timeLap
                status = false
            } else {
                //Lap
                timeLap += timeToText(timeValue) + "\n"
                textViewLapTime.text = timeLap
            }
        }
    }

    // Function to convert numeric value to character string of 00: 00: 00 format
    private fun timeToText(time: Int = 0): String? {
        return when {
            time < 0 -> null
            time == 0 -> "00:00:00"
            else -> {
                val h = time / 3600
                val m = time % 3600 / 60
                val s = time % 60
                "%1$02d:%2$02d:%3$02d".format(h, m, s)
            }
        }
    }

}
