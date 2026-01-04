package com.shanalanka.emergency.data.repository

import com.shanalanka.emergency.data.local.dao.EmergencyGuideDao
import com.shanalanka.emergency.data.local.entity.EmergencyGuideEntity
import com.shanalanka.emergency.data.local.entity.GuideStepEntity
import com.shanalanka.emergency.data.local.entity.GuideWarningEntity
import com.shanalanka.emergency.data.model.EmergencyGuide
import com.shanalanka.emergency.data.model.GuideCategory
import com.shanalanka.emergency.data.model.GuideStep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for accessing emergency guide data.
 * Acts as a single source of truth for emergency guide information.
 */
@Singleton
class EmergencyGuideRepository @Inject constructor(
    private val guideDao: EmergencyGuideDao
) {
    
    /**
     * Get all emergency guides.
     * @return Flow of list of all guides
     */
    fun getAllGuides(): Flow<List<EmergencyGuide>> {
        return guideDao.getAllGuides().map { entities ->
            entities.map { entity ->
                entity.toEmergencyGuide(
                    steps = guideDao.getStepsForGuide(entity.id).map { it.toGuideStep() },
                    warnings = guideDao.getWarningsForGuide(entity.id).map { it.warningText }
                )
            }
        }
    }
    
    /**
     * Get guides by category.
     * @param category The category to filter by
     * @return Flow of list of guides in the specified category
     */
    fun getGuidesByCategory(category: GuideCategory): Flow<List<EmergencyGuide>> {
        return guideDao.getGuidesByCategory(category.name).map { entities ->
            entities.map { entity ->
                entity.toEmergencyGuide(
                    steps = guideDao.getStepsForGuide(entity.id).map { it.toGuideStep() },
                    warnings = guideDao.getWarningsForGuide(entity.id).map { it.warningText }
                )
            }
        }
    }
    
    /**
     * Get bookmarked guides.
     * @return Flow of list of bookmarked guides
     */
    fun getBookmarkedGuides(): Flow<List<EmergencyGuide>> {
        return guideDao.getBookmarkedGuides().map { entities ->
            entities.map { entity ->
                entity.toEmergencyGuide(
                    steps = guideDao.getStepsForGuide(entity.id).map { it.toGuideStep() },
                    warnings = guideDao.getWarningsForGuide(entity.id).map { it.warningText }
                )
            }
        }
    }
    
    /**
     * Search guides by title or description.
     * @param query Search query
     * @return Flow of list of matching guides
     */
    fun searchGuides(query: String): Flow<List<EmergencyGuide>> {
        return guideDao.searchGuides(query).map { entities ->
            entities.map { entity ->
                entity.toEmergencyGuide(
                    steps = guideDao.getStepsForGuide(entity.id).map { it.toGuideStep() },
                    warnings = guideDao.getWarningsForGuide(entity.id).map { it.warningText }
                )
            }
        }
    }
    
    /**
     * Get a guide by ID.
     * @param guideId Guide ID
     * @return The guide, or null if not found
     */
    suspend fun getGuideById(guideId: String): EmergencyGuide? {
        val entity = guideDao.getGuideById(guideId) ?: return null
        return entity.toEmergencyGuide(
            steps = guideDao.getStepsForGuide(entity.id).map { it.toGuideStep() },
            warnings = guideDao.getWarningsForGuide(entity.id).map { it.warningText }
        )
    }
    
    /**
     * Toggle bookmark status for a guide.
     * @param guideId Guide ID
     * @param isBookmarked New bookmark status
     */
    suspend fun updateBookmarkStatus(guideId: String, isBookmarked: Boolean) {
        guideDao.updateBookmarkStatus(guideId, isBookmarked)
    }
    
    /**
     * Get count of guides in database.
     * @return Count of guides
     */
    suspend fun getGuideCount(): Int {
        return guideDao.getGuideCount()
    }
}

/**
 * Extension function to convert entity to domain model.
 */
private fun EmergencyGuideEntity.toEmergencyGuide(
    steps: List<GuideStep>,
    warnings: List<String>
): EmergencyGuide {
    return EmergencyGuide(
        id = id,
        title = title,
        category = GuideCategory.fromString(category) ?: GuideCategory.OTHER,
        description = description,
        steps = steps,
        warnings = warnings,
        whenToCallEmergency = whenToCallEmergency,
        iconRes = iconRes,
        isBookmarked = isBookmarked
    )
}

/**
 * Extension function to convert step entity to domain model.
 */
private fun GuideStepEntity.toGuideStep(): GuideStep {
    return GuideStep(
        stepNumber = stepNumber,
        title = title,
        description = description,
        imageRes = imageRes
    )
}
