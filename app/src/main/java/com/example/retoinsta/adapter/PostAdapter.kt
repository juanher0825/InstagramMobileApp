package com.example.retoinsta.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retoinsta.R
import com.example.retoinsta.ViewHolder.PostViewHolder
import com.example.retoinsta.model.Post

class PostAdapter : RecyclerView.Adapter<PostViewHolder>(){
    private val posts = mutableListOf<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.postrow, parent, false)
        val postViewHolder = PostViewHolder(view)
        return postViewHolder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val postn = posts[position]
        holder.bind(postn)
    }

    fun addPost(post: Post){
        posts.add(post)
        posts.sortByDescending{
            it.fechaPost
        }
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun limpiarPublicaciones() {
        posts.clear()
    }

}