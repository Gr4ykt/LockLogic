package com.example.locklogic

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.locklogic.databinding.HomeActivityBinding
import com.example.locklogic.Views.AddDoorFragment
import com.example.locklogic.Views.HomeFragment
import com.example.locklogic.Views.AdminDoorFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: HomeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicialización del binding
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Cargar fragment por defecto
        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
        }

        //Carga de todos los fragments
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.item_adminDoor -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, AdminDoorFragment()).commit()
                    true
                }
                R.id.item_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
                    true
                }
                R.id.item_add_door -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, AddDoorFragment()).commit()
                    true
                }
                else -> false
            }
        }


        //Para evitar que la pagina se recargue
        binding.bottomNavigation.setOnItemReselectedListener {
            when(it.itemId){
                R.id.item_adminDoor -> {
                    true
                }
                R.id.item_home -> {
                    true
                }
                R.id.item_add_door -> {
                    true
                }
                else -> false
            }
        }

    }
}
