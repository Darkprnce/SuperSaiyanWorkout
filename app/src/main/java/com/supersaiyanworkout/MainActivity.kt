package com.supersaiyanworkout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.supersaiyanworkout.model.BottomNavigationItem
import com.supersaiyanworkout.routes.Routes
import com.supersaiyanworkout.ui.customComposables.CustomBottomBar
import com.supersaiyanworkout.ui.customComposables.CustomText
import com.supersaiyanworkout.ui.customComposables.MyScaffold
import com.supersaiyanworkout.ui.customComposables.noRippleClickable
import com.supersaiyanworkout.ui.screen.SharedViewModel
import com.supersaiyanworkout.ui.screen.home.ui.HomeScreen
import com.supersaiyanworkout.ui.screen.progress.ui.ProgressScreen
import com.supersaiyanworkout.ui.screen.splash.ui.SplashScreen
import com.supersaiyanworkout.ui.screen.workoutlist.ui.WorkoutListScreen
import com.supersaiyanworkout.ui.theme.SuperSaiyanWorkoutTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperSaiyanWorkoutTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    var navPos by remember { mutableIntStateOf(0) }
                    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
                    val sharedViewModel: SharedViewModel = hiltViewModel()

                    MyScaffold(
                        showConnectivity = true,
                        navHostController = navController,
                        content = { snackBarState, paddingValues ->
                            NavHost(
                                navController = navController,
                                startDestination = Routes.Splash.route
                            ) {
                                composable(
                                    route = Routes.Splash.route,
                                    content = {
                                        LaunchedEffect(Unit) {
                                            bottomBarState.value = false
                                        }
                                        SplashScreen(navController = navController)
                                    })

                                composable(
                                    route = Routes.Home.route,
                                    content = {
                                        LaunchedEffect(Unit) {
                                            bottomBarState.value = true
                                            navPos = 0
                                            Timber.e("HOme")
                                        }
                                        HomeScreen()
                                    })

                                composable(
                                    route = Routes.Workout.route,
                                    content = {
                                        LaunchedEffect(Unit) {
                                            bottomBarState.value = true
                                            navPos = 1
                                            Timber.e("Workout")
                                        }
                                        WorkoutListScreen(
                                            navController = navController,
                                            snackBarState = snackBarState,
                                            sharedViewModel = sharedViewModel,
                                            paddingValues = paddingValues
                                        )
                                    })

                                composable(
                                    route = Routes.Progress.route,
                                    content = {
                                        LaunchedEffect(Unit) {
                                            bottomBarState.value = true
                                            navPos = 2
                                        }
                                        ProgressScreen(
                                            navController = navController,
                                            snackBarState = snackBarState,
                                            sharedViewModel = sharedViewModel
                                        )
                                    })
                            }
                        }, bottomBar = {
                            CustomBottomBar(
                                list = BottomNavigationItem().bottomNavigationItems(),
                                navController =navController,
                                isBottomBarVisible = bottomBarState.value,
                                selected = navPos
                            )
                        })
                }
            }
        }
    }
}


