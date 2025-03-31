package com.example.tubechallenge.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.tubechallenge.presentation.navigation.Navigation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TubeChallengeApp() {
    val navController = rememberNavController()
    Scaffold {
        Navigation(navController)
    }
}
