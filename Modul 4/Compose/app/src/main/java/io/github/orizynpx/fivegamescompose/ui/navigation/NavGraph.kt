package io.github.orizynpx.fivegamescompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.github.orizynpx.fivegamescompose.ui.navigation.Screen
import io.github.orizynpx.fivegamescompose.ui.detail.DetailScreen
import io.github.orizynpx.fivegamescompose.ui.home.HomeScreen
import io.github.orizynpx.fivegamescompose.ui.settings.SettingsScreen

@Composable
fun FiveGamesNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToDetail = { gameId ->
                    navController.navigate(Screen.Detail.createRoute(gameId))
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("gameId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getInt("gameId") ?: -1
            DetailScreen(
                gameId = gameId,
                onNavigateBack = { navController.navigateUp() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}