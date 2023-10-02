package com.supersaiyanworkout.ui.screen.progress.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.supersaiyanworkout.routes.Routes
import com.supersaiyanworkout.ui.customComposables.CustomText
import com.supersaiyanworkout.ui.customComposables.MyToolbar
import com.supersaiyanworkout.ui.screen.SharedViewModel
import com.supersaiyanworkout.ui.screen.workoutlist.viewmodel.WorkoutListViewModel
import com.supersaiyanworkout.ui.theme.White


@Composable
fun ProgressScreen(
    modifier: Modifier = Modifier,
    workoutListViewModel: WorkoutListViewModel = hiltViewModel(),
    navController: NavHostController,
    snackBarState: SnackbarHostState,
    sharedViewModel: SharedViewModel,
) {
    Column(
        modifier = Modifier
    ) {
        MyToolbar(
            title = "Workout List",
            isbackAvailable = false,
            navHostController = navController,
            actions = {
                IconButton(onClick = {
                    sharedViewModel.setWorkout(null)
                    navController.navigate(Routes.WorkoutDetail.route)
                }) {
                    Icon(
                        Icons.Filled.Add,
                        "add_icon",
                        tint = White
                    )
                }
            },
        )
        CustomText(value = "Progress")
    }
}