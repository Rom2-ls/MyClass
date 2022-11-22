package com.example.myclass

import java.util.*

data class TextMessage(val text : String,
                       override val time: Date,
                       override val senderId: String,
                       override val type: String = MessageType.TEXT)
    : Message {
        constructor() : this(text:"", Date(date:0), senderId:"")
}