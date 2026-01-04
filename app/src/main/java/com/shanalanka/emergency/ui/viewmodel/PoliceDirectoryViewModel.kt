package com.shanalanka.emergency.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shanalanka.emergency.data.model.District
import com.shanalanka.emergency.data.model.PoliceStation
import com.shanalanka.emergency.data.repository.PoliceStationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel for Police Directory screen.
 * Manages police station data, filtering, and search.
 */
@HiltViewModel
class PoliceDirectoryViewModel @Inject constructor(
    private val repository: PoliceStationRepository
) : ViewModel() {
    
    // UI State
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _selectedDistrict = MutableStateFlow<District?>(null)
    val selectedDistrict: StateFlow<District?> = _selectedDistrict.asStateFlow()
    
    private val _isSearchActive = MutableStateFlow(false)
    val isSearchActive: StateFlow<Boolean> = _isSearchActive.asStateFlow()
    
    // All police stations from repository
    private val allPoliceStations = repository.getAllPoliceStations()
    
    // Filtered and searched police stations
    val policeStations: StateFlow<List<PoliceStation>> = combine(
        allPoliceStations,
        _selectedDistrict,
        _searchQuery
    ) { stations, district, query ->
        var filtered = stations
        
        // Filter by district if selected
        if (district != null) {
            filtered = filtered.filter { it.district == district }
        }
        
        // Search by query if not empty
        if (query.isNotBlank()) {
            filtered = filtered.filter { station ->
                station.name.contains(query, ignoreCase = true) ||
                station.district.displayName.contains(query, ignoreCase = true) ||
                station.phoneNumber.contains(query, ignoreCase = true)
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
     * Select a district filter.
     */
    fun selectDistrict(district: District?) {
        _selectedDistrict.value = district
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
     * Clear all filters.
     */
    fun clearFilters() {
        _selectedDistrict.value = null
        _searchQuery.value = ""
    }
}
