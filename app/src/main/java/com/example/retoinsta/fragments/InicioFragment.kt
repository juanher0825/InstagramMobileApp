package com.example.retoinsta.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retoinsta.InicioViewModel
import com.example.retoinsta.R
import com.example.retoinsta.adapter.PostAdapter
import com.example.retoinsta.databinding.FragmentInicioBinding
import com.example.retoinsta.model.Post

class InicioFragment : Fragment(), PostFragment.onNewPostListener {

    private var _binding:FragmentInicioBinding? = null
    private val binding get() = _binding!!

    private lateinit var inicioViewModel : InicioViewModel

    private val adaptador = PostAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInicioBinding.inflate(inflater, container, false)

        val view = binding.root
        val publicacionRecycler = binding.recyclerviewInicio
        publicacionRecycler.setHasFixedSize(true)
        publicacionRecycler.layoutManager = LinearLayoutManager(activity)
        publicacionRecycler.adapter = adaptador
        inicioViewModel= ViewModelProvider(this).get(InicioViewModel::class.java)
        inicioViewModel.posts.observe(viewLifecycleOwner) {

            adaptador.limpiarPublicaciones()
            for (publicacion in it) {
                adaptador.addPost(publicacion)
            }
        }
        return view
    }


    override fun onResume() {
        super.onResume()
        adaptador.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            InicioFragment()
    }

    override fun onNewPost(post: Post) {
        adaptador.addPost(post)
    }
}