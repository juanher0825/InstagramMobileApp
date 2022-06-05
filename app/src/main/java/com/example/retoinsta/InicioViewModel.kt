package com.example.retoinsta

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retoinsta.model.Post

class InicioViewModel : ViewModel(){
    var posts = MutableLiveData<MutableList<Post>> ()

    init {

        posts.postValue(RetoInsta.myDataBase.getPosts())
    }
}