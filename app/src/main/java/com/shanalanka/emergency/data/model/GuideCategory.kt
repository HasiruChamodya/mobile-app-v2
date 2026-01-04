package com.shanalanka.emergency.data.model

/**
 * Enum representing categories of emergency guides.
 */
enum class GuideCategory(val displayName: String) {
    BREATHING("Breathing"),
    INJURIES("Injuries"),
    MEDICAL("Medical"),
    ENVIRONMENTAL("Environmental"),
    OTHER("Other");

    companion object {
        fun fromString(value: String): GuideCategory? {
            return entries.find { it.name.equals(value, ignoreCase = true) }
        }
    }
}
