package dev.vijayakumar.adminapp.network


import dev.vijayakumar.adminapp.network.model.PostResponse
import dev.vijayakumar.adminapp.network.model.PostResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

interface PostServices {
    @GET("comments")
    suspend fun getPosts (@Query("postId") postId: Int): List<PostResponseItem>

}