package dev.vijayakumar.adminapp.repository

import dev.vijayakumar.adminapp.local.PostDAO
import dev.vijayakumar.adminapp.local.PostEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalRepository @Inject constructor(private val postDAO: PostDAO) {

  suspend fun insertPost(post: PostEntity) {
       postDAO.insertPost(post)
    }

     fun getPosts(): Flow<List<PostEntity>> {
        return postDAO.getPosts()
    }

    suspend fun deletePost(post: PostEntity) {
        postDAO.deletePost(post)

    }

}