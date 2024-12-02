package com.example.locklogic

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast
import com.example.locklogic.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializacion del binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            v.setPadding(
                100 + systemBars.left,
                100 + systemBars.top,
                100 + systemBars.right,
                100 + systemBars.bottom
            )
            insets
        }

        binding.Registerbtn.setOnClickListener { changeToRegister() }
        binding.btnLogin.setOnClickListener { loginUser() }

    }

    // FUNCIONES PARA CAMBIAR DE PANTALLAS
    private fun changeToRegister(){ val intent = Intent(this, RegisterActivity::class.java); startActivity(intent) }
    private fun changeToHome(){ val intent = Intent(this, HomeActivity::class.java); startActivity(intent) }

    // FUNCIONES DE FIREBASE
    private fun loginUser() {
        val email = binding.textInEmail.text.toString().trim()
        val password = binding.textInPaswword.text.toString().trim()

        // Validar entradas
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa correo y contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        // Iniciar sesión con Firebase
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Inicio de sesión exitoso
                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                    changeToHome() // Lógica para ir a la pantalla principal
                } else {
                    // Error al iniciar sesión
                    Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
}