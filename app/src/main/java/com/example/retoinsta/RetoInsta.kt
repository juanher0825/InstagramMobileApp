package com.example.retoinsta

import android.app.Application

class RetoInsta: Application() {

    companion object{
        lateinit var myDataBase : Preferencias
    }

    override fun onCreate() {
        super.onCreate()
        myDataBase = Preferencias(applicationContext)
        myDataBase.newUser()
    }
}