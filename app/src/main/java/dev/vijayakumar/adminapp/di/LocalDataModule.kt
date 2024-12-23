package dev.vijayakumar.adminapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.vijayakumar.adminapp.local.PostDAO
import dev.vijayakumar.adminapp.local.PostDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PostDatabase {
        return Room.databaseBuilder(
            context,
            PostDatabase::class.java,
            "post_database"
        ).build()

    }


    @Provides
    @Singleton
    fun providePostDAO(database: PostDatabase) :PostDAO{
        return database.postDAO

    }

}