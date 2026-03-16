package com.roddstkd.admin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.roddstkd.admin.ui.screens.BeltTestingScreen
import com.roddstkd.admin.ui.screens.CommunicationScreen
import com.roddstkd.admin.ui.screens.HomeScreen
import com.roddstkd.admin.ui.screens.PaymentScreen
import com.roddstkd.admin.ui.screens.RegistrationScreen

object Routes {
    const val HOME = "home"
    const val PAYMENT = "payment"
    const val REGISTRATION = "registration"
    const val BELT = "belt"
    const val COMMUNICATION = "communication"
}

@Composable
fun AppNav(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) { HomeScreen(navController) }
        composable(Routes.PAYMENT) { PaymentScreen(navController) }
        composable(Routes.REGISTRATION) { RegistrationScreen(navController) }
        composable(Routes.BELT) { BeltTestingScreen(navController) }
        composable(Routes.COMMUNICATION) { CommunicationScreen(navController) }
    }
}
