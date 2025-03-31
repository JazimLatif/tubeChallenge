package com.example.tubechallenge.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tubechallenge.presentation.service.StopwatchService
import com.example.tubechallenge.presentation.ui.views.ChallengeScreen
import com.example.tubechallenge.presentation.ui.views.HomeView
import com.example.tubechallenge.presentation.ui.views.ResultsScreen
import com.example.tubechallenge.presentation.viewmodel.StopViewModel

@Composable
fun Navigation(
    navController: NavHostController
) {
    val viewModel = hiltViewModel<StopViewModel>()

    val isRunning = StopwatchService.isRunning

    NavHost(
        navController = navController,
        startDestination = if (!isRunning) Screen.HomeScreen.route else (Screen.ChallengeScreen.route)
    ) {
        composable(route = Screen.HomeScreen.route) {
            HomeView({ navController.navigate(Screen.ChallengeScreen.route) }, { navController.navigate(Screen.ResultsScreen.route)})
        }
        composable(route = Screen.ChallengeScreen.route)
        {
            ChallengeScreen(viewModel, onNavigateBack = { navController.popBackStack() })
        }
        composable(route = Screen.ResultsScreen.route)
        {
            ResultsScreen()
        }
    }
}