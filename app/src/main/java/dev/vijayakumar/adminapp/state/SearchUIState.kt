package dev.vijayakumar.adminapp.state

import dev.vijayakumar.adminapp.network.model.PostResponse
import dev.vijayakumar.adminapp.network.model.PostResponseItem

sealed class SearchUIState {
    data class Success(val data: PostResponse) : SearchUIState()
    data class Failure(val message: String) : SearchUIState()
    object Loading : SearchUIState()
    object Empty : SearchUIState()

}