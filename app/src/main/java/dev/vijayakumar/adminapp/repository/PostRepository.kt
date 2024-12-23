package dev.vijayakumar.adminapp.repository

import dev.vijayakumar.adminapp.network.PostServices
import dev.vijayakumar.adminapp.network.model.PostResponse
import dev.vijayakumar.adminapp.network.model.PostResponseItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostRepository @Inject constructor(private val apiService: PostServices) {




        fun getPostList(postId: Int): Flow<List<PostResponseItem>> = flow {
            val response = apiService.getPosts(postId)
            emit(response)
        }.catch{
            emit(emptyList())
        }





}