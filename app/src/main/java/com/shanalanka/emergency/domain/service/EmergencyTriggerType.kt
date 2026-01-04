package com.shanalanka.emergency.domain.service

/**
 * Types of emergency alert triggers.
 */
enum class EmergencyTriggerType {
    BUTTON,      // Manual button press
    SHAKE,       // Shake detection
    LOW_BATTERY  // Low battery auto-trigger
}
