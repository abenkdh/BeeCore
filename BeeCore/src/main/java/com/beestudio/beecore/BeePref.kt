package com.beestudio.beecore

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
class BeePref(val application: Application){

    companion object {
        var currentApplication: Application? = null
        fun initialize(application: Application) {
            currentApplication = application
        }
        
        fun readString(key: String, default: String): String {
            val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(currentApplication?.applicationContext)
            return sharedPreferences.getString(key, default)!!
        }
    
        fun readInt(key: String, default: Int): Int {
            val sharedPreferences: SharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(currentApplication?.applicationContext)
            return sharedPreferences.getInt(key, default)
        }
    
        fun readFloat(key: String, default: Float): Float {
            val sharedPreferences: SharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(currentApplication?.applicationContext)
            return sharedPreferences.getFloat(key, default)
        }
    
        fun readBoolean(key: String, default: Boolean): Boolean {
            val sharedPreferences: SharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(currentApplication?.applicationContext)
            return sharedPreferences.getBoolean(key, default)
        }
    
        fun readLong(key: String, default: Long): Long {
            val sharedPreferences: SharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(currentApplication?.applicationContext)
            return sharedPreferences.getLong(key, default)
        }
        
        fun write(key: String, value: Any) {
            val sharedPreferences: SharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(currentApplication?.applicationContext)
            when (value) {
                is Boolean -> {
                    sharedPreferences.edit().putBoolean(key, value).apply()
                }
                is String -> {
                    sharedPreferences.edit().putString(key, value).apply()
                }
                is Float -> {
                    sharedPreferences.edit().putFloat(key, value).apply()
                }
                is Int -> {
                    sharedPreferences.edit().putInt(key, value).apply()
                }
                is Long -> {
                    sharedPreferences.edit().putLong(key, value).apply()
                }
            }
        }
        
    }
}