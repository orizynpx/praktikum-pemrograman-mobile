package io.github.orizynpx.fivegamescompose.ui.home

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.orizynpx.fivegamescompose.R
import io.github.orizynpx.fivegamescompose.data.model.Game
import io.github.orizynpx.fivegamescompose.ui.viewmodel.HomeViewModel
import io.github.orizynpx.fivegamescompose.ui.viewmodel.HomeViewModelFactory
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val activity = LocalContext.current as ComponentActivity
    val context = LocalContext.current

    val viewModel: HomeViewModel = viewModel(
        viewModelStoreOwner = activity,
        factory = HomeViewModelFactory(
            application = LocalContext.current.applicationContext as Application,
            appLabel = stringResource(R.string.app_name)
        )
    )

    val games by viewModel.gameList.collectAsStateWithLifecycle()
    val navigateToDetail by viewModel.navigateToDetail.collectAsStateWithLifecycle()
    val navigateToLink by viewModel.navigateToLink.collectAsStateWithLifecycle()

    LaunchedEffect(navigateToDetail) {
        navigateToDetail?.let { game ->
            Timber.d("GALAT: Navigasi ke halaman Detail dengan membawa data berupa ${game}")
            onNavigateToDetail(game.id)
            viewModel.onDetailNavigated()
        }
    }

    LaunchedEffect(navigateToLink) {
        navigateToLink?.let {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            context.startActivity(intent)
            viewModel.onLinkNavigated()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(R.string.title_settings)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // Highlight / Carousel section
            item {
                Text(
                    text = stringResource(R.string.tv_highlight),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
            item {
                GameCarouselItem(
                    games = games,
                    onGameClick = { viewModel.onDetailClicked(it) }
                )
            }

            // Game list section
            item {
                Text(
                    text = stringResource(R.string.tv_game_list),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
            items(games, key = { it.id }) { game ->
                GameListItem(
                    game = game,
                    onDetailClick = { viewModel.onDetailClicked(game) },
                    onLinkClick = { url -> viewModel.onLinkClicked(url) }
                )
            }
        }
    }
}

@Composable
fun GameCarouselItem(
    games: List<Game>,
    onGameClick: (Game) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(games, key = { it.id }) { game ->
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onGameClick(game) }
            ) {
                Image(
                    painter = painterResource(id = game.imageResourceId),contentDescription = stringResource(game.titleResourceId),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun GameListItem(
    game: Game,
    onDetailClick: () -> Unit,
    onLinkClick: (String) -> Unit
) {
    val context = LocalContext.current
    val urlString = stringResource(game.urlResourceId)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painterResource(id = game.imageResourceId),
                contentDescription = stringResource(game.titleResourceId),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .height(140.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = stringResource(game.titleResourceId),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(game.yearResourceId),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row {
                    Text(
                        text = stringResource(R.string.tv_genre_label),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(game.genreResourceId),
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(onClick = onDetailClick) {
                        Text(text = stringResource(R.string.btn_detail))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { onLinkClick(urlString) }
                    ) {
                        Text(text = stringResource(R.string.btn_link))
                    }
                }
            }
        }
    }
}