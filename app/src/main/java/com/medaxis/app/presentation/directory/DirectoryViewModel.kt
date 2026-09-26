package com.medaxis.app.presentation.directory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.medaxis.app.domain.model.Hospital
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the Directory screen which shows nearby healthcare facilities for a given disease.
 */
class DirectoryViewModel : ViewModel() {
    // Holds the list of hospitals for the UI.
    private val _hospitals = MutableStateFlow<List<Hospital>>(emptyList())
    val hospitals: StateFlow<List<Hospital>> = _hospitals.asStateFlow()

    /**
     * Mock fetch that populates [_hospitals] with static data for the Puducherry area.
     * The [diseaseName] parameter is currently unused but kept for future API calls.
     */
    fun fetchNearbyHospitals(diseaseName: String) {
        viewModelScope.launch {
            val mock = listOf(
                Hospital(
                    id = "jipmer",
                    name = "JIPMER",
                    distance = "2.1 km away",
                    address = "JIPMER Campus, Puducherry 605006",
                    specialty = "General Hospital",
                    isOpen = true
                ),
                Hospital(
                    id = "pims",
                    name = "Pondicherry Institute of Medical Sciences (PIMS)",
                    distance = "3.4 km away",
                    address = "PIMS Campus, Puducherry 605006",
                    specialty = "Multispecialty",
                    isOpen = true
                ),
                Hospital(
                    id = "eastcoast",
                    name = "East Coast Hospitals",
                    distance = "4.0 km away",
                    address = "East Coast Road, Puducherry",
                    specialty = "Cardiology, Orthopaedics",
                    isOpen = false
                ),
                Hospital(
                    id = "govt_general",
                    name = "Government General Hospital",
                    distance = "5.2 km away",
                    address = "T.Nagar, Puducherry",
                    specialty = "Emergency Care",
                    isOpen = true
                )
            )
            _hospitals.value = mock
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DirectoryViewModel() as T
            }
        }
    }
}
