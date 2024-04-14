package ru.sevagrbnv.cinemaapp.data.remote.models.actors

import com.google.gson.annotations.SerializedName

data class ActorList(
    @SerializedName("docs") val docs: List<ActorDoc>
)