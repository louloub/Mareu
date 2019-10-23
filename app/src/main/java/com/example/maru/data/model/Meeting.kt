package com.example.maru.data.model

import java.util.*

class Meeting(var date: String = "2019-10-17'T'18:00:00",
              var place: String = "",
              var subject: String = "",
              var listOfEmailOfParticipant: MutableList<String> = Arrays.asList("")) {
}