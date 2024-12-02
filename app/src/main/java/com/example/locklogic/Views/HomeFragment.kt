package com.example.locklogic.Views

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import com.example.locklogic.MainActivity
import com.example.locklogic.R
import com.example.locklogic.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth


class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        val btnDoors: MaterialButton = view.findViewById(R.id.btnDoors)
        val btnLogout: MaterialButton = view.findViewById(R.id.btnLogout)

        btnDoors.setOnClickListener{ changeToFragmentAdminDoor() }
        btnLogout.setOnClickListener { showLogoutConfirmationDialog() }

        return view
    }


    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Cerrar sesión")
            .setMessage("¿Estás seguro de que deseas cerrar sesión?")
            .setPositiveButton("Sí") { _, _ ->
                logout()

            }
            .setNegativeButton("No", null) // No hacer nada si elige "No"
            .show()
    }

    private fun logout() {
        // Cerrar sesión en Firebase
        FirebaseAuth.getInstance().signOut()

        // Navegar al fragmento o actividad de inicio de sesión
        requireActivity().supportFragmentManager.popBackStack() // O manejar la navegación según lo necesites
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun changeToFragmentAdminDoor(){
        val adminDoorFragment = AdminDoorFragment()

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, adminDoorFragment)
            .addToBackStack(null)
            .commit()

    }

}


