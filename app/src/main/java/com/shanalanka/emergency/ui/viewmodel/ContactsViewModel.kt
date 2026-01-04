package com.shanalanka.emergency.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com. shanalanka.emergency.data. models.EmergencyContact
import com.shanalanka.emergency. data.repository.ContactRepository
import dagger. hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines. flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing emergency contacts.
 * Connects UI to the ContactRepository.
 */
@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactRepository: ContactRepository
) : ViewModel() {

    /**
     * Observable list of emergency contacts from database.
     * Updates automatically when contacts are added/deleted.
     */
    val contacts:  StateFlow<List<EmergencyContact>> =
        contactRepository.getAllContacts()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    /**
     * Add a new emergency contact to database.
     * @param name Contact's name
     * @param phoneNumber Contact's phone number
     */
    fun addContact(name: String, phoneNumber: String) {
        viewModelScope.launch {
            val contact = EmergencyContact(
                name = name,
                phoneNumber = phoneNumber
            )
            contactRepository.addContact(contact)
        }
    }

    /**
     * Delete an emergency contact from database.
     * @param contact The contact to delete
     */
    fun deleteContact(contact: EmergencyContact) {
        viewModelScope.launch {
            contactRepository.deleteContact(contact)
        }
    }
}