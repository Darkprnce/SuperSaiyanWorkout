package com.supersaiyanworkout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.supersaiyanworkout.model.BottomNavigationItem
import com.supersaiyanworkout.routes.Routes
import com.supersaiyanworkout.ui.customComposables.CustomBottomBar
import com.supersaiyanworkout.ui.customComposables.MyScaffold
import com.supersaiyanworkout.ui.screen.SharedViewModel
import com.supersaiyanworkout.ui.screen.home.ui.HomeScreen
import com.supersaiyanworkout.ui.screen.progress.ui.ProgressScreen
import com.supersaiyanworkout.ui.screen.splash.ui.SplashScreen
import com.supersaiyanworkout.ui.screen.workoutlist.ui.WorkoutListScreen
import com.supersaiyanworkout.ui.theme.SuperSaiyanWorkoutTheme
import dagger.hilt.android.AndroidEntryPoint

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
                                        }
                                        HomeScreen()
                                    })
                                composable(
                                    route = Routes.Workout.route,
                                    content = {
                                        LaunchedEffect(Unit) {
                                            bottomBarState.value = true
                                            navPos = 1
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
                                selected = navPos,
                            )
                        })
                }
            }
        }
    }
}


