package com.example.locklogic

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.locklogic.Models.Puerta
import com.google.firebase.database.FirebaseDatabase

class PuertaDetail : AppCompatActivity() {
    private lateinit var puerta: Puerta

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_puerta_detail)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recuperar la puerta pasada desde el fragment
        puerta = intent.getParcelableExtra<Puerta>("Puertas")!!

        // Vincular vistas
        val nombreTextView: TextView = findViewById(R.id.textNameDoor)
        val ipTextView: TextView = findViewById(R.id.textIp)
        val activeTextView: TextView = findViewById(R.id.textActive)
        val imgPuerta: ImageView = findViewById(R.id.imgPuerta)

        // Poblar vistas con información de la puerta
        puerta.let {
            nombreTextView.text = it.nombre
            ipTextView.text = "IP: ${it.ip}"
            activeTextView.text = "Estado: ${it.active}"
        }

        // Configurar listeners para botones de edición y eliminación
        val imgEdit: ImageView = findViewById(R.id.imgEdit)
        val imgTrash: ImageView = findViewById(R.id.imgTrash)

        imgEdit.setOnClickListener {
            // Abrir UpdateActivity con los datos de la puerta
            val intent = Intent(this, UpdateActivity::class.java)
            intent.putExtra("Puertas", puerta)
            startActivity(intent)
        }

        imgTrash.setOnClickListener {
            // Mostrar diálogo de confirmación para eliminar
            mostrarDialogoEliminar()
        }
    }

    private fun mostrarDialogoEliminar() {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Puerta")
            .setMessage("¿Está seguro que desea eliminar la puerta ${puerta.nombre}?")
            .setPositiveButton("Sí") { _, _ ->
                eliminarPuerta()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun eliminarPuerta() {
        puerta.id?.let { doorId ->
            val database = FirebaseDatabase.getInstance().reference
            database.child("Puertas").child(doorId).removeValue()
                .addOnSuccessListener {
                    Toast.makeText(this, "Puerta eliminada exitosamente", Toast.LENGTH_SHORT).show()
                    finish() // Cerrar la actividad después de eliminar
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al eliminar la puerta", Toast.LENGTH_SHORT).show()
                }
        }
    }
}