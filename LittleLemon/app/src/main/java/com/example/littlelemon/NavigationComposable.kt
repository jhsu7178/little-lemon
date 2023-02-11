package com.example.littlelemon

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation (navController: NavHostController) {
    val context = LocalContext.current

    val sharedPreferences = context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)
    var startingRoute = ""

    startingRoute = if(sharedPreferences.contains("onboardingComplete")) {
        Home.route
    } else {
        Onboarding.route
    }

    NavHost(navController = navController, startDestination = startingRoute) {
        composable(Home.route) {
            Home(navController)
        }

        composable(Profile.route) {
            Profile()
        }

        composable(Onboarding.route) {
            OnboardingScreen(navController)
        }
    }
}