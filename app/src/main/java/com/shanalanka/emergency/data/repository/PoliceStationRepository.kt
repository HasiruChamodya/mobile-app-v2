package com.shanalanka.emergency.data.repository

import com.shanalanka.emergency.data.local.dao.PoliceStationDao
import com.shanalanka.emergency.data.local.entity.toPoliceStation
import com.shanalanka.emergency.data.model.District
import com.shanalanka.emergency.data.model.PoliceStation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for accessing police station data.
 * Acts as a single source of truth for police station information.
 */
@Singleton
class PoliceStationRepository @Inject constructor(
    private val policeStationDao: PoliceStationDao
) {
    
    /**
     * Get all police stations.
     * @return Flow of list of all police stations
     */
    fun getAllPoliceStations(): Flow<List<PoliceStation>> {
        return policeStationDao.getAllPoliceStations()
            .map { entities -> entities.map { it.toPoliceStation() } }
    }
    
    /**
     * Get police stations by district.
     * @param district The district to filter by
     * @return Flow of list of police stations in the specified district
     */
    fun getPoliceStationsByDistrict(district: District): Flow<List<PoliceStation>> {
        return policeStationDao.getPoliceStationsByDistrict(district.name)
            .map { entities -> entities.map { it.toPoliceStation() } }
    }
    
    /**
     * Search police stations by name.
     * @param query Search query
     * @return Flow of list of matching police stations
     */
    fun searchPoliceStations(query: String): Flow<List<PoliceStation>> {
        return policeStationDao.searchPoliceStations(query)
            .map { entities -> entities.map { it.toPoliceStation() } }
    }
    
    /**
     * Get count of police stations in database.
     * @return Count of police stations
     */
    suspend fun getPoliceStationCount(): Int {
        return policeStationDao.getPoliceStationCount()
    }
}
