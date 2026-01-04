package com.shanalanka.emergency.util

/**
 * Utility functions for formatting location coordinates.
 */
object LocationFormatter {
    /**
     * Format latitude to 6 decimal places for display.
     * Provides ~0.1 meter accuracy.
     */
    fun formatLatitude(latitude: Double): String {
        return String.format("%.6f", latitude)
    }
    
    /**
     * Format longitude to 6 decimal places for display.
     * Provides ~0.1 meter accuracy.
     */
    fun formatLongitude(longitude: Double): String {
        return String.format("%.6f", longitude)
    }
    
    /**
     * Format coordinates as a display string.
     * Format: "lat, lon"
     */
    fun formatCoordinates(latitude: Double, longitude: Double): String {
        return "${formatLatitude(latitude)}, ${formatLongitude(longitude)}"
    }
}
