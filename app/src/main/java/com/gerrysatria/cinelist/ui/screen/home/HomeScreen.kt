package com.gerrysatria.cinelist.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gerrysatria.cinelist.R
import com.gerrysatria.cinelist.di.Injection
import com.gerrysatria.cinelist.model.WatchList
import com.gerrysatria.cinelist.ui.ViewModelFactory
import com.gerrysatria.cinelist.ui.common.UiState
import com.gerrysatria.cinelist.ui.components.Banner
import com.gerrysatria.cinelist.ui.components.MovieItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let{ uiState ->
        when(uiState){
            is UiState.Loading -> viewModel.getAllMovies()
            is UiState.Success -> HomeContent(
                watchList = uiState.data,
                modifier = modifier,
                navigateToDetail = navigateToDetail,
            )
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    watchList: List<WatchList>,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    val query by viewModel.query
    Column(
        modifier = modifier
    ){
        Banner(
            query = query,
            onQueryChange = viewModel::searchMovie
        )
        Text(
            text = stringResource(R.string.home_title),
            color = Color.White,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Medium
            ),
            modifier = modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(horizontal = 16.dp, vertical = 6.dp)
        )
        if (watchList.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(160.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier.testTag("MovieList")
            ) {
                items(watchList, key = { it.movie.id}) { data ->
                    MovieItem(
                        image = data.movie.poster,
                        title = data.movie.name,
                        duration = data.movie.duration,
                        modifier = modifier.clickable {
                            navigateToDetail(data.movie.id)
                        }
                    )
                }
            }
        } else {
            Text(
                text = stringResource(R.string.search_message),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            )
        }
    }
}