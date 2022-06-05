package com.example.retoinsta.model

import java.util.*

data class Post(
    var id: String,
    var imagenPost: String,
    var descripcion: String,
    var fechaPost : Calendar,
    var ciudad : String
)
