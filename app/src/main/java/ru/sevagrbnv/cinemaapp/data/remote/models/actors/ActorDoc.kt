package ru.sevagrbnv.cinemaapp.data.remote.models.actors

import com.google.gson.annotations.SerializedName

data class ActorDoc(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("photo") val photo: String,
)