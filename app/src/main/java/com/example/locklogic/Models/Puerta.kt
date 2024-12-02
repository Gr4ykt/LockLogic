package com.example.locklogic.Models

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.auth.FirebaseAuth

data class Puerta(
    val id:String? = null,
    val ip:String? = null,
    val nombre:String? = null,
    val active:String? = null,
    val userId:String? = FirebaseAuth.getInstance().currentUser?.uid // OBTENER EL UID DEL USUARIO AUTENTICADO

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(ip)
        parcel.writeString(nombre)
        parcel.writeString(active)
        parcel.writeString(userId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Puerta> {
        override fun createFromParcel(parcel: Parcel): Puerta {
            return Puerta(parcel)
        }

        override fun newArray(size: Int): Array<Puerta?> {
            return arrayOfNulls(size)
        }
    }

}