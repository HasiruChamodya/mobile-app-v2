package com.shanalanka.emergency.data.model

/**
 * Data model representing an emergency guide.
 * 
 * @property id Unique identifier for the guide
 * @property title Title of the guide
 * @property category Category of the emergency
 * @property description Brief description of the guide
 * @property steps List of steps to follow
 * @property warnings List of warnings and things to avoid
 * @property whenToCallEmergency Description of when to call emergency services
 * @property iconRes Resource ID for the guide icon
 * @property isBookmarked Whether the user has bookmarked this guide
 */
data class EmergencyGuide(
    val id: String,
    val title: String,
    val category: GuideCategory,
    val description: String,
    val steps: List<GuideStep>,
    val warnings: List<String>,
    val whenToCallEmergency: String,
    val iconRes: Int,
    val isBookmarked: Boolean = false
)
