package com.example.retoinsta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.retoinsta.fragments.NavigationScreenActivity
import com.example.retoinsta.RetoInsta.Companion.myDataBase


class LoginActivity : AppCompatActivity() {

    private lateinit var emailET : EditText
    private lateinit var passET : EditText
    private lateinit var loginBTN : Button
    private lateinit var noOlvidarBTN : CheckBox
    var isRemembered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailET = findViewById(R.id.userName)
        passET = findViewById(R.id.pass)
        loginBTN = findViewById(R.id.singBTN)
        noOlvidarBTN = findViewById(R.id.rememberCheck)
        isRemembered = myDataBase.getRemembered()

        if (isRemembered) {
            val intent = Intent(this, NavigationScreenActivity::class.java)
            startActivity(intent)
            finish()
        }

        loginBTN.setOnClickListener{
            val userName: String = emailET.text.toString()
           val password: String = passET.text.toString()
            val rememberCheck: Boolean = noOlvidarBTN.isChecked

            if ((userName == "alfa@gmail.com" || userName == "beta@gmail.com") && password == "aplicacionesmoviles"){

                if (userName == "alfa@gmail.com"){
                    myDataBase.loginUser("ALFA");
                    myDataBase.saveCheckRemember(rememberCheck)


                    Toast.makeText(this, "Information saved!", Toast.LENGTH_LONG).show()

                    val intent = Intent(this, NavigationScreenActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Usuario o contraseña correctos!", Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    myDataBase.loginUser("BETA");
                    myDataBase.saveCheckRemember(rememberCheck)

                    Toast.makeText(this, "Information saved!", Toast.LENGTH_LONG).show()

                    val intent = Intent(this, NavigationScreenActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Usuario o contraseña correctos!", Toast.LENGTH_LONG).show()
                    finish()
                }
            }else{

                Toast.makeText(this, "Usuario o contraseña incorrecto!", Toast.LENGTH_LONG).show()
            }

        }

    }
}