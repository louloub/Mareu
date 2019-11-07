package com.example.maru.service.model

import java.util.*

class Meeting(var date: String = "2019-10-17",
              var hour: String = "",
              var place: String = "",
              var subject: String = "",
              var listOfEmailOfParticipant: List<String> = Arrays.asList(""))