package ru.sevagrbnv.cinemaapp.data.remote.models.listFragment

import com.google.gson.annotations.SerializedName

data class CinemaList(
    @SerializedName("docs") val docs: List<CinemaDoc>
)