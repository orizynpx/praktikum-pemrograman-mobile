package io.github.orizynpx.fivegamescompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import io.github.orizynpx.fivegamescompose.ui.navigation.FiveGamesNavGraph
import io.github.orizynpx.fivegamescompose.ui.theme.FiveGamesComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FiveGamesComposeTheme {
                FiveGamesNavGraph()
            }
        }
    }
}