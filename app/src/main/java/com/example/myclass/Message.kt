package com.example.myclass

import java.util.*

object MessageType {
    const val TEXT = "TEXT"
    const val image = "IMAGE"

}

interface Message {
    val time: Date
    val senderId: String
    val type: String
}