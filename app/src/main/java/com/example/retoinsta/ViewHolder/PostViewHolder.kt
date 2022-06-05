package com.example.retoinsta.ViewHolder

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.text.format.DateFormat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retoinsta.R
import com.example.retoinsta.RetoInsta
import com.example.retoinsta.model.Post
import java.time.Month
import java.util.*

class PostViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
    var nombreUsuarioPost : TextView = itemView.findViewById(R.id.userPost)
    var descripcionPostET: TextView = itemView.findViewById(R.id.descriptionPost)
    var fechitaPostET: TextView = itemView.findViewById(R.id.datePost)
    var ubicacionPostET: TextView = itemView.findViewById(R.id.ubiPost)
    var imagenPublicacion : ImageView = itemView.findViewById(R.id.imagenPublicacion)
    var fotoPerfilPost : ImageView = itemView.findViewById(R.id.picPost)
    var nombreUsuarioAbajo : TextView = itemView.findViewById(R.id.userDPost)

    fun bind(post : Post){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mes =
                Month.of(post.fechaPost.get(Calendar.MONTH)+1).toString()

            val dia = post.fechaPost.get(Calendar.DAY_OF_MONTH).toString()
            val anio = post.fechaPost.get(Calendar.YEAR).toString()
            fechitaPostET.text = dia + "/" + mes + "/" + anio

        } else {
            val dia = DateFormat.format("dd", post.fechaPost.time) as String
            val mes = DateFormat.format("MMM", post.fechaPost.time) as String
            val anio = DateFormat.format("yyyy", post.fechaPost.time) as String
            fechitaPostET.text = dia + " " + mes+ " " + anio
        }


        val bitmap = BitmapFactory.decodeFile(post.imagenPost)
        val thumbnail = Bitmap.createScaledBitmap(bitmap, bitmap.width/4, bitmap.height/4, true)


        nombreUsuarioPost.text = RetoInsta.myDataBase.getUserByID(post.id).nombre
        descripcionPostET.text = post.descripcion
        ubicacionPostET.text = post.ciudad
        imagenPublicacion.setImageBitmap(thumbnail)
        fotoPerfilPost.setImageBitmap(BitmapFactory.decodeFile(RetoInsta.myDataBase.getUserByID(post.id).foto))
        nombreUsuarioAbajo.text = RetoInsta.myDataBase.getUserByID(post.id).usuario

    }
}