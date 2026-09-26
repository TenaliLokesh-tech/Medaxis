package com.medaxis.app.presentation.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.medaxis.app.MedaxisApplication
import com.medaxis.app.data.local.profile.UserProfileRepository
import kotlinx.coroutines.flow.map

/** ViewModel for the splash screen. Checks if a user profile exists. */
class SplashViewModel(private val repository: UserProfileRepository) : ViewModel() {
    /** Emits true when a profile is present, false otherwise. */
    val isProfilePresent = repository.profileFlow.map { it != null }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val context = MedaxisApplication.instance
                return SplashViewModel(UserProfileRepository(context)) as T
            }
        }
    }
}

/** Simple splash composable that decides navigation based on profile existence. */
@Composable
fun SplashScreen(viewModel: SplashViewModel, onResult: (Boolean) -> Unit) {
    // Collect the flow once – the UI itself does not display anything fancy here.
    val hasProfile by viewModel.isProfilePresent.collectAsStateWithLifecycle(initialValue = false)
    // Trigger navigation when the value is emitted.
    LaunchedEffect(hasProfile) {
        onResult(hasProfile)
    }
    // Could show a logo or progress indicator if desired.
}
