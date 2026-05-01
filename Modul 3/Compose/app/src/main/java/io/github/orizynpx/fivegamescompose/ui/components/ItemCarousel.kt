package io.github.orizynpx.fivegamescompose.ui.components

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.orizynpx.fivegamescompose.model.ItemModel

@Composable
fun ItemCarousel(items: List<ItemModel>) {
    val listState = rememberLazyListState()
    LazyRow(
        state = listState,
        flingBehavior = rememberSnapFlingBehavior(listState)
    ) {
        itemsIndexed(items) { _, item ->
            CarouselCard(
                item = item,
                modifier = Modifier.fillParentMaxWidth()
            )
        }
    }
}