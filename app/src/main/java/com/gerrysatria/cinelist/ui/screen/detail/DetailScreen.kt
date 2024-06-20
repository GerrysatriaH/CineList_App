package com.gerrysatria.cinelist.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gerrysatria.cinelist.R
import com.gerrysatria.cinelist.di.Injection
import com.gerrysatria.cinelist.model.MovieDataSource
import com.gerrysatria.cinelist.ui.ViewModelFactory
import com.gerrysatria.cinelist.ui.common.UiState
import com.gerrysatria.cinelist.ui.components.AddToListButton
import com.gerrysatria.cinelist.ui.theme.CineListTheme

@Composable
fun DetailScreen(
    movieId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    navigateToWatchlist: () -> Unit
){
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {uiState ->
        when(uiState){
            is UiState.Loading -> viewModel.getMovieById(movieId = movieId)
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    image = data.movie.poster,
                    title = data.movie.name,
                    synopsis = data.movie.synopsis,
                    duration = data.movie.duration,
                    rating = data.movie.rating,
                    onBackClick = { navigateBack() },
                    onAddToList = {
                        viewModel.addToWatchlist(
                            data.movie,
                            if(data.status) false else true
                        )
                        navigateToWatchlist()
                    },
                    statusMovie = data.status
                )
            }
            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    image: Int,
    title: String,
    synopsis: String,
    duration: String,
    rating: Double,
    onBackClick: () -> Unit,
    onAddToList: () -> Unit,
    statusMovie: Boolean,
    modifier: Modifier = Modifier,
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.detail_title)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { onBackClick() }
                    )
                }
            )
        },
        modifier = modifier
    ){ innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ){
            Column(
                modifier = modifier.padding(all = 16.dp)
            ){
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    modifier = modifier.fillMaxWidth()
                )
                Text(
                    text = duration,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Row(
                verticalAlignment = Alignment.Top,
                modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ){
                Image(
                    painter = painterResource(image),
                    contentDescription = title,
                )
                Text(
                    text = synopsis,
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Light
                    ),
                    modifier = modifier.padding(start = 8.dp)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .padding(all = 16.dp)
                    .fillMaxWidth()
            ){
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = stringResource(R.string.rating),
                    tint = Color.Yellow,
                    modifier = modifier.size(50.dp)
                )
                Text(
                    text = rating.toString(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(R.string.rating),
                    style = MaterialTheme.typography.titleSmall
                )
            }

            if(statusMovie){
                AddToListButton(
                    text = stringResource(R.string.remove_from_watchlist),
                    imageVector = Icons.Filled.Clear,
                    containerColors = Color.Black,
                    contentColor = Color.White,
                    onClickAction = { onAddToList() }
                )
            } else {
                AddToListButton(
                    text = stringResource(R.string.add_to_watchlist),
                    imageVector = Icons.Filled.Add,
                    containerColors = Color.Yellow,
                    contentColor = Color.Black,
                    onClickAction = { onAddToList() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailContentPreview(){
    CineListTheme {
        DetailContent(
            image = R.drawable.smatsv,
            title = MovieDataSource.dataMovie[11].name,
            synopsis = MovieDataSource.dataMovie[11].synopsis,
            duration = MovieDataSource.dataMovie[11].duration,
            rating = MovieDataSource.dataMovie[11].rating,
            onBackClick = {},
            onAddToList = {},
            statusMovie = false
        )
    }
}