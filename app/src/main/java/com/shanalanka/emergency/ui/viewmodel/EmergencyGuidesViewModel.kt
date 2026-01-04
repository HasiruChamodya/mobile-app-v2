package com.shanalanka.emergency.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shanalanka.emergency.data.model.EmergencyGuide
import com.shanalanka.emergency.data.model.GuideCategory
import com.shanalanka.emergency.data.repository.EmergencyGuideRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for Emergency Guides screens.
 * Manages guide data, filtering, search, and bookmarks.
 */
@HiltViewModel
class EmergencyGuidesViewModel @Inject constructor(
    private val repository: EmergencyGuideRepository
) : ViewModel() {
    
    // UI State
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _selectedCategory = MutableStateFlow<GuideCategory?>(null)
    val selectedCategory: StateFlow<GuideCategory?> = _selectedCategory.asStateFlow()
    
    private val _isSearchActive = MutableStateFlow(false)
    val isSearchActive: StateFlow<Boolean> = _isSearchActive.asStateFlow()
    
    // All guides from repository
    private val allGuides = repository.getAllGuides()
    
    // Bookmarked guides
    val bookmarkedGuides: StateFlow<List<EmergencyGuide>> = repository.getBookmarkedGuides()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    // Filtered and searched guides
    val guides: StateFlow<List<EmergencyGuide>> = combine(
        allGuides,
        _selectedCategory,
        _searchQuery
    ) { guides, category, query ->
        var filtered = guides
        
        // Filter by category if selected
        if (category != null) {
            filtered = filtered.filter { it.category == category }
        }
        
        // Search by query if not empty
        if (query.isNotBlank()) {
            filtered = filtered.filter { guide ->
                guide.title.contains(query, ignoreCase = true) ||
                guide.description.contains(query, ignoreCase = true) ||
                guide.steps.any { step ->
                    step.title.contains(query, ignoreCase = true) ||
                    step.description.contains(query, ignoreCase = true)
                }
            }
        }
        
        filtered
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    
    /**
     * Update search query.
     */
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    /**
     * Select a category filter.
     */
    fun selectCategory(category: GuideCategory?) {
        _selectedCategory.value = category
    }
    
    /**
     * Toggle search active state.
     */
    fun toggleSearch() {
        _isSearchActive.value = !_isSearchActive.value
        if (!_isSearchActive.value) {
            _searchQuery.value = ""
        }
    }
    
    /**
     * Toggle bookmark status for a guide.
     */
    fun toggleBookmark(guideId: String, isBookmarked: Boolean) {
        viewModelScope.launch {
            repository.updateBookmarkStatus(guideId, !isBookmarked)
        }
    }
    
    /**
     * Clear all filters.
     */
    fun clearFilters() {
        _selectedCategory.value = null
        _searchQuery.value = ""
    }
    
    /**
     * Get a specific guide by ID.
     */
    suspend fun getGuideById(guideId: String): EmergencyGuide? {
        return repository.getGuideById(guideId)
    }
}
