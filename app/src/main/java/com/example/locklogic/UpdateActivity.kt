package com.example.locklogic

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.locklogic.Models.Puerta
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase

class UpdateActivity : AppCompatActivity() {
    private lateinit var puerta: Puerta

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recuperar puerta del intent
        puerta = intent.getParcelableExtra("Puertas")!!

        // Vincular vistas
        val ipEditText: TextInputEditText = findViewById(R.id.IP_Text)
        val nameEditText: TextInputEditText = findViewById(R.id.name_Text)
        val activoCheckBox: MaterialCheckBox = findViewById(R.id.activo_checkbox)
        val btnUpdate: MaterialButton = findViewById(R.id.btnDoorUpdate)

        // Poblar vistas con datos de la puerta
        ipEditText.setText(puerta.ip)
        nameEditText.setText(puerta.nombre)
        activoCheckBox.isChecked = puerta.active == "true"

        btnUpdate.setOnClickListener {
            // Obtener valores actualizados
            val nuevaIp = ipEditText.text.toString()
            val nuevoNombre = nameEditText.text.toString()
            val nuevoEstado = if (activoCheckBox.isChecked) "true" else "false"

            // Validaciones básicas
            if (nuevaIp.isBlank() || nuevoNombre.isBlank()) {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Actualizar puerta en Firebase
            actualizarPuerta(nuevaIp, nuevoNombre, nuevoEstado)
        }
    }

    private fun actualizarPuerta(ip: String, nombre: String, active: String) {
        puerta.id?.let { doorId ->
            val database = FirebaseDatabase.getInstance().reference
            val updatedPuerta = puerta.copy(
                ip = ip,
                nombre = nombre,
                active = active
            )

            database.child("Puertas").child(doorId).setValue(updatedPuerta)
                .addOnSuccessListener {
                    Toast.makeText(this, "Puerta actualizada exitosamente", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)

                    finish() // Cerrar la actividad después de actualizar
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al actualizar la puerta", Toast.LENGTH_SHORT).show()
                }
        }
    }
}