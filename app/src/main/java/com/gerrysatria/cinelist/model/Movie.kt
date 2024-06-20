package com.gerrysatria.cinelist.model

data class Movie(
    val id: Long,
    val poster: Int,
    val name: String,
    val synopsis: String,
    val duration: String,
    val rating: Double
)