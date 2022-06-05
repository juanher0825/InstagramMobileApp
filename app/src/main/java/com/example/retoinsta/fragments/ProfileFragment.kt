package com.example.retoinsta.fragments

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.retoinsta.AccountSettingActivity
import com.example.retoinsta.RetoInsta.Companion.myDataBase
import com.example.retoinsta.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val view = binding.root

        binding.editBtn.setOnClickListener {
            startActivity(Intent(context, AccountSettingActivity::class.java))
        }

        verificarDatosUsuario()

        return view
    }

    fun verificarDatosUsuario() {

        binding.nombreET.setText(myDataBase.getUserLogin()?.usuario)
        binding.descriptionET.setText(myDataBase.getUserLogin()?.biografia)
        binding.photoPerFragment.setImageBitmap(BitmapFactory.decodeFile(myDataBase.getUserLogin()!!.foto))
        binding.usuarioFragment.setText(myDataBase.getUserLogin()?.nombre)
    }

    override fun onPause() {
        super.onPause()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ProfileFragment()
    }
}