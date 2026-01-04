package com.shanalanka.emergency.data.repository

import com.shanalanka.emergency.data.local.ContactDao
import com.shanalanka.emergency.data.local.ContactEntity
import com.shanalanka.emergency.data.models.EmergencyContact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for managing emergency contacts.
 * Provides access to contact data from the local database.
 */
@Singleton
class ContactRepository @Inject constructor(
    private val contactDao: ContactDao
) {
    
    /**
     * Get all emergency contacts as a Flow.
     * Automatically converts from database entities to UI models.
     */
    fun getAllContacts(): Flow<List<EmergencyContact>> {
        return contactDao.getAllContacts().map { entities ->
            entities.map { it.toEmergencyContact() }
        }
    }
    
    /**
     * Add a new emergency contact.
     */
    suspend fun addContact(contact: EmergencyContact) {
        contactDao.insertContacts(listOf(contact.toEntity()))
    }
    
    /**
     * Delete an emergency contact.
     */
    suspend fun deleteContact(contact: EmergencyContact) {
        contactDao.deleteContact(contact.toEntity())
    }
    
    /**
     * Convert ContactEntity to EmergencyContact model.
     */
    private fun ContactEntity.toEmergencyContact(): EmergencyContact {
        return EmergencyContact(
            id = id,
            name = name,
            phoneNumber = phoneNumber
        )
    }
    
    /**
     * Convert EmergencyContact model to ContactEntity.
     */
    private fun EmergencyContact.toEntity(): ContactEntity {
        return ContactEntity(
            id = id,
            name = name,
            phoneNumber = phoneNumber,
            category = "",
            district = ""
        )
    }
}
