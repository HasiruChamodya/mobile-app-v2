package com.shanalanka.emergency.domain.service

/**
 * Service interface for GPS location operations.
 * Provides offline-capable location fetching using GPS satellites.
 */
interface LocationService {
    /**
     * Get current GPS location.
     * Uses high accuracy mode with 10 second timeout.
     * @return LocationResult with coordinates or error
     */
    suspend fun getCurrentLocation(): LocationResult
    
    /**
     * Get last known location as fallback.
     * @return LocationResult with coordinates or error
     */
    suspend fun getLastKnownLocation(): LocationResult
    
    /**
     * Check if location services (GPS) are enabled.
     * @return true if GPS is enabled, false otherwise
     */
    fun isLocationEnabled(): Boolean
    
    /**
     * Check if location permission is granted.
     * @return true if permission granted, false otherwise
     */
    fun hasLocationPermission(): Boolean
}

/**
 * Result wrapper for location operations.
 */
sealed class LocationResult {
    /**
     * Success result with coordinates.
     * @param latitude Decimal degrees (e.g., 6.9271)
     * @param longitude Decimal degrees (e.g., 79.8612)
     */
    data class Success(val latitude: Double, val longitude: Double) : LocationResult()
    
    /**
     * Error result with message.
     * @param message Error description
     */
    data class Error(val message: String) : LocationResult()
}
