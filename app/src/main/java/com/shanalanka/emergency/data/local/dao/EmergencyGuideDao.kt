package com.shanalanka.emergency.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.shanalanka.emergency.data.local.entity.EmergencyGuideEntity
import com.shanalanka.emergency.data.local.entity.GuideStepEntity
import com.shanalanka.emergency.data.local.entity.GuideWarningEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for emergency guides.
 * Provides methods to interact with the emergency_guides, guide_steps, and guide_warnings tables.
 */
@Dao
interface EmergencyGuideDao {
    
    /**
     * Get all emergency guides.
     * @return Flow of list of all emergency guides
     */
    @Query("SELECT * FROM emergency_guides ORDER BY category, title")
    fun getAllGuides(): Flow<List<EmergencyGuideEntity>>
    
    /**
     * Get emergency guides by category.
     * @param category Category name
     * @return Flow of list of guides in the specified category
     */
    @Query("SELECT * FROM emergency_guides WHERE category = :category ORDER BY title")
    fun getGuidesByCategory(category: String): Flow<List<EmergencyGuideEntity>>
    
    /**
     * Get bookmarked guides.
     * @return Flow of list of bookmarked guides
     */
    @Query("SELECT * FROM emergency_guides WHERE isBookmarked = 1 ORDER BY title")
    fun getBookmarkedGuides(): Flow<List<EmergencyGuideEntity>>
    
    /**
     * Search guides by title or description.
     * @param query Search query
     * @return Flow of list of matching guides
     */
    @Query("SELECT * FROM emergency_guides WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' ORDER BY title")
    fun searchGuides(query: String): Flow<List<EmergencyGuideEntity>>
    
    /**
     * Get a specific guide by ID.
     * @param guideId Guide ID
     * @return Guide entity
     */
    @Query("SELECT * FROM emergency_guides WHERE id = :guideId")
    suspend fun getGuideById(guideId: String): EmergencyGuideEntity?
    
    /**
     * Get steps for a guide.
     * @param guideId Guide ID
     * @return List of steps for the guide
     */
    @Query("SELECT * FROM guide_steps WHERE guideId = :guideId ORDER BY stepNumber")
    suspend fun getStepsForGuide(guideId: String): List<GuideStepEntity>
    
    /**
     * Get warnings for a guide.
     * @param guideId Guide ID
     * @return List of warnings for the guide
     */
    @Query("SELECT * FROM guide_warnings WHERE guideId = :guideId")
    suspend fun getWarningsForGuide(guideId: String): List<GuideWarningEntity>
    
    /**
     * Insert a guide.
     * @param guide Guide to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGuide(guide: EmergencyGuideEntity)
    
    /**
     * Insert multiple guides.
     * @param guides List of guides to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllGuides(guides: List<EmergencyGuideEntity>)
    
    /**
     * Insert a guide step.
     * @param step Step to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStep(step: GuideStepEntity)
    
    /**
     * Insert multiple guide steps.
     * @param steps List of steps to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSteps(steps: List<GuideStepEntity>)
    
    /**
     * Insert a guide warning.
     * @param warning Warning to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWarning(warning: GuideWarningEntity)
    
    /**
     * Insert multiple guide warnings.
     * @param warnings List of warnings to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllWarnings(warnings: List<GuideWarningEntity>)
    
    /**
     * Update guide bookmark status.
     * @param guideId Guide ID
     * @param isBookmarked New bookmark status
     */
    @Query("UPDATE emergency_guides SET isBookmarked = :isBookmarked WHERE id = :guideId")
    suspend fun updateBookmarkStatus(guideId: String, isBookmarked: Boolean)
    
    /**
     * Get count of guides.
     * @return Count of guides
     */
    @Query("SELECT COUNT(*) FROM emergency_guides")
    suspend fun getGuideCount(): Int
}
