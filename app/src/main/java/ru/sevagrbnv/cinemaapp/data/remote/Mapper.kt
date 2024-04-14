package ru.sevagrbnv.cinemaapp.data.remote

import ru.sevagrbnv.cinemaapp.data.remote.models.actors.ActorDoc
import ru.sevagrbnv.cinemaapp.data.remote.models.detailMain.DetailInfo
import ru.sevagrbnv.cinemaapp.data.remote.models.listFragment.CinemaDoc
import ru.sevagrbnv.cinemaapp.data.remote.models.posters.PosterDoc
import ru.sevagrbnv.cinemaapp.data.remote.models.reviews.ReviewDoc
import ru.sevagrbnv.cinemaapp.domain.models.Actor
import ru.sevagrbnv.cinemaapp.domain.models.Cinema
import ru.sevagrbnv.cinemaapp.domain.models.CinemaDetail
import ru.sevagrbnv.cinemaapp.domain.models.Poster
import ru.sevagrbnv.cinemaapp.domain.models.Review
import kotlin.math.round


fun CinemaDoc.toCinema() = Cinema(
    id = id,
    name = name ?: "[Нет данных]",
    posterPreview = poster?.previewUrl,
    rating = (round(rating?.kp?.times(10) ?: 0.toDouble()) / 10).toString() ?: "[Нет данных]",
)

fun ActorDoc.toActor() = Actor(
    id = id,
    name = name ?: "[Нет данных]",
    photo = photo ?: "[Нет данных]"
)

fun ReviewDoc.toReview() = Review(
    id = id,
    author = author ?: "[Нет данных]",
    title = title ?: "[Нет данных]",
    review = review ?: "[Нет данных]",
    type = type ?: "[Нет данных]"
)

fun PosterDoc.toPoster() = Poster(
    id = id,
    urt = previewUrl
)

fun DetailInfo.toCinemaDetail() = CinemaDetail(
    countries = countries?.joinToString(", ") { it.name } ?: "[Нет данных]",
    description = description ?: "[Нет данных]",
    genres = genres?.joinToString(", ") {it.name} ?: "[Нет данных]",
    id = id,
    name = name ?: "[Нет данных]",
    poster = poster?.previewUrl ?: "[Нет данных]",
    rating = (round(rating?.kp?.times(10) ?: 0.toDouble()) / 10).toString() ?: "[Нет данных]",
    year = year.toString() ?: "[Нет данных]"
)