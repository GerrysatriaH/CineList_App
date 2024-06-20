package com.gerrysatria.cinelist.ui.screen.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gerrysatria.cinelist.R
import com.gerrysatria.cinelist.ui.theme.CineListTheme

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        AboutContent(
            photo = R.drawable.photo_gerry,
            name = stringResource(R.string.about_name),
            email = stringResource(R.string.about_email)
        )
    }
}

@Composable
fun AboutContent(
    photo: Int,
    name: String,
    email: String,
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ){
        Image(
            painter = painterResource(photo),
            contentDescription = stringResource(R.string.about_image),
            contentScale = ContentScale.Crop,
            modifier = modifier
                .width(200.dp)
                .height(200.dp)
                .clip(CircleShape)
            )
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = modifier.padding(top = 20.dp)
        )
        Text(
            text = email,
            color = Color.DarkGray,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontStyle = FontStyle.Italic
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AboutContentPreview(){
    CineListTheme {
        AboutContent(
            photo = R.drawable.photo_gerry,
            name = stringResource(R.string.about_name),
            email = stringResource(R.string.about_email)
        )
    }
}