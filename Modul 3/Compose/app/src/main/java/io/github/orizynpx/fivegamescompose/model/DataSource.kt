package io.github.orizynpx.fivegamescompose.model

import io.github.orizynpx.fivegamescompose.R

class DataSource {
    fun loadItems(): List<ItemModel> {
        return listOf(
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
    }
}