package com.shanalanka.emergency.data.repository

import com.shanalanka.emergency.data.local.ContactDao
import com.shanalanka.emergency.data.local.ContactEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val contactDao: ContactDao
) {
    // Member 6 (Directory) will use this to observe the contact list
    fun getAllLocalContacts(): Flow<List<ContactEntity>> = contactDao.getAllContacts()

    // Used to pre-populate or add new emergency numbers
    suspend fun saveContactLocally(contact: ContactEntity) {
        contactDao.insertContacts(listOf(contact))
    }
}