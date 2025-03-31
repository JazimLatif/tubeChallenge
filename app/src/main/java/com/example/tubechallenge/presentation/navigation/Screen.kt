package com.example.tubechallenge.presentation.navigation

sealed class Screen(val route: String) {
    object HomeScreen: Screen("homescreen")
    object ChallengeScreen: Screen("challengescreen")
    object ResultsScreen: Screen("resultsscreen")
}
