package io.github.orizynpx.fivegamescompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.orizynpx.fivegamescompose.ui.theme.FiveGamesComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FiveGamesComposeTheme {
                viewModel: ScrollableViewModel = viewModel(),
                navController: NavHostController = rememberNavController()

                val uiState by viewModel.uiState.collectAsState()
                val context = LocalContext.current

                NavHost(
                    navController = navController,
                    startDestination = ScrollableScreen.Home.name,
                ) {
                    composable(route = ScrollableScreen.Home.name) {
                        val activity = context as Activity
                        HomeScreen(
                            uiState = uiState,
                            onDetailClick = { index ->
                                navController.navigate("${ScrollableScreen.Details.name}/$index")
                            },
                            onIntentClick = { url ->
                                context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
                            },
                            onSettingsClick = { navController.navigate(ScrollableScreen.Settings.name) },
                            onExit = { activity.finish() }
                        )
                    }
                    composable(route = "${ScrollableScreen.Details.name}/{itemId}") { backStackEntry ->
                        val index = backStackEntry.arguments?.getString("itemId")?.toIntOrNull() ?: 0
                        val item = uiState.list.getOrNull(index) ?: uiState.list.first()
                        LaunchedEffect(index){
                            viewModel.updateCurrentScrollable(index)
                        }
                        DetailScreen(
                            item = item,
                            modifier = Modifier,
                            onBackClick = { navController.navigateUp() }
                        )
                    }
                    composable(route = ScrollableScreen.Settings.name) {
                        SettingScreen(
                            modifier = Modifier,
                            onBackClick = { navController.navigateUp() },
                            onLocaleChange = { locale -> viewModel.updateLocale(locale) },
                            selectedLocale = uiState.selectedLocale
                        )
                    }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FiveGamesComposeTheme {

    }
}