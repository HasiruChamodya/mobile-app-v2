package com.shanalanka.emergency.data.models

/**
 * Data model for emergency contacts in the app.
 * This is a UI model that wraps the database ContactEntity for easier use in composables.
 */
data class EmergencyContact(
    val id: Int = 0,
    val name: String,
    val phoneNumber: String
) {
    companion object {
        const val MAX_CONTACTS = 5
    }
}
