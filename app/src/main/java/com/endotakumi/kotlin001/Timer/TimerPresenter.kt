package com.endotakumi.kotlin001.Timer

import android.os.Handler
import com.endotakumi.kotlin001.Data.TimerRepository
import com.endotakumi.kotlin001.R

/**
 * Listens to user actions from the UI ([AddEditTaskFragment]), retrieves the data and updates
 * the UI as required.
 *
 * @param timerRepository a repository of data for tasks
 *
 * @param timerTaskView the add/edit view
 *
 * @param isDataMissing whether data needs to be loaded or not (for config changes)
 */
class TimerPresenter(
    val timerRepository: TimerRepository,
    val timerTaskView: TimerContract.View,
    override var isDataMissing: Boolean
    ) : TimerContract.Presenter{

    val handler = Handler()

    var timeValue = 0
    var status = false
    var timeLap = ""

    init{
        timerTaskView.presenter = this
    }

    override fun start() {
        if(isDataMissing) {
            loadDataFromSPM()
        }
    }

    private fun loadDataFromSPM(){
        with(timerRepository.sharedPreferencesManager){
            timeValue = getValue(TimerRepository.SP_KEY_TIME_VALUE, 0)

            println("TimeV: " + timeValue)
            timeLap = getValue(TimerRepository.SP_KEY_TIME_LAP, "")

            timeToText(timeValue)?.let { timerTaskView.setTextTextviewTime(it) }
            timerTaskView.setTextTextviewLapTime(timeLap)
        }
    }

    // Execute processing every second
    private val runnable = object : Runnable {
        override fun run() {
            // Increment one second
            timeValue++

            // Update TextView
            timeToText(timeValue)?.let { timerTaskView.setTextTextviewTime(it) }
            handler.postDelayed(this, 1000)
        }

    }

    override fun timerStart(){
        if (!status) {
            handler.post(runnable)
            status = true
            timerTaskView.setTextButtonReset(R.string.buttonResetLap)
        }
    }

    override fun timerStop(){
        if (status) {
            handler.removeCallbacks(runnable)
            status = false
            timerTaskView.setTextButtonReset(R.string.buttonReset)
        }
    }

    override fun timerReset(){
        if (!status) {
            // Reset
            handler.removeCallbacks(runnable)
            timeValue = 0
            timeToText()?.let {
                timerTaskView.setTextTextviewTime(it)
            }

            timeLap = ""
            timerTaskView.setTextTextviewLapTime(timeLap)
            status = false
        } else {
            //Lap
            timeLap = timeToText(timeValue) + "\n" + timeLap
            timerTaskView.setTextTextviewLapTime(timeLap)
        }
    }

    fun pause(){
        saveDateToSPM()
        timerStop()
    }

    private fun saveDateToSPM(){
        if(timeValue == 0 && timeLap.equals("")){
            isDataMissing = false
            with(timerRepository.sharedPreferencesManager) {
                startEditSPreferences()
                putValue(TimerActivity.SHOULD_LOAD_DATA_FROM_REPO_KEY, isDataMissing)
                endEditSPreferences()
            }
        }else {
            isDataMissing = true
            with(timerRepository.sharedPreferencesManager) {
                startEditSPreferences()
                putValue(TimerActivity.SHOULD_LOAD_DATA_FROM_REPO_KEY, isDataMissing)
                putValue(TimerRepository.SP_KEY_TIME_VALUE, timeValue)
                putValue(TimerRepository.SP_KEY_TIME_LAP, timeLap)
                endEditSPreferences()
            }
        }
    }

}

// Function to convert numeric value to character string of 00: 00: 00 format
fun timeToText(time: Int = 0): String? {
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