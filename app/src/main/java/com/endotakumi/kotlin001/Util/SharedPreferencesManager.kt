package com.endotakumi.kotlin001.Util

import android.app.Activity
import android.content.SharedPreferences
import java.lang.IllegalStateException

class SharedPreferencesManager(){
    //SharedPreferenceに現在のタイマーの情報を保存するようにしてみる
    private var sharedPreferences : SharedPreferences? = null
    private var editor : SharedPreferences.Editor? = null

    constructor(activity: Activity, dataName: String, mode: Int) : this() {
        sharedPreferences = activity.getSharedPreferences(dataName, mode)
    }

    fun setSharedPreferences(activity: Activity, dataName: String, mode: Int){
        sharedPreferences = activity.getSharedPreferences(dataName, mode)
    }

    //データ読み込み系
    fun getValue(key: String, default: Int): Int{
        checkNotNull(sharedPreferences)
        return sharedPreferences?.getInt(key, default) ?: default
    }
    fun getValue(key: String, default: Float): Float{
        checkNotNull(sharedPreferences)
        return sharedPreferences?.getFloat(key, default) ?: default
    }
    fun getValue(key: String, default: String): String{
        checkNotNull(sharedPreferences)
        return sharedPreferences?.getString(key, default) ?: default
    }
    fun getValue(key: String, default: Boolean): Boolean{
        checkNotNull(sharedPreferences)
        return sharedPreferences?.getBoolean(key, default) ?: default
    }


    //データ書き込み系
    fun startEditSPreferences(){
        checkNotNull(sharedPreferences)
        if(editor == null){
            editor = sharedPreferences?.edit()
        }else{
            throw IllegalStateException("Before startSaveSPreference(), you have to apply editor by endSaveSPreference() method.")
        }
    }

    fun endEditSPreferences() {
        editor?.commit()
        editor = null
    }

    //put系
    fun putValue(key: String, value: Int){
        checkNotNull(editor)
        editor?.putInt(key, value)
    }
    fun putValue(key: String, value: Float){
        checkNotNull(editor)
        editor?.putFloat(key, value)
    }
    fun putValue(key: String, value: String){
        checkNotNull(editor)
        editor?.putString(key, value)
    }
    fun putValue(key: String, value: Boolean){
        checkNotNull(editor)
        editor?.putBoolean(key, value)
    }

    fun isSharedPreferencesNonNull(): Boolean{
        return sharedPreferences != null
    }

    
}