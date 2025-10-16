package com.uncannyvalley.cookflow.di

import com.uncannyvalley.cookflow.data.local.dao.RecipeDao
import com.uncannyvalley.cookflow.data.remote.api.RecipeApi
import com.uncannyvalley.cookflow.data.repository.RecipeRepositoryImpl
import com.uncannyvalley.cookflow.domain.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRecipeRepository(
        api: RecipeApi,
        db: RecipeDao
    ): RecipeRepository {
        return RecipeRepositoryImpl(api, db)
    }
}