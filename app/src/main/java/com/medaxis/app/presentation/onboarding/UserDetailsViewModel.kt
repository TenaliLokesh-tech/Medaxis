package com.medaxis.app.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.medaxis.app.MedaxisApplication
import com.medaxis.app.data.local.profile.UserProfile
import com.medaxis.app.data.local.profile.UserProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserDetailsViewModel(private val repository: UserProfileRepository) : ViewModel() {
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone.asStateFlow()

    fun onNameChange(value: String) {
        _name.value = value
    }

    fun onPhoneChange(value: String) {
        _phone.value = value
    }

    fun saveProfile(onSuccess: () -> Unit) {
        val currentName = _name.value.trim()
        val currentPhone = _phone.value.trim()
        if (currentName.isEmpty()) return
        viewModelScope.launch {
            repository.saveProfile(UserProfile(currentName, currentPhone))
            onSuccess()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val context = MedaxisApplication.instance
                return UserDetailsViewModel(UserProfileRepository(context)) as T
            }
        }
    }
}
