package com.medaxis.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.medaxis.app.data.datasource.DemoMedicalTriageDataSource
import com.medaxis.app.data.datasource.RemoteMedicalTriageDataSource
import com.medaxis.app.data.repository.MedicalTriageRepository
import com.medaxis.app.domain.model.TriageResponse
import com.medaxis.app.domain.model.Urgency
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

sealed interface HomeEvent {
    data object NavigateToEmergency : HomeEvent
    data class NavigateToTriage(val response: TriageResponse) : HomeEvent
}

class HomeViewModel(
    private val triageRepository: MedicalTriageRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _triageResponse = MutableStateFlow<TriageResponse?>(null)
    val triageResponse: StateFlow<TriageResponse?> = _triageResponse.asStateFlow()

    private val _isChecking = MutableStateFlow(false)
    val isChecking: StateFlow<Boolean> = _isChecking.asStateFlow()

    private val _events = MutableSharedFlow<HomeEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<HomeEvent> = _events.asSharedFlow()

    fun onQueryChange(value: String) {
        _searchQuery.value = value
    }

    fun onVoiceResult(spokenText: String) {
        val cleaned = spokenText.trim()
        if (cleaned.isEmpty()) return
        val current = _searchQuery.value.trim()
        _searchQuery.value = if (current.isEmpty()) {
            cleaned
        } else {
            "$current $cleaned"
        }
        interceptIfEmergency(_searchQuery.value)
    }

    fun submitSymptoms() {
        val query = _searchQuery.value.trim()
        if (query.isEmpty() || _isChecking.value) return
        if (checkSymptomForEmergency(query)) {
            _events.tryEmit(HomeEvent.NavigateToEmergency)
            return
        }
        viewModelScope.launch {
            _isChecking.value = true
            try {
                val response = triageRepository.getTriage(query)
                _triageResponse.value = response
                _events.tryEmit(HomeEvent.NavigateToTriage(response))
            } finally {
                _isChecking.value = false
            }
        }
    }

    fun checkSymptomForEmergency(query: String): Boolean {
        val normalized = query
            .lowercase(Locale.ROOT)
            .replace("'", "")
            .replace("-", " ")
            .replace(Regex("\\s+"), " ")
            .trim()
        if (normalized.isEmpty()) return false
        return emergencyKeywords.any { keyword -> normalized.contains(keyword) }
    }

    private fun interceptIfEmergency(query: String) {
        if (checkSymptomForEmergency(query)) {
            _events.tryEmit(HomeEvent.NavigateToEmergency)
        }
    }

    companion object {
        val emergencyKeywords: List<String> = listOf(
            "chest pain",
            "heart attack",
            "heart",
            "stroke",
            "cannot breathe",
            "cant breathe",
            "not breathing",
            "snake",
            "bite",
            "poison",
            "heavy bleeding",
            "bleeding",
            "unconscious",
            "suicide",
            "severe burn",
            "burn"
        )

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val remote = RemoteMedicalTriageDataSource()
                val demo = DemoMedicalTriageDataSource()
                val repo = MedicalTriageRepository(remote, demo)
                return HomeViewModel(repo) as T
            }
        }
    }
}
