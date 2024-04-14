package ru.sevagrbnv.cinemaapp.data.remote.models.detailMain

import com.google.gson.annotations.SerializedName

data class DetailInfo(
    @SerializedName("countries") val countries: List<Country>?,
    @SerializedName("description") val description: String?,
    @SerializedName("genres") val genres: List<Genre>?,
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("poster") val poster: PosterDto?,
    @SerializedName("rating") val rating: Rating?,
    @SerializedName("year") val year: Int?
)