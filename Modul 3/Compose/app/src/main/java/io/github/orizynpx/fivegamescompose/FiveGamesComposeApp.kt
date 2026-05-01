package io.github.orizynpx.fivegamescompose

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.orizynpx.fivegamescompose.ui.screens.DetailScreen
import io.github.orizynpx.fivegamescompose.ui.screens.HomeScreen
import io.github.orizynpx.fivegamescompose.ui.screens.SettingsScreen
import io.github.orizynpx.fivegamescompose.viewmodel.ItemViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

enum class FiveGamesScreen { Home, Details, Settings }

@Composable
fun FiveGamesComposeApp(
    viewModel: ItemViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = FiveGamesScreen.Home.name,
    ) {
        composable(route = FiveGamesScreen.Home.name) {
            val activity = context as Activity
            HomeScreen(
                uiState = uiState,
                onDetailClick = { index ->
                    navController.navigate("${FiveGamesScreen.Details.name}/$index")
                },
                onIntentClick = { url ->
                    context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
                },
                onSettingsClick = { navController.navigate(FiveGamesScreen.Settings.name) },
                onExit = { activity.finish() }
            )
        }
        composable(route = "${FiveGamesScreen.Details.name}/{itemId}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("itemId")?.toIntOrNull() ?: 0
            val item = uiState.itemList.getOrNull(index) ?: uiState.itemList.first()
            LaunchedEffect(index) {
                viewModel.updateCurrentScrollable(index)
            }
            DetailScreen(
                item = item,
                modifier = Modifier,
                onBackClick = { navController.navigateUp() }
            )
        }
        composable(route = FiveGamesScreen.Settings.name) {
            SettingsScreen(
                modifier = Modifier,
                onBackClick = { navController.navigateUp() },
                onLocaleChange = { locale -> viewModel.updateLocale(locale) },
                selectedLocale = uiState.selectedLocale
            )
        }
    }
}