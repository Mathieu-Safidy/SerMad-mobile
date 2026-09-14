package com.example.demarches.navigation

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.demarches.ui.auth.AuthViewModel
import com.example.demarches.ui.auth.LoginScreen
import com.example.demarches.ui.auth.RegisterScreen
import com.example.demarches.ui.home.HomeScreen
import com.example.demarches.ui.home.HomeViewModel
import com.example.demarches.ui.citoyen.DemandeListScreen
import com.example.demarches.ui.notifications.NotificationListScreen
import com.example.demarches.ui.admin.AdministrationListScreen
import com.example.demarches.ui.qrcode.QRCodeScannerScreen
import com.example.demarches.ui.agent.CreerDemandeScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Demandes : Screen("demandes")
    object Notifications : Screen("notifications")
    object Administrations : Screen("administrations")
    object QRCode : Screen("qrcode")
    object CreerDemande : Screen("creer_demande")
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val authState by authViewModel.state.collectAsState()

    val startDestination = if (authState.isLoggedIn) {
        Screen.Home.route
    } else {
        Screen.Login.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { profil ->
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Home.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            val profil by homeViewModel.profil.collectAsState()

            HomeScreen(
                profil = profil,
                onDemandesClick = {
                    navController.navigate(Screen.Demandes.route)
                },
                onNotificationsClick = {
                    navController.navigate(Screen.Notifications.route)
                },
                onAdministrationsClick = {
                    navController.navigate(Screen.Administrations.route)
                },
                onDocumentsClick = {
                    // TODO: Implement documents screen
                },
                onCreerDemandeClick = {
                    navController.navigate(Screen.CreerDemande.route)
                },
                onQRCodeClick = {
                    navController.navigate(Screen.QRCode.route)
                },
                onLogoutClick = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Demandes.route) {
            DemandeListScreen(
                onDemandeClick = { id ->
                    // TODO: Navigate to demande detail
                }
            )
        }

        composable(Screen.Notifications.route) {
            NotificationListScreen()
        }

        composable(Screen.Administrations.route) {
            AdministrationListScreen()
        }

        composable(Screen.QRCode.route) {
            QRCodeScannerScreen(
                onQRCodeScanned = { token ->
                    // TODO: Validate QR code
                    navController.popBackStack()
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.CreerDemande.route) {
            CreerDemandeScreen(
                onDemandeCreee = {
                    navController.popBackStack()
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
