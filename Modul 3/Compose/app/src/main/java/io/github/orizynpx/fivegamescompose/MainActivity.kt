package io.github.orizynpx.fivegamescompose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import io.github.orizynpx.fivegamescompose.ui.navigation.FiveGamesNavGraph
import io.github.orizynpx.fivegamescompose.ui.theme.FiveGamesComposeTheme

class MainActivity : AppCompatActivity() {
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