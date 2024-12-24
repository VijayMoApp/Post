package dev.vijayakumar.adminapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.vijayakumar.adminapp.local.PostEntity
import dev.vijayakumar.adminapp.network.model.PostResponse
import dev.vijayakumar.adminapp.network.model.PostResponseItem
import dev.vijayakumar.adminapp.repository.LocalRepository
import dev.vijayakumar.adminapp.repository.PostRepository
import dev.vijayakumar.adminapp.state.PostUIState
import dev.vijayakumar.adminapp.state.SearchUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: PostRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _postList = MutableStateFlow<SearchUIState>(SearchUIState.Loading)
    val postList: MutableStateFlow<SearchUIState> = _postList

    private val _localPostList = MutableStateFlow<PostUIState>(PostUIState.Loading)
    val localPostList: MutableStateFlow<PostUIState> = _localPostList

  fun getPostList(query: Int) {
        viewModelScope.launch {

             repository.getPostList(query)
                .onStart {
                    _postList.value = SearchUIState.Loading
                }
                .catch {e ->
                    _postList.value = SearchUIState.Failure(e.message ?: "Unknown error")
                }
                .collect {data ->
                    if (data.isEmpty()) {
                        _postList.value = SearchUIState.Empty
                    }else{
                        _postList.value = SearchUIState.Success(PostResponse(data))
                    }
                }

        }
    }

    fun postListFromDatabase() {
        viewModelScope.launch {

            localRepository.getPosts()
                .onStart {
                    _localPostList.value = PostUIState.Loading
                }
                .catch { e ->
                    _localPostList.value = PostUIState.Failure(e.message.toString())
                }
                .collect { result ->
                    _localPostList.value = PostUIState.Success(result)
                }
        }

    }

    fun savePostToDatabase(post: PostResponseItem) {
        viewModelScope.launch {
            val postEntity = PostEntity(
                id = post.id,
                name = post.name,
                body = post.body,
                email = post.email,
                postId = post.postId
            )
            localRepository.insertPost(postEntity)
        }
    }

fun deletePostFromDatabase(post: PostEntity) {
    viewModelScope.launch {
        localRepository.deletePost(post)
    }
}

}
