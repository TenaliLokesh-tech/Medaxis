package com.medaxis.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.medaxis.app.presentation.emergency.EmergencyScreen
import com.medaxis.app.presentation.home.HomeScreen
import com.medaxis.app.presentation.home.HomeViewModel
import com.medaxis.app.presentation.triage.TriageResultsScreen
import com.medaxis.app.presentation.directory.DirectoryScreen
import com.medaxis.app.presentation.directory.DirectoryViewModel
import com.medaxis.app.presentation.onboarding.UserDetailsScreen
import com.medaxis.app.presentation.onboarding.UserDetailsViewModel
import com.medaxis.app.presentation.settings.SettingsScreen
import com.medaxis.app.presentation.settings.SettingsViewModel
import com.medaxis.app.presentation.splash.SplashScreen
import com.medaxis.app.presentation.splash.SplashViewModel
import androidx.compose.runtime.LaunchedEffect

/** Navigation destinations for the Medaxis app */
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object Emergency : Screen("emergency")
    object TriageResults : Screen("triage")
    object Directory : Screen("directory/{diseaseName}") {
        fun createRoute(diseaseName: String) = "directory/$diseaseName"
    }
    object Settings : Screen("settings")
}

@Composable
fun MedaxisNavHost() {
    val navController = rememberNavController()
    // Shared ViewModels
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
    val splashViewModel: SplashViewModel = viewModel(factory = SplashViewModel.Factory)
    val triageResponse by homeViewModel.triageResponse.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // ---------- Splash ----------
        composable(Screen.Splash.route) {
            SplashScreen(viewModel = splashViewModel) { isProfilePresent ->
                if (isProfilePresent) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                } else {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            }
        }
        // ---------- Onboarding ----------
        composable(Screen.Onboarding.route) {
            val onboardingVm: UserDetailsViewModel = viewModel(factory = UserDetailsViewModel.Factory)
            UserDetailsScreen(viewModel = onboardingVm) {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Onboarding.route) { inclusive = true }
                }
            }
        }
        // ---------- Home ----------
        composable(Screen.Home.route) {
            HomeScreen(
                onEmergency = { navController.navigate(Screen.Emergency.route) },
                onSymptomChecked = { navController.navigate(Screen.TriageResults.route) },
                viewModel = homeViewModel,
                onSettings = { navController.navigate(Screen.Settings.route) }
            )
        }
        // ---------- Settings (Clear Profile) ----------
        composable(Screen.Settings.route) {
            val settingsVm: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory)
            SettingsScreen(
                viewModel = settingsVm,
                onProfileCleared = {
                    // After clearing profile, go back to onboarding
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
        // ---------- Emergency ----------
        composable(Screen.Emergency.route) {
            EmergencyScreen(onGoBack = { navController.popBackStack() })
        }
        // ---------- Triage Results ----------
        composable(Screen.TriageResults.route) {
            triageResponse?.let { response ->
                TriageResultsScreen(
                    response = response,
                    onFindDoctorsClick = { diseaseName ->
                        navController.navigate(Screen.Directory.createRoute(diseaseName))
                    },
                    onGoBack = { navController.popBackStack() }
                )
            }
        }
        }
        // ---------- Directory ----------
        composable(
            route = Screen.Directory.route,
            arguments = listOf(navArgument("diseaseName") { type = NavType.StringType })
        ) { backStackEntry ->
            val diseaseName = backStackEntry.arguments?.getString("diseaseName") ?: ""
            val directoryViewModel: DirectoryViewModel = viewModel(factory = DirectoryViewModel.Factory)
            LaunchedEffect(diseaseName) { directoryViewModel.fetchNearbyHospitals(diseaseName) }
            val hospitals by directoryViewModel.hospitals.collectAsStateWithLifecycle()
            DirectoryScreen(
                hospitals = hospitals,
                onBackClick = { navController.popBackStack() },
                onCallClinic = { /* TODO: implement phone call */ }
            )
        }
    }
}
