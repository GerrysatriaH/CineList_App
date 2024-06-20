package com.gerrysatria.cinelist.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun MovieItem(
    image: Int,
    title: String,
    duration: String,
    modifier: Modifier = Modifier,
){
    Card(
        shape = RoundedCornerShape(6.dp),
        modifier = modifier.width(160.dp)
    ) {
        Column {
            Image(
                painter = painterResource(image),
                contentDescription = stringResource(R.string.movie_poster),
                modifier = modifier
                    .size(170.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .padding(8.dp)
            )
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = modifier
                    .padding(vertical = 4.dp, horizontal = 8.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.padding(8.dp)
            ){
                Icon(
                    painter = painterResource(R.drawable.schedule),
                    contentDescription = stringResource(R.string.duration_icon),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = duration,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = modifier.padding(horizontal = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieItemPreview(){
    CineListTheme {
        MovieItem(
            image = R.drawable.smatsv,
            title = MovieDataSource.dataMovie[11].name,
            duration = MovieDataSource.dataMovie[11].duration
        )
    }
}