package com.example.locklogic

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.locklogic.Models.Puerta
import com.example.locklogic.databinding.ActivityDoorRecyclerBinding
import com.example.locklogic.databinding.FragmentAddDoorBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

@Suppress("DEPRECATION")
class DoorRecycler : AppCompatActivity() {

    private lateinit var binding: ActivityDoorRecyclerBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDoorRecyclerBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val puerta = intent.getParcelableExtra<Puerta>("Puertas")

        puerta?.let {
            binding.puertaName.text = it.nombre
            binding.ipText.text = it.ip

            database = FirebaseDatabase.getInstance().reference

            binding.imgPuerta.setOnClickListener{
                val intent = Intent(this, PuertaDetail::class.java)
                intent.putExtra("Puertas", it.id)
                startActivity(intent)
            }
        }

    }
}