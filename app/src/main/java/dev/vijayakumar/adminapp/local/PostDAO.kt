package dev.vijayakumar.adminapp.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(posts: PostEntity)

    @Query("SELECT * FROM posts")
    fun getPosts(): Flow<List<PostEntity>>

    @Delete
    suspend fun deletePost(post: PostEntity)
}