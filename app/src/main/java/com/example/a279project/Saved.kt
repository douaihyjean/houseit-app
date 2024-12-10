package com.example.a279project

data class SavedCreate(
    val user_id: String,
    val listing_id: Int
)

data class Saved(
    val saved_id: Int,
    val user_id: String,
    val listing_id: Int
)