package com.example.littlelemon

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MyNavigation (navController: NavHostController, sharedPreferences: SharedPreferences, database: AppDatabase) {

    var startingRoute = if(sharedPreferences.contains("onboardingComplete")) {
        Home.route
    } else {
        Onboarding.route
    }

    NavHost(navController = navController, startDestination = startingRoute) {
        composable(Home.route) {
            Home(navController, database)
        }

        composable(Profile.route) {
            Profile(navController, sharedPreferences)
        }

        composable(Onboarding.route) {
            OnboardingScreen(navController, sharedPreferences)
        }
    }
}