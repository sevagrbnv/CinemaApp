package ru.sevagrbnv.cinemaapp.data.remote.models.posters

import com.google.gson.annotations.SerializedName

data class PosterList(
    @SerializedName("docs") val docs: List<PosterDoc>
)