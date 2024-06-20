package com.gerrysatria.cinelist.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gerrysatria.cinelist.R
import com.gerrysatria.cinelist.model.MovieDataSource
import com.gerrysatria.cinelist.ui.theme.CineListTheme

@Composable
fun WatchlistItem(
    image: Int,
    title: String,
    duration: String,
    modifier: Modifier = Modifier
){
    Card {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = stringResource(R.string.movie_poster)
            )
            Column(
                modifier = modifier
            ){
                Text(
                    text = title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    modifier = modifier.padding(horizontal = 6.dp)
                )
                Text(
                    text = duration,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = modifier.padding(horizontal = 6.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WatchlistItemPreview(){
    CineListTheme {
        WatchlistItem(
            image = R.drawable.smatsv,
            title = MovieDataSource.dataMovie[11].name,
            duration = MovieDataSource.dataMovie[11].duration
        )
    }
}