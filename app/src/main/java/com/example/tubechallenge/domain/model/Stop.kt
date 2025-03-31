package com.example.tubechallenge.domain.model

data class Stop(
    val id: Long = 0L,
    val stationName: String = "",
    val timeArrived: String = "",
    val timeDeparted: String = "",
    val notes: String = ""
)
