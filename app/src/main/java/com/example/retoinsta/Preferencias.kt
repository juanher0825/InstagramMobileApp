package com.example.retoinsta

import android.content.Context
import com.example.retoinsta.model.Post
import com.example.retoinsta.model.Usuario
import com.google.gson.Gson
import java.util.*

class Preferencias(val context: Context) {
    val NOMBRE_PREFERENCIAS ="Mydtb"
    val post = "posts"
    val database = context.getSharedPreferences(NOMBRE_PREFERENCIAS, 0)
    var id : String? = null
    var userLogin = "LOGGED_USER"
    val remember = "remember"
    val gson = Gson()

    fun newUser(){
        if(database.getString("ALFA","").toString().isEmpty()||database.getString("BETA","").toString().isEmpty()){

            val usuarioAlfa = Usuario(UUID.randomUUID().toString(),"Alfa","El Alfa Guapi","Me la pela Apps #Es broma","")
            val usuarioBeta = Usuario(UUID.randomUUID().toString(),"Beta","El Beta Guapi","Me la suda Apps #Es broma","")
            val alfaString = gson.toJson(usuarioAlfa)
            val betaString = gson.toJson(usuarioBeta)
            database.edit().putString("ALFA", alfaString).apply()
            database.edit().putString("BETA", betaString).apply()
        }

        else return
    }

    fun loginUser(user : String){
        database.edit().putString(userLogin, user).apply()
    }

    fun getUserLogin(): Usuario? {

        id = database.getString(userLogin,"").toString()

        if(id != null){
            val userString = database.getString(id,"")
            return gson.fromJson(userString, Usuario::class.java)
        }

        else return null
    }

    fun getUserByID(uuid: String): Usuario {

        val usuarioAlfa = gson.fromJson(database.getString("ALFA",""), Usuario::class.java)
        val usuarioBeta = gson.fromJson(database.getString("BETA",""), Usuario::class.java)

        if(usuarioAlfa.id.contentEquals(uuid)) return usuarioAlfa

        return usuarioBeta


    }

    fun getRemembered(): Boolean{
        return database.getBoolean(remember, false)
    }

    fun saveCheckRemember(remember2:Boolean){
        database.edit().putBoolean(remember, remember2).apply()
    }

    fun logout(){

        database.edit().remove(userLogin).apply()
    }

    fun getPosts() : MutableList<Post>{
        var listOfPosts = mutableListOf<Post>()
        val posts = database.getString(post,"")

        if(posts!!.isEmpty()) return listOfPosts
        listOfPosts = gson.fromJson(posts, Array<Post>::class.java).toMutableList()
        return listOfPosts
    }




}