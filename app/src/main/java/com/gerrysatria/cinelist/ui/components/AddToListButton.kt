package com.gerrysatria.cinelist.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun AddToListButton(
    text: String,
    imageVector: ImageVector,
    containerColors: Color,
    contentColor: Color,
    onClickAction: () -> Unit,
    modifier: Modifier = Modifier
){
    Button(
        onClick = { onClickAction() },
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColors,
            contentColor = contentColor
        ),
        modifier = modifier.padding(16.dp).fillMaxWidth()
            .semantics(mergeDescendants = true) {
                contentDescription = "Add To List Button"
            }
    ){
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = modifier.padding(end = 8.dp)
        )
        Text(
            text = text
        )
    }
}