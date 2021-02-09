package com.example.uangkas

import android.app.Application
import com.example.uangkas.data.DatabaseHelper

class App : Application() {

    companion object{
        var db : DatabaseHelper? = null
    }

    override fun onCreate() {
        super.onCreate()

        db = DatabaseHelper(this)
    }
}