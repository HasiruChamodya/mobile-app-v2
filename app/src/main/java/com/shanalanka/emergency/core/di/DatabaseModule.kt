package com.shanalanka.emergency.core.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.shanalanka.emergency.data.local.AppDatabase
import com.shanalanka.emergency.data.local.ContactDao
import com.shanalanka.emergency.data.local.dao.EmergencyGuideDao
import com.shanalanka.emergency.data.local.dao.PoliceStationDao
import com.shanalanka.emergency.data.repository.ContactRepository
import com.shanalanka.emergency.data.repository.SettingsRepository
import com.shanalanka.emergency.data.source.EmergencyGuidesData
import com.shanalanka.emergency.data.source.PoliceStationData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // This makes the "pipes" available to the whole app
object DatabaseModule {

    // Migration from version 2 to 3: Remove default pre-populated contacts
    private val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Remove default contacts that were previously pre-populated (119, 1990)
            database.execSQL("DELETE FROM emergency_contacts WHERE phoneNumber IN ('119', '1990')")
        }
    }

    @Provides
    @Singleton // Only one database instance is ever created (saves battery/RAM)
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "sahana_lanka_db"
        )
            .addMigrations(MIGRATION_2_3)
            .fallbackToDestructiveMigration() // Useful for students if you change the table later
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // Pre-populate database on first creation
                    CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
                        val database = Room.databaseBuilder(
                            context,
                            AppDatabase::class.java,
                            "sahana_lanka_db"
                        ).build()
                        
                        // Pre-populate police stations
                        val policeDao = database.policeStationDao()
                        if (policeDao.getPoliceStationCount() == 0) {
                            policeDao.insertAllPoliceStations(PoliceStationData.getPoliceStations())
                        }
                        
                        // Pre-populate emergency guides
                        val guideDao = database.emergencyGuideDao()
                        if (guideDao.getGuideCount() == 0) {
                            guideDao.insertAllGuides(EmergencyGuidesData.getGuides())
                            guideDao.insertAllSteps(EmergencyGuidesData.getSteps())
                            guideDao.insertAllWarnings(EmergencyGuidesData.getWarnings())
                        }
                    }
                }
            })
            .build()
    }

    @Provides
    fun provideContactDao(db: AppDatabase): ContactDao {
        return db.contactDao() // Now Member 6 can get the Dao automatically
    }
    
    @Provides
    fun providePoliceStationDao(db: AppDatabase): PoliceStationDao {
        return db.policeStationDao()
    }
    
    @Provides
    fun provideEmergencyGuideDao(db: AppDatabase): EmergencyGuideDao {
        return db.emergencyGuideDao()
    }
    
    @Provides
    @Singleton
    fun provideContactRepository(contactDao: ContactDao): ContactRepository {
        return ContactRepository(contactDao)
    }
    
    @Provides
    @Singleton
    fun provideSettingsRepository(
        @ApplicationContext context: Context
    ): SettingsRepository {
        return SettingsRepository(context)
    }
}

