package dev.vijayakumar.adminapp.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val postId: Int,
    val name: String,
    val body: String,
    val email: String
)
