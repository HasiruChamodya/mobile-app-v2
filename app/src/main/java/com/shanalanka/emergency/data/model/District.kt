package com.shanalanka.emergency.data.model

/**
 * Enum representing all 25 districts of Sri Lanka.
 * Used for categorizing police stations and emergency contacts.
 */
enum class District(val displayName: String) {
    COLOMBO("Colombo"),
    GAMPAHA("Gampaha"),
    KALUTARA("Kalutara"),
    KANDY("Kandy"),
    MATALE("Matale"),
    NUWARA_ELIYA("Nuwara Eliya"),
    GALLE("Galle"),
    MATARA("Matara"),
    HAMBANTOTA("Hambantota"),
    JAFFNA("Jaffna"),
    KILINOCHCHI("Kilinochchi"),
    MANNAR("Mannar"),
    VAVUNIYA("Vavuniya"),
    MULLAITIVU("Mullaitivu"),
    BATTICALOA("Batticaloa"),
    AMPARA("Ampara"),
    TRINCOMALEE("Trincomalee"),
    KURUNEGALA("Kurunegala"),
    PUTTALAM("Puttalam"),
    ANURADHAPURA("Anuradhapura"),
    POLONNARUWA("Polonnaruwa"),
    BADULLA("Badulla"),
    MONARAGALA("Monaragala"),
    RATNAPURA("Ratnapura"),
    KEGALLE("Kegalle");

    companion object {
        fun fromString(value: String): District? {
            return entries.find { it.name.equals(value, ignoreCase = true) }
        }
    }
}
