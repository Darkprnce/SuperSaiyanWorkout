package com.supersaiyanworkout.ui.screen.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.supersaiyanworkout.R
import com.supersaiyanworkout.ui.customComposables.CustomText
import com.supersaiyanworkout.ui.customComposables.GifImage
import com.supersaiyanworkout.ui.theme.AppHightlightColor
import com.supersaiyanworkout.ui.theme.typography

@Preview(showBackground = true)
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                CustomText("Hello Saiyan", txtStyle = MaterialTheme.typography.bodyMedium)
                CustomText("Let's start your day", txtStyle = MaterialTheme.typography.headlineSmall)
            }
            GifImage(
                imageUrl = R.drawable.user_icon, modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Categories", style = typography.bodyMedium)
            Text(text = "See all", style = typography.bodyMedium, color = AppHightlightColor)
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 10.dp)
        ) {
            items(20) {
                GifImage(
                    imageUrl = R.drawable.placeholder, modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                )
            }
        }
    }
}
