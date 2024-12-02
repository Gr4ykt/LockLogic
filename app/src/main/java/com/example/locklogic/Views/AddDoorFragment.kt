package com.example.locklogic.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.example.locklogic.Models.Puerta
import com.example.locklogic.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddDoorFragment : Fragment() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_add_door, container, false)
        val btnRegister: MaterialButton = view.findViewById(R.id.btnDoorRegister)

        val ipInp: TextInputEditText = view.findViewById(R.id.IP_Text)
        val nameText: TextInputEditText = view.findViewById(R.id.name_Text)
        val checkActivo: CheckBox = view.findViewById(R.id.activo_checkbox)

        database = FirebaseDatabase.getInstance().getReference("Puertas")

        btnRegister.setOnClickListener {
            val statusCheck:String
            val id = database.child("Puertas").push().key
            val ip = ipInp.text.toString()
            val nombre = nameText.text.toString()
            val cActivo = checkActivo.isChecked

            if(cActivo){
                statusCheck = "True"
            }else{
                statusCheck = "False"
            }

            val puerta = Puerta(id, ip, nombre, statusCheck, )
            database.child(id!!).setValue(puerta)
                .addOnSuccessListener {
                    changeConfirmation()
                }
        }

        return view
    }

    private fun changeConfirmation() {
        val adminDoorFragment = AdminDoorFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, adminDoorFragment)
            .addToBackStack(null)
            .commit()
    }
}