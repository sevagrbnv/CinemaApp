package ru.sevagrbnv.cinemaapp.data.remote.models.reviews

import com.google.gson.annotations.SerializedName

data class ReviewDoc(
    @SerializedName("author") val author: String?,
    @SerializedName("id") val id: Int,
    @SerializedName("review") val review: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("type") val type: String?,
)