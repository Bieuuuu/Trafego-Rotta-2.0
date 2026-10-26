package com.traffigorotta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.traffigorotta.core.design.theme.RottaTheme
import com.traffigorotta.ui.entry.ManualEntryScreen
import com.traffigorotta.ui.entry.ManualEntryViewModel
import com.traffigorotta.ui.goals.GoalsScreen
import com.traffigorotta.ui.goals.GoalsViewModel
import com.traffigorotta.ui.journey.JourneyScreen
import com.traffigorotta.ui.journey.JourneyViewModel
import com.traffigorotta.ui.disclosure.ProminentDisclosureScreen
import com.traffigorotta.ui.login.LoginScreen
import com.traffigorotta.ui.profile.ProfileScreen
import com.traffigorotta.ui.profile.ProfileViewModel
import com.traffigorotta.ui.recommendations.RecommendationsScreen
import com.traffigorotta.ui.recommendations.RecommendationsViewModel
import com.traffigorotta.ui.privacy.PrivacyPolicyScreen
import com.traffigorotta.ui.reports.ReportsScreen
import com.traffigorotta.ui.reports.ReportsViewModel
import com.traffigorotta.ui.signal.TrafficLightSettingsScreen
import com.traffigorotta.ui.signal.TrafficLightViewModel

enum class AppDestination {
    LOGIN,
    JOURNEY,
    MANUAL_ENTRY,
    GOALS,
    REPORTS,
    RECOMMENDATIONS,
    PROFILE,
    TRAFFIC_LIGHT_SETTINGS,
    PROMINENT_DISCLOSURE,
    PRIVACY_POLICY,
    CATALOG
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }
            var currentScreen by remember { mutableStateOf(AppDestination.JOURNEY) }

            val profileViewModel: ProfileViewModel = viewModel()
            val profileState by profileViewModel.uiState.collectAsState()

            val journeyViewModel: JourneyViewModel = viewModel()
            OfferCoordinator.journeyRepository = journeyViewModel.journeyRepository

            val manualEntryViewModel: ManualEntryViewModel = viewModel()
            val goalsViewModel: GoalsViewModel = viewModel()
            val reportsViewModel: ReportsViewModel = viewModel()
            val trafficLightViewModel: TrafficLightViewModel = viewModel()
            val recommendationsViewModel: RecommendationsViewModel = viewModel()

            RottaTheme(darkTheme = isDarkTheme) {
                when (currentScreen) {
                    AppDestination.LOGIN -> {
                        LoginScreen(
                            onLoginSuccess = { email ->
                                profileViewModel.login(email)
                                currentScreen = AppDestination.JOURNEY
                            },
                            onSkipToCatalog = {
                                currentScreen = AppDestination.CATALOG
                            }
                        )
                    }
                    AppDestination.JOURNEY -> {
                        if (!profileState.isLoggedIn) {
                            LoginScreen(
                                onLoginSuccess = { email ->
                                    profileViewModel.login(email)
                                },
                                onSkipToCatalog = {
                                    currentScreen = AppDestination.CATALOG
                                }
                            )
                        } else {
                            JourneyScreen(
                                viewModel = journeyViewModel,
                                onNavigateToProfile = { currentScreen = AppDestination.PROFILE },
                                onNavigateToCatalog = { currentScreen = AppDestination.CATALOG },
                                onNavigateToManualEntry = { currentScreen = AppDestination.MANUAL_ENTRY },
                                onNavigateToGoals = { currentScreen = AppDestination.GOALS },
                                onNavigateToReports = { currentScreen = AppDestination.REPORTS },
                                onNavigateToTrafficLight = { currentScreen = AppDestination.TRAFFIC_LIGHT_SETTINGS },
                                onNavigateToRecommendations = { currentScreen = AppDestination.RECOMMENDATIONS }
                            )
                        }
                    }
                    AppDestination.MANUAL_ENTRY -> {
                        ManualEntryScreen(
                            viewModel = manualEntryViewModel,
                            onNavigateBack = { currentScreen = AppDestination.JOURNEY }
                        )
                    }
                    AppDestination.GOALS -> {
                        GoalsScreen(
                            viewModel = goalsViewModel,
                            onNavigateBack = { currentScreen = AppDestination.JOURNEY }
                        )
                    }
                    AppDestination.REPORTS -> {
                        ReportsScreen(
                            viewModel = reportsViewModel,
                            onNavigateBack = { currentScreen = AppDestination.JOURNEY }
                        )
                    }
                    AppDestination.RECOMMENDATIONS -> {
                        RecommendationsScreen(
                            viewModel = recommendationsViewModel,
                            onNavigateBack = { currentScreen = AppDestination.JOURNEY }
                        )
                    }
                    AppDestination.PROFILE -> {
                        ProfileScreen(
                            viewModel = profileViewModel,
                            onNavigateToCatalog = { currentScreen = AppDestination.CATALOG },
                            onLogout = { currentScreen = AppDestination.LOGIN },
                            onNavigateToPrivacy = { currentScreen = AppDestination.PRIVACY_POLICY },
                            onNavigateBack = { currentScreen = AppDestination.JOURNEY }
                        )
                    }
                    AppDestination.PRIVACY_POLICY -> {
                        PrivacyPolicyScreen(
                            onNavigateBack = { currentScreen = AppDestination.PROFILE }
                        )
                    }
                    AppDestination.TRAFFIC_LIGHT_SETTINGS -> {
                        TrafficLightSettingsScreen(
                            viewModel = trafficLightViewModel,
                            onOpenDisclosure = { currentScreen = AppDestination.PROMINENT_DISCLOSURE },
                            onNavigateBack = { currentScreen = AppDestination.JOURNEY }
                        )
                    }
                    AppDestination.PROMINENT_DISCLOSURE -> {
                        ProminentDisclosureScreen(
                            onAccept = {
                                trafficLightViewModel.toggleOverlayEnabled(true)
                                currentScreen = AppDestination.TRAFFIC_LIGHT_SETTINGS
                            },
                            onDecline = {
                                currentScreen = AppDestination.TRAFFIC_LIGHT_SETTINGS
                            }
                        )
                    }
                    AppDestination.CATALOG -> {
                        CatalogScreen(
                            isDarkTheme = isDarkTheme,
                            onToggleTheme = { isDarkTheme = !isDarkTheme },
                            onNavigateToProfile = { currentScreen = AppDestination.PROFILE }
                        )
                    }
                }
            }
        }
    }
}
