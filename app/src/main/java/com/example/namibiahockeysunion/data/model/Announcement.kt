package com.example.namibiahockeysunion.data.model

data class Announcement(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
