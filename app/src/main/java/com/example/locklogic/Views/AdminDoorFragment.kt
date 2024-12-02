package com.example.locklogic.Views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.locklogic.AdapterDoor.AdapterPuerta
import com.example.locklogic.Models.Puerta
import com.example.locklogic.PuertaDetail
import com.example.locklogic.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminDoorFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var adapter: AdapterPuerta
    private val puertasList = ArrayList<Puerta>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_admin_door, container, false)

        val rvDoors: RecyclerView = view.findViewById(R.id.Puertas)

        database = FirebaseDatabase.getInstance().getReference("Puertas")

        adapter = AdapterPuerta(puertasList) { puerta ->
            val intent = Intent(requireContext(), PuertaDetail::class.java)
            intent.putExtra("Puertas", puerta)
            startActivity(intent)
        }

        rvDoors.layoutManager = LinearLayoutManager(requireContext())
        rvDoors.adapter = adapter

        fetchPuertasFromFirebase()
        return view
    }

    private fun fetchPuertasFromFirebase() {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        database.orderByChild("userId").equalTo(currentUserId)
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    puertasList.clear()
                    for (data in snapshot.children) {
                        val puerta = data.getValue(Puerta::class.java)
                        puerta?.let { puertasList.add(it) }
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("FirebaseError", "Error al leer los datos: ${error.message}")
                }
            })
    }
}