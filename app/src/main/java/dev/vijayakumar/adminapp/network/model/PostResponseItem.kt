package dev.vijayakumar.adminapp.network.model

data class PostResponseItem(
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
)