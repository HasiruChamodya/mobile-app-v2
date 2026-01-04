package com.shanalanka.emergency.data.local

import androidx.room.*
//import com.shanalanka.emergency.data.local.ContactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    // Member 6 will use this to show the list on the UI
    @Query("SELECT * FROM emergency_contacts ORDER BY name ASC")
    fun getAllContacts(): Flow<List<ContactEntity>>

    // This allows Member 1 or 6 to add new contacts to the local storage
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(contacts: List<ContactEntity>)

    @Delete
    suspend fun deleteContact(contact: ContactEntity)
}