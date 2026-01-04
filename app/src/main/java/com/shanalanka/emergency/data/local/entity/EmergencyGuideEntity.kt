package com.shanalanka.emergency.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shanalanka.emergency.data.model.GuideCategory

/**
 * Room database entity for emergency guides.
 */
@Entity(tableName = "emergency_guides")
data class EmergencyGuideEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val whenToCallEmergency: String,
    val iconRes: Int,
    val isBookmarked: Boolean = false
)

/**
 * Room database entity for guide steps.
 * Links to EmergencyGuideEntity via guideId foreign key.
 */
@Entity(tableName = "guide_steps")
data class GuideStepEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val guideId: String,
    val stepNumber: Int,
    val title: String,
    val description: String,
    val imageRes: Int? = null
)

/**
 * Room database entity for guide warnings.
 * Links to EmergencyGuideEntity via guideId foreign key.
 */
@Entity(tableName = "guide_warnings")
data class GuideWarningEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val guideId: String,
    val warningText: String
)
