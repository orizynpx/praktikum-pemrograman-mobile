package io.github.orizynpx.fivegamescompose.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.orizynpx.fivegamescompose.model.ItemModel
import io.github.orizynpx.fivegamescompose.viewmodel.HomeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    uiState: HomeUiState,
    item: ItemModel,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "TODO") },
                navigationIcon = {
                    IconButton(onClick = onButtonClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "TODO"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column() {
            Image(
                painter = painterResource(id = item.imageResourceId),
                contentDescription = stringResource(id = item.titleResourceId)
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(id = item.titleResourceId),
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = stringResource(id = item.yearResourceId),
                    style = MaterialTheme.typography.headlineSmall
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = stringResource(id = item.genreResourceId),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = stringResource(id = item.detailResourceId),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Justify
                        )
                    }
                }
            }
        }
    }
}