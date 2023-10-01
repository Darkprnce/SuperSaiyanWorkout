package com.supersaiyanworkout.ui.screen.splash.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.supersaiyanworkout.R
import com.supersaiyanworkout.routes.Routes
import com.supersaiyanworkout.ui.customComposables.GifImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(Unit) {
        delay(1000)
        navController.navigate(Routes.Home.route){
            popUpTo(Routes.Splash.route) {
                inclusive = true
            }
        }
    }
    GifImage(
        imageUrl = R.drawable.splash,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
}

