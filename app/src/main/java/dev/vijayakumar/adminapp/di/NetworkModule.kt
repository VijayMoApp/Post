package dev.vijayakumar.adminapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.vijayakumar.adminapp.network.PostServices
import dev.vijayakumar.adminapp.repository.PostRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    //Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



    //API Services
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): PostServices {
        return retrofit.create(PostServices::class.java)
    }


    //Repository
    @Provides
    @Singleton
    fun provideRepository(apiService: PostServices): PostRepository {
        return PostRepository(apiService)
    }


}