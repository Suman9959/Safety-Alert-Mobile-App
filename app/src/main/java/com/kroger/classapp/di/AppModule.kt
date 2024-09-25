package com.kroger.classapp.di

import android.content.Context
import androidx.room.Room
import com.kroger.classapp.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule { // DB singleton, didn't know if name mattered, kept it safe
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "database-emergency"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideYourDao(db: AppDatabase) = db.emergencyEventDao()
    // Provide the ContactDao
    @Provides
    @Singleton
    fun provideContactDao(db: AppDatabase) = db.contactDao()
}
