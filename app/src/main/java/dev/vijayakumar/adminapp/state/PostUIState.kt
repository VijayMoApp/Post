package dev.vijayakumar.adminapp.state

import dev.vijayakumar.adminapp.local.PostEntity
import dev.vijayakumar.adminapp.network.model.PostResponse
import dev.vijayakumar.adminapp.network.model.PostResponseItem

sealed class PostUIState {
    data class Success(val data: List<PostEntity>) : PostUIState()
    data class Failure(val message: String) : PostUIState()
    object Loading : PostUIState()


}