package com.example.locklogic

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.locklogic.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicialización del binding
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                100 + systemBars.left,
                100 + systemBars.top,
                100 + systemBars.right,
                100 + systemBars.bottom
            )
            insets
        }

        // Listener para el botón de registro
        binding.btnRegister.setOnClickListener {
            registerUser()
        }
    }

    // Método para manejar el registro del usuario
    private fun registerUser() {
        val email = binding.textInEmail.text.toString().trim()
        val password = binding.textInPaswword.text.toString()
        val confirmPassword = binding.textInPaswwordRepeat.text.toString()

        // Validar el correo electrónico
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tvRegistrar.text = "Por favor, ingresa un correo válido."
            return
        }

        // Validar la contraseña
        if (password.length != 8) {
            binding.tvRegistrar.text = "La contraseña debe tener exactamente 8 caracteres."
            return
        }

        // Verificar que las contraseñas coincidan
        if (password != confirmPassword) {
            binding.tvRegistrar.text = "Las contraseñas no coinciden."
            return
        }

        // Verificar si el correo ya existe en Firebase
        auth.fetchSignInMethodsForEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val signInMethods = task.result?.signInMethods ?: emptyList<String>()
                if (signInMethods.isNotEmpty()) {
                    binding.tvRegistrar.text = "El correo ya está registrado."
                } else {
                    // Registrar el usuario
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { createTask ->
                            if (createTask.isSuccessful) {
                                // Registro exitoso, cambiar a la pantalla principal
                                Toast.makeText(this, "Usuario registrado correctamente.", Toast.LENGTH_SHORT).show()
                                changeToLogin()
                            } else {
                                // Error al registrar
                                binding.tvRegistrar.text = "Error al registrar: ${createTask.exception?.message}"
                            }
                        }
                }
            } else {
                binding.tvRegistrar.text = "Error al verificar el correo: ${task.exception?.message}"
            }
        }
    }

    // CAMBIO DE PANTALLA
    private fun changeToLogin() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Cierra la actividad actual para evitar volver con "Atrás"
    }
}
