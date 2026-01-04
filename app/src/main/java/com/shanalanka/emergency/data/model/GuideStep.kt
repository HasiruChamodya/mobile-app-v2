package com.shanalanka.emergency.data.model

/**
 * Data model for a step in an emergency guide.
 * 
 * @property stepNumber The sequence number of the step
 * @property title Brief title of the step
 * @property description Detailed description of what to do
 * @property imageRes Optional resource ID for an illustration
 */
data class GuideStep(
    val stepNumber: Int,
    val title: String,
    val description: String,
    val imageRes: Int? = null
)
