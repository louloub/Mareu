package com.example.maru.data.model

data class Meeting(var date: String = "2019-10-17'T'18:00:00",
                   val place: String,
                   val subject: String,
                   val listOfEmailOfParticipant: List<String>) {
}