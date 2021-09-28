package com.kanyideveloper.savingszetu.di

import com.kanyideveloper.savingszetu.data.AuthRepository
import com.kanyideveloper.savingszetu.data.DefaultAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Singleton


//Lives as long as the Activity lives

@Module
@InstallIn(ActivityComponent::class)
object AuthModule {

    @ActivityScoped
    @Provides
    fun providesAuthRepository() = DefaultAuthRepository() as AuthRepository
}