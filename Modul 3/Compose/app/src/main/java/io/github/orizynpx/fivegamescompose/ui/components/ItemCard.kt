package io.github.orizynpx.fivegamescompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import io.github.orizynpx.fivegamescompose.model.ItemModel

@Composable
fun ItemCard(
    item: ItemModel,
    onDetailClick: () -> Unit,
    onIntentClick: () -> Unit
) {
    Card() {
        Column {
            Image(
                painter = painterResource(id = item.imageResourceId),
                contentDescription = "TODO"
            )
            Row {
                Column() {
                    Text(
                        text = stringResource(id = item.titleResourceId),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = stringResource(id = item.yearResourceId),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Column {
                    Text(text = "Genre:")
                    Text(
                        text = stringResource(id = item.genreResourceId),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}