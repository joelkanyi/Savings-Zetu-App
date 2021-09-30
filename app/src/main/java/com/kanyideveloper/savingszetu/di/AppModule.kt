package com.kanyideveloper.savingszetu.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kanyideveloper.savingszetu.R
import com.kanyideveloper.savingszetu.data.AuthRepository
import com.kanyideveloper.savingszetu.data.DefaultAuthRepository
import com.kanyideveloper.savingszetu.data.DefaultMainRepository
import com.kanyideveloper.savingszetu.data.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesApplicationContext(@ApplicationContext context: Context) = context

    @Singleton
    @Provides
    fun providesGlideInstance(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(RequestOptions()
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
        )

    @Singleton
    @Provides
    fun providesDispatcher() = Dispatchers.Main as CoroutineDispatcher

    @Singleton
    @Provides
    fun providesAuthRepository() : AuthRepository {
        return DefaultAuthRepository()
    }

    @Singleton
    @Provides
    fun providesMainRepository() : MainRepository {
        return DefaultMainRepository()
    }
}