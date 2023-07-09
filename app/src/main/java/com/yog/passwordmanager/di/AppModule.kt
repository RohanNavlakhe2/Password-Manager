package com.yog.passwordmanager.di

import android.app.Application
import androidx.room.Room
import com.yog.passwordmanager.data.PasswordManagerRepoImpl
import com.yog.passwordmanager.db.PasswordManagerDatabase
import com.yog.passwordmanager.domain.PasswordManagerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePasswordManagerDatabase(app:Application):PasswordManagerDatabase{
        return Room.databaseBuilder(
            app,
            PasswordManagerDatabase::class.java,
            PasswordManagerDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providePasswordManageRepository(database: PasswordManagerDatabase):PasswordManagerRepository{
        return PasswordManagerRepoImpl(database.passwordManagerDao)
    }

    @Provides
    @Singleton
    fun provideIODispatcher():CoroutineDispatcher{
        return Dispatchers.IO
    }

}