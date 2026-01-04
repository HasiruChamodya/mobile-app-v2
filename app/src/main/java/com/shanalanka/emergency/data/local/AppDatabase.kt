package com.shanalanka.emergency.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ContactEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    // This gives the rest of the team access to the DAO commands
    abstract fun contactDao(): ContactDao
}