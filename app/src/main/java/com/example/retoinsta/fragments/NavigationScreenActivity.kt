package com.example.retoinsta.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.retoinsta.R
import com.example.retoinsta.databinding.ActivityNavigationScreenBinding

class NavigationScreenActivity : AppCompatActivity() {
    private lateinit var inicioFragment: InicioFragment
    private lateinit var postFragment: PostFragment
    //private lateinit var profileFragment: ProfileFragment


    private lateinit var binding: ActivityNavigationScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_screen)
        binding = ActivityNavigationScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        inicioFragment = InicioFragment.newInstance()
        postFragment = PostFragment.newInstance()
        //profileFragment = ProfileFragment.newInstance()

        mostrarFragment(inicioFragment)
        binding.barraNavegacion.setOnNavigationItemSelectedListener { menuItem ->

            if (menuItem.itemId == R.id.inicioItem) {
                mostrarFragment(inicioFragment)
            } else if (menuItem.itemId == R.id.postItem) {
                mostrarFragment(postFragment)
            }  else if (menuItem.itemId == R.id.profileItem) {
                //mostrarFragment(profileFragment)
            }

            true
        }

    }

    private fun mostrarFragment(fragment: Fragment) {
        val transaccion = supportFragmentManager.beginTransaction()
        transaccion.replace(R.id.fragmentContainer, fragment)
        transaccion.commit()

    }
}