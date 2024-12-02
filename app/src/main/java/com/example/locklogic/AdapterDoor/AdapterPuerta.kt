package com.example.locklogic.AdapterDoor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.locklogic.Models.Puerta
import com.example.locklogic.R

class AdapterPuerta(
    private val puertas: ArrayList<Puerta>,
    private val onItemClick: (Puerta) -> Unit
) : RecyclerView.Adapter<AdapterPuerta.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.puertaName)
        val ip: TextView = itemView.findViewById(R.id.ipText)
        val imagen: ImageView = itemView.findViewById(R.id.imgPuerta)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_door_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val puerta = puertas[position]
        holder.nombre.text = puerta.nombre
        holder.ip.text = puerta.ip

        holder.itemView.setOnClickListener {
            onItemClick(puerta)
        }
    }

    override fun getItemCount(): Int = puertas.size
}