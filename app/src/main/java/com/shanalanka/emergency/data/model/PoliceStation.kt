package com.shanalanka.emergency.data.model

/**
 * Data model representing a police station.
 * 
 * @property id Unique identifier for the police station
 * @property name Name of the police station
 * @property district District where the station is located
 * @property phoneNumber Contact phone number
 * @property address Optional physical address
 */
data class PoliceStation(
    val id: Int,
    val name: String,
    val district: District,
    val phoneNumber: String,
    val address: String? = null
)
