package dev.vijayakumar.adminapp.network.model

data class PostResponse (
    val post : List<PostResponseItem> = emptyList()
)