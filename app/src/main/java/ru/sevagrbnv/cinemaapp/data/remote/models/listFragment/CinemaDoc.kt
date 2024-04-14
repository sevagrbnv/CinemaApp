package ru.sevagrbnv.cinemaapp.data.remote.models.listFragment

import com.google.gson.annotations.SerializedName

data class CinemaDoc(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("poster") val poster: Poster?,
    @SerializedName("rating") val rating: Rating?,
)