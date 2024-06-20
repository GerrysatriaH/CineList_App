package com.gerrysatria.cinelist.ui.screen.watchlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gerrysatria.cinelist.R
import com.gerrysatria.cinelist.di.Injection
import com.gerrysatria.cinelist.model.WatchList
import com.gerrysatria.cinelist.ui.ViewModelFactory
import com.gerrysatria.cinelist.ui.common.UiState
import com.gerrysatria.cinelist.ui.components.CenterTopBar
import com.gerrysatria.cinelist.ui.components.WatchlistItem
import com.gerrysatria.cinelist.ui.theme.CineListTheme

@Composable
fun WatchlistScreen(
    viewModel: WatchlistViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateToDetail: (Long) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let{uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAddedWatchlist()
            }
            is UiState.Success -> {
                if (uiState.data.isEmpty()) {
                    NoContent()
                } else {
                    WatchlistContent(
                        watchlist = uiState.data,
                        navigateToDetail = navigateToDetail
                    )
                }
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun WatchlistContent(
    watchlist: List<WatchList>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CenterTopBar(
            text = stringResource(R.string.watchlist_title)
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(weight = 1f)
        ) {
            items(watchlist, key = { it.movie.id }) { item ->
                WatchlistItem(
                    image = item.movie.poster,
                    title = item.movie.name,
                    duration =  item.movie.duration,
                    modifier = Modifier.clickable {
                        navigateToDetail(item.movie.id)
                    }
                )
                Divider()
            }
        }
    }
}

@Composable
fun NoContent(
    modifier: Modifier = Modifier
){
    Column {
        CenterTopBar(
            text = stringResource(R.string.watchlist_title)
        )
        Text(
            text = stringResource(R.string.watchlist_message),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = modifier
                .fillMaxSize()
                .padding(vertical = 50.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NoContentPreview(){
    CineListTheme {
        NoContent()
    }
}