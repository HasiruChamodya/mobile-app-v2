package com.shanalanka.emergency.core.di

import android.content.Context
import androidx.room.Room
import com.shanalanka.emergency.data.local.AppDatabase
import com.shanalanka.emergency.data.local.ContactDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // This makes the "pipes" available to the whole app
object DatabaseModule {

    @Provides
    @Singleton // Only one database instance is ever created (saves battery/RAM)
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "sahana_lanka_db"
        ).fallbackToDestructiveMigration() // Useful for students if you change the table later
            .build()
    }

    @Provides
    fun provideContactDao(db: AppDatabase): ContactDao {
        return db.contactDao() // Now Member 6 can get the Dao automatically
    }
}

