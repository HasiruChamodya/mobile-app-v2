package com.shanalanka.emergency.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shanalanka.emergency.data.model.District
import com.shanalanka.emergency.data.model.PoliceStation

/**
 * Room database entity for police stations.
 */
@Entity(tableName = "police_stations")
data class PoliceStationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val district: String,
    val phoneNumber: String,
    val address: String? = null
)

/**
 * Extension function to convert entity to domain model.
 */
fun PoliceStationEntity.toPoliceStation(): PoliceStation {
    return PoliceStation(
        id = id,
        name = name,
        district = District.fromString(district) ?: District.COLOMBO,
        phoneNumber = phoneNumber,
        address = address
    )
}

/**
 * Extension function to convert domain model to entity.
 */
fun PoliceStation.toEntity(): PoliceStationEntity {
    return PoliceStationEntity(
        id = id,
        name = name,
        district = district.name,
        phoneNumber = phoneNumber,
        address = address
    )
}
