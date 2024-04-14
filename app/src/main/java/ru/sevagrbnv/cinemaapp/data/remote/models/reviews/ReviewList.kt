package ru.sevagrbnv.cinemaapp.data.remote.models.reviews

import com.google.gson.annotations.SerializedName

data class ReviewList(
    @SerializedName("docs")val docs: List<ReviewDoc>
)