package io.github.orizynpx.fivegamescompose.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.orizynpx.fivegamescompose.R
import io.github.orizynpx.fivegamescompose.model.ItemModel
import io.github.orizynpx.fivegamescompose.ui.components.HeaderItem
import io.github.orizynpx.fivegamescompose.ui.components.ItemCard
import io.github.orizynpx.fivegamescompose.ui.components.ItemCarousel
import io.github.orizynpx.fivegamescompose.ui.theme.FiveGamesComposeTheme
import io.github.orizynpx.fivegamescompose.viewmodel.HomeUiState

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onSettingsClick: () -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            HeaderItem(onSettingsClick = onSettingsClick, onExit = onExit)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
        ) {
            item {
                ItemCarousel(items = uiState.itemList)
            }
            itemsIndexed(uiState.itemList) { index, item ->
                ItemCard(
                    item = item,
                    { },
                    { }
                )
            }
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    FiveGamesComposeTheme {
        HomeScreen(
            uiState = HomeUiState(
                listOf(
                    ItemModel(
                        0,
                        R.drawable.bullet_ant,
                        R.string.bullet_ant,
                        R.string.year_2024,
                        R.string.bullet_ant_genre,
                        R.string.bullet_ant_detail,
                        R.string.bullet_ant_url
                    ),
                    ItemModel(
                        1,
                        R.drawable.wizard,
                        R.string.wizard,
                        R.string.year_2025,
                        R.string.wizard_genre,
                        R.string.wizard_detail,
                        R.string.wizard_url
                    ),
                    ItemModel(
                        2,
                        R.drawable.ammid,
                        R.string.ammid,
                        R.string.year_2025,
                        R.string.ammid_genre,
                        R.string.ammid_detail,
                        R.string.ammid_url
                    ),
                    ItemModel(
                        3,
                        R.drawable.robert,
                        R.string.robert,
                        R.string.year_2025,
                        R.string.robert_genre,
                        R.string.robert_detail,
                        R.string.robert_url
                    ),
                    ItemModel(
                        4,
                        R.drawable.personal_space,
                        R.string.personal_space,
                        R.string.year_2025,
                        R.string.personal_space_genre,
                        R.string.personal_space_detail,
                        R.string.personal_space_url
                    )
                )
            ),
            onSettingsClick = {},
            onExit = {},
        )
    }
}