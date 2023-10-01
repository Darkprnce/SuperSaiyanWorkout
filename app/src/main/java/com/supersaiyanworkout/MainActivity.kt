package com.supersaiyanworkout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.supersaiyanworkout.model.BottomNavigationItem
import com.supersaiyanworkout.routes.Routes
import com.supersaiyanworkout.ui.customComposables.CustomText
import com.supersaiyanworkout.ui.customComposables.MyScaffold
import com.supersaiyanworkout.ui.screen.SharedViewModel
import com.supersaiyanworkout.ui.screen.home.ui.HomeScreen
import com.supersaiyanworkout.ui.screen.splash.ui.SplashScreen
import com.supersaiyanworkout.ui.screen.workoutlist.ui.WorkoutListScreen
import com.supersaiyanworkout.ui.theme.SuperSaiyanWorkoutTheme
import com.supersaiyanworkout.ui.theme.White
import com.supersaiyanworkout.ui.theme.WhiteSmoke
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
                        content = { snackBarState ->
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
                                            sharedViewModel = sharedViewModel
                                        )
                                    })
                            }
                        }, bottomBar = {
                            AnimatedVisibility(
                                visible = bottomBarState.value,
                                enter = slideInVertically(initialOffsetY = { it }),
                                exit = slideOutVertically(targetOffsetY = { it }),
                                content = {
                                    Column(modifier=Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Card(
                                            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                                            shape = RoundedCornerShape(20.dp),
                                            modifier = Modifier
                                                .padding(bottom = 10.dp)
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .padding(horizontal = 10.dp)
                                            ) {
                                                BottomNavigationItem().bottomNavigationItems()
                                                    .forEachIndexed { index, navigationItem ->
                                                        Column(
                                                            modifier = Modifier
                                                                .width(80.dp)
                                                                .clickable {
                                                                    navPos = index
                                                                    navController.navigate(
                                                                        navigationItem.route
                                                                    ) {
                                                                        popUpTo(navController.graph.findStartDestination().id) {
                                                                            saveState = true
                                                                        }
                                                                        launchSingleTop = true
                                                                        restoreState = true
                                                                    }
                                                                },
                                                            verticalArrangement = Arrangement.Center,
                                                            horizontalAlignment = Alignment.CenterHorizontally
                                                        ) {
                                                            AnimatedVisibility(
                                                                visible = index == navPos,
                                                                enter = slideInHorizontally(
                                                                    initialOffsetX = { it }),
                                                                exit = slideOutHorizontally(
                                                                    targetOffsetX = { it }),
                                                                content = {
                                                                    Card(
                                                                        colors = CardDefaults.cardColors(
                                                                            containerColor = MaterialTheme.colorScheme.primary
                                                                        ),
                                                                        shape = RoundedCornerShape(
                                                                            bottomStart = 20.dp,
                                                                            bottomEnd = 20.dp
                                                                        ),
                                                                        modifier = Modifier
                                                                            .height(
                                                                                10.dp
                                                                            )
                                                                            .fillMaxWidth()
                                                                            .padding(horizontal = 10.dp).padding(bottom = 10.dp)
                                                                    ) {}
                                                                })

                                                            AnimatedVisibility(
                                                                visible = index == navPos,
                                                                enter = slideInVertically(
                                                                    initialOffsetY = { it }),
                                                                exit = slideOutVertically(
                                                                    targetOffsetY = { it }),
                                                                content = {
                                                                    CustomText(
                                                                        navigationItem.label,
                                                                        modifier = Modifier.padding(10.dp)
                                                                    )
                                                                })
                                                            AnimatedVisibility(
                                                                visible = index != navPos,
                                                                enter = slideInVertically(
                                                                    initialOffsetY = { it }),
                                                                exit = slideOutVertically(
                                                                    targetOffsetY = { it }),
                                                                content = {
                                                                    Icon(
                                                                        navigationItem.icon,
                                                                        tint=MaterialTheme.colorScheme.primary,
                                                                        contentDescription = navigationItem.label,
                                                                        modifier = Modifier.padding(10.dp)
                                                                    )
                                                                })
                                                        }
                                                    }
                                            }
                                        }
                                    }

                                    /*
                                                                        NavigationBar(
                                                                            containerColor = MaterialTheme.colorScheme.primary,
                                                                            modifier = Modifier
                                                                                .padding(horizontal = 50.dp)
                                                                                .padding(bottom = 10.dp)
                                                                                .clip(RoundedCornerShape(20.dp))
                                                                        ) {
                                                                            BottomNavigationItem().bottomNavigationItems()
                                                                                .forEachIndexed { index, navigationItem ->
                                                                                    NavigationBarItem(
                                                                                        selected = index == navPos,
                                                                                        label = {
                                                                                            CustomText(
                                                                                                navigationItem.label, color =
                                                                                                if (index == navPos) {
                                                                                                    White
                                                                                                } else {
                                                                                                    WhiteSmoke
                                                                                                }
                                                                                            )
                                                                                        },
                                                                                        icon = {
                                                                                            Icon(
                                                                                                navigationItem.icon,
                                                                                                contentDescription = navigationItem.label
                                                                                            )
                                                                                        },
                                                                                        onClick = {
                                                                                            navPos = index
                                                                                            navController.navigate(navigationItem.route) {
                                                                                                popUpTo(navController.graph.findStartDestination().id) {
                                                                                                    saveState = true
                                                                                                }
                                                                                                launchSingleTop = true
                                                                                                restoreState = true
                                                                                            }
                                                                                        }
                                                                                    )
                                                                                }
                                                                        }
                                    */
                                })
                        })
                }
            }
        }
    }
}


