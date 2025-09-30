package vcmsa.projects.quizmaster

import AuthViewModel
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

/**
 * Navigation routes for the app.
 */
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object BiometricGate : Screen("biometric_gate")
    object Home : Screen("home")
    object Play : Screen("play")
    object Leaderboard : Screen("leaderboard")
    object Chat : Screen("chat")
    object Settings : Screen("settings")
}

@Composable
fun QuizMasterNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()
    val isSignedIn by authViewModel.isSignedIn.collectAsState()

    // Start at Login or BiometricGate depending on user state
    val startDestination =
        if (isSignedIn) Screen.BiometricGate.route else Screen.Login.route

    NavHost(navController = navController, startDestination = startDestination) {
        // Login screen
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.BiometricGate.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }

        // Register screen
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.BiometricGate.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        // Biometric gate (shown right after login)
        composable(Screen.BiometricGate.route) {
            BiometricGateScreen(
                onAuthSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.BiometricGate.route) { inclusive = true }
                    }
                },
                onSkip = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.BiometricGate.route) { inclusive = true }
                    }
                }
            )
        }

        // Main app screens
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Play.route) { PlayScreen(navController) }
        composable(Screen.Leaderboard.route) { LeaderboardScreen(navController) }
        composable(Screen.Chat.route) { ChatScreen(navController) }
        composable(Screen.Settings.route) {
            SettingsScreen(
                navController = navController,
                onLogout = {
                    authViewModel.signOut()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}