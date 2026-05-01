package io.github.orizynpx.fivegamescompose.data

import io.github.orizynpx.fivegamescompose.R
import io.github.orizynpx.fivegamescompose.data.model.Game

class GameRepository {
    fun getGames(): List<Game> {
        return listOf(
            Game(
                0,
                R.drawable.bullet_ant,
                R.string.bullet_ant,
                R.string.year_2024,
                R.string.bullet_ant_genre,
                R.string.bullet_ant_detail,
                R.string.bullet_ant_url
            ),
            Game(
                1,
                R.drawable.wizard,
                R.string.wizard,
                R.string.year_2025,
                R.string.wizard_genre,
                R.string.wizard_detail,
                R.string.wizard_url
            ),
            Game(
                2,
                R.drawable.ammid,
                R.string.ammid,
                R.string.year_2025,
                R.string.ammid_genre,
                R.string.ammid_detail,
                R.string.ammid_url
            ),
            Game(
                3,
                R.drawable.robert,
                R.string.robert,
                R.string.year_2025,
                R.string.robert_genre,
                R.string.robert_detail,
                R.string.robert_url
            ),
            Game(
                4,
                R.drawable.personal_space,
                R.string.personal_space,
                R.string.year_2025,
                R.string.personal_space_genre,
                R.string.personal_space_detail,
                R.string.personal_space_url
            )
        )
    }
}