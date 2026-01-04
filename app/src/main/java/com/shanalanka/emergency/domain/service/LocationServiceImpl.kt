package com.shanalanka.emergency.domain.service

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import kotlin.coroutines.resume

/**
 * Implementation of LocationService using Google Play Services.
 * Provides offline-capable GPS location fetching.
 */
class LocationServiceImpl @Inject constructor(
    private val context: Context
) : LocationService {
    
    companion object {
        private const val LOCATION_TIMEOUT_MS = 10_000L // 10 seconds
    }
    
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    
    private val locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    
    override suspend fun getCurrentLocation(): LocationResult {
        if (!hasLocationPermission()) {
            return LocationResult.Error("Location permission not granted")
        }
        
        if (!isLocationEnabled()) {
            return LocationResult.Error("GPS is disabled. Please enable it in settings.")
        }
        
        return try {
            // Try to get location with configured timeout
            val location = withTimeoutOrNull(LOCATION_TIMEOUT_MS) {
                suspendCancellableCoroutine { continuation ->
                    val locationRequest = LocationRequest.Builder(
                        Priority.PRIORITY_HIGH_ACCURACY,
                        1000L
                    ).apply {
                        setMaxUpdates(1)
                        setWaitForAccurateLocation(false)
                    }.build()
                    
                    val callback = object : LocationCallback() {
                        override fun onLocationResult(result: com.google.android.gms.location.LocationResult) {
                            val location = result.lastLocation
                            if (location != null) {
                                continuation.resume(
                                    LocationResult.Success(
                                        latitude = location.latitude,
                                        longitude = location.longitude
                                    )
                                )
                            } else {
                                continuation.resume(
                                    LocationResult.Error("Could not get current location")
                                )
                            }
                        }
                    }
                    
                    try {
                        fusedLocationClient.requestLocationUpdates(
                            locationRequest,
                            callback,
                            Looper.getMainLooper()
                        ).addOnCompleteListener {
                            if (!it.isSuccessful) {
                                continuation.resume(
                                    LocationResult.Error("Failed to request location updates")
                                )
                            }
                        }
                        
                        continuation.invokeOnCancellation {
                            fusedLocationClient.removeLocationUpdates(callback)
                        }
                    } catch (e: SecurityException) {
                        continuation.resume(
                            LocationResult.Error("Location permission denied")
                        )
                    }
                }
            }
            
            location ?: LocationResult.Error("Location request timed out after 10 seconds")
            
        } catch (e: Exception) {
            LocationResult.Error("Error getting location: ${e.message}")
        }
    }
    
    override suspend fun getLastKnownLocation(): LocationResult {
        if (!hasLocationPermission()) {
            return LocationResult.Error("Location permission not granted")
        }
        
        return try {
            suspendCancellableCoroutine { continuation ->
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        continuation.resume(
                            LocationResult.Success(
                                latitude = location.latitude,
                                longitude = location.longitude
                            )
                        )
                    } else {
                        continuation.resume(
                            LocationResult.Error("No last known location available")
                        )
                    }
                }.addOnFailureListener { e ->
                    continuation.resume(
                        LocationResult.Error("Error getting last location: ${e.message}")
                    )
                }
            }
        } catch (e: SecurityException) {
            LocationResult.Error("Location permission denied")
        } catch (e: Exception) {
            LocationResult.Error("Error getting last location: ${e.message}")
        }
    }
    
    override fun isLocationEnabled(): Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
               locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
    
    override fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}
