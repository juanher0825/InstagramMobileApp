package com.example.retoinsta

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    var isRemembered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        isRemembered = sharedPreferences.getBoolean("CHECKBOX", false)

        if (isRemembered) {
            //val intent = Intent(this, NavigationScreen::class.java)
            //startActivity(intent)
            finish()
        }

        singBTN.setOnClickListener{
            val userName: String = userName.text.toString()
           val password: String = pass.text.toString()
            val rememberCheck: Boolean = rememberCheck.isChecked
           val editor: SharedPreferences.Editor = sharedPreferences.edit()

            if ((userName == "alfa@gmail.com" || userName == "beta@gmail.com") && password == "aplicacionesmoviles"){

                if (userName == "alfa@gmail.com"){

                    editor.putString("NAME_OF_ALFA", userName)
                    editor.putString("PASSWORD_OF_ALFA", password)
                    editor.putBoolean("IS_ALFA", true)
                    editor.putBoolean("CHECKBOX", rememberCheck)
                    editor.apply()

                    Toast.makeText(this, "Information saved!", Toast.LENGTH_LONG).show()

                    //val intent = Intent(this, NavigationScreen::class.java)
                    //startActivity(intent)
                    Toast.makeText(this, "Usuario o contraseña correctos!", Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    editor.putString("NAME_OF_BETA", userName)
                    editor.putString("PASSWORD_OF_BETA", password)
                    editor.putBoolean("IS_BETA", true)
                    editor.putBoolean("CHECKBOX", rememberCheck)
                    editor.apply()

                    Toast.makeText(this, "Information saved!", Toast.LENGTH_LONG).show()

                    //val intent = Intent(this, NavigationScreen::class.java)
                    //startActivity(intent)
                    Toast.makeText(this, "Usuario o contraseña correctos!", Toast.LENGTH_LONG).show()
                    finish()
                }
            }else{

                Toast.makeText(this, "Usuario o contraseña incorrecto!", Toast.LENGTH_LONG).show()
            }

        }

    }
}