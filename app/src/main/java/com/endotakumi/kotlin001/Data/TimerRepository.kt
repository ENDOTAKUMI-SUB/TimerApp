package com.endotakumi.kotlin001.Data

import com.endotakumi.kotlin001.Util.SharedPreferencesManager

class TimerRepository(public val sharedPreferencesManager : SharedPreferencesManager) : DataSource{
    companion object {
        val SP_DATA_NAME = "Timer"
        val SP_KEY_TIME_LAP = "TimeLap"
        val SP_KEY_TIME_VALUE = "TimeValue"
    }
}