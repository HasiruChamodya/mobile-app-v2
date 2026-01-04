package com.shanalanka.emergency.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shanalanka.emergency.data.local.dao.EmergencyGuideDao
import com.shanalanka.emergency.data.local.dao.PoliceStationDao
import com.shanalanka.emergency.data.local.entity.EmergencyGuideEntity
import com.shanalanka.emergency.data.local.entity.GuideStepEntity
import com.shanalanka.emergency.data.local.entity.GuideWarningEntity
import com.shanalanka.emergency.data.local.entity.PoliceStationEntity

@Database(
    entities = [
        ContactEntity::class,
        PoliceStationEntity::class,
        EmergencyGuideEntity::class,
        GuideStepEntity::class,
        GuideWarningEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    // This gives the rest of the team access to the DAO commands
    abstract fun contactDao(): ContactDao
    abstract fun policeStationDao(): PoliceStationDao
    abstract fun emergencyGuideDao(): EmergencyGuideDao
}