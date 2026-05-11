package io.github.orizynpx.fivegamescompose.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Detail : Screen("detail/{gameId}") {
        fun createRoute(gameId: Int) = "detail/$gameId"
    }
    data object Settings : Screen("settings")
}