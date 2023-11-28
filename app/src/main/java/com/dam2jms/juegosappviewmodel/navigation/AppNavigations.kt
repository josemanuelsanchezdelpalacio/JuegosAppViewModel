package com.dam2jms.juegosappviewmodel.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dam2jms.juegosappviewmodel.ui.ViewModelNones
import com.dam2jms.juegosappviewmodel.ui.ViewModelPiedra
import com.dam2jms.juegosappviewmodel.screens.mainScreen
import com.dam2jms.juegosappviewmodel.screens.nonesScreen
import com.dam2jms.juegosappviewmodel.screens.piedraScreen
import com.dam2jms.juegosappviewmodel.screens.sieteScreen
import com.dam2jms.juegosappviewmodel.ui.ViewModelSiete

@Composable
fun appNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.MainScreen.route) {
        composable(route = AppScreens.MainScreen.route) { mainScreen(navController) }
        composable(route = AppScreens.NonesScreen.route) { nonesScreen(navController, mvvm = ViewModelNones()) }
        composable(route = AppScreens.PiedraScreen.route) { piedraScreen(navController, mvvm = ViewModelPiedra()) }
        composable(route = AppScreens.SieteScreen.route) { sieteScreen(navController, mvvm = ViewModelSiete()) }
    }
}