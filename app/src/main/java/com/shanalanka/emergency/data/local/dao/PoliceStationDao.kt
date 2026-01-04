package com.shanalanka.emergency.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shanalanka.emergency.data.local.entity.PoliceStationEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for police stations.
 * Provides methods to interact with the police_stations table.
 */
@Dao
interface PoliceStationDao {
    
    /**
     * Get all police stations.
     * @return Flow of list of all police stations
     */
    @Query("SELECT * FROM police_stations ORDER BY district, name")
    fun getAllPoliceStations(): Flow<List<PoliceStationEntity>>
    
    /**
     * Get police stations by district.
     * @param district Name of the district
     * @return Flow of list of police stations in the specified district
     */
    @Query("SELECT * FROM police_stations WHERE district = :district ORDER BY name")
    fun getPoliceStationsByDistrict(district: String): Flow<List<PoliceStationEntity>>
    
    /**
     * Search police stations by name.
     * @param query Search query
     * @return Flow of list of matching police stations
     */
    @Query("SELECT * FROM police_stations WHERE name LIKE '%' || :query || '%' ORDER BY name")
    fun searchPoliceStations(query: String): Flow<List<PoliceStationEntity>>
    
    /**
     * Insert a police station.
     * @param station Police station to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoliceStation(station: PoliceStationEntity)
    
    /**
     * Insert multiple police stations.
     * @param stations List of police stations to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPoliceStations(stations: List<PoliceStationEntity>)
    
    /**
     * Get count of police stations.
     * @return Count of police stations
     */
    @Query("SELECT COUNT(*) FROM police_stations")
    suspend fun getPoliceStationCount(): Int
}
