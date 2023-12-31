package com.supersaiyanworkout.ui.screen.workoutlist.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.supersaiyanworkout.R
import com.supersaiyanworkout.model.WorkoutListBean
import com.supersaiyanworkout.network.sealed.ValidationState
import com.supersaiyanworkout.routes.Routes
import com.supersaiyanworkout.ui.customComposables.CustomButton
import com.supersaiyanworkout.ui.customComposables.CustomDialog
import com.supersaiyanworkout.ui.customComposables.CustomText
import com.supersaiyanworkout.ui.customComposables.GifImage
import com.supersaiyanworkout.ui.customComposables.MyToolbar
import com.supersaiyanworkout.ui.customComposables.ShimmerListItem
import com.supersaiyanworkout.ui.screen.SharedViewModel
import com.supersaiyanworkout.ui.screen.workoutlist.viewmodel.WorkoutListViewModel
import com.supersaiyanworkout.ui.theme.*

@Composable
fun WorkoutListScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    workoutListViewModel: WorkoutListViewModel = hiltViewModel(),
    navController: NavHostController,
    snackBarState: SnackbarHostState,
    sharedViewModel: SharedViewModel,
) {
    val context = LocalContext.current
    val refresh by sharedViewModel.isRefresh.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        if (refresh) {
            workoutListViewModel.getWorkouts()
        }
        workoutListViewModel.apiState.collect { apiState ->
            when (apiState) {
                is ValidationState.Loading -> {}

                is ValidationState.Error -> {
                    snackBarState.showSnackbar(
                        message = apiState.errorMsg,
                        duration = SnackbarDuration.Short
                    )
                }

                is ValidationState.Ideal -> {}

                is ValidationState.Success -> {}
            }
        }
    }

    val workouts by workoutListViewModel.workoutList.collectAsStateWithLifecycle()
    val showListLoading by workoutListViewModel.showLoading.collectAsStateWithLifecycle()
    val showRemoveWorkout by workoutListViewModel.showRemoveWorkout.collectAsStateWithLifecycle()

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

        if (showRemoveWorkout != null) {
            CustomDialog(
                onDismissRequest = {
                    workoutListViewModel.setshowRemoveWorkout(null)
                }
            ) {
                CustomText(
                    value = "${stringResource(id = R.string.remove_btn)} ${showRemoveWorkout!!.title}",
                    isheading = true,
                    txtsize = 18.sp,
                    modifier = modifier.align(Alignment.Start)
                )
                CustomText(
                    value = stringResource(R.string.do_you_want_to_remove_this_workout),
                    modifier = modifier.align(Alignment.Start)
                )
                Row {
                    CustomButton(
                        value = stringResource(R.string.cancel_btn),
                        bgcolor = Red,
                        onClick = {
                            workoutListViewModel.setshowRemoveWorkout(null)
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .weight(1.0f)
                    )
                    Spacer(modifier = Modifier.weight(0.1f))
                    CustomButton(
                        value = stringResource(R.string.remove_btn),
                        bgcolor = Green, onClick = {
                            workoutListViewModel.setshowRemoveWorkout(null)
                            val data = HashMap<String?, Any?>()
                            data["id"] = showRemoveWorkout!!.id
                            workoutListViewModel.removeWorkout(data)
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .weight(1.0f)
                    )
                }
            }
        }

        ShimmerListItem(
            isLoading = showListLoading,
            contentAfterLoading = {
                if (workouts.isNotEmpty()) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(
                            workouts,
                            key = { item -> "workout_${item.id!!}" }) { item ->
                            WorkoutItem(
                                modifier = modifier,
                                workout = item,
                                onClick = {
                                    sharedViewModel.setWorkout(item)
                                    navController.navigate(Routes.WorkoutDetail.route)
                                }, onLongClick = {
                                    workoutListViewModel.setshowRemoveWorkout(item)
                                }
                            )
                        }
                        item{
                            Spacer(modifier = Modifier.padding(paddingValues))
                        }
                    }
                } else {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        GifImage(
                            imageUrl = R.drawable.no_record_icon,
                            modifier = modifier.height(250.dp)
                        )
                        CustomText(
                            value = stringResource(R.string.no_workouts),
                            isheading = true,
                            txtsize = 18.sp,
                            modifier = modifier.padding(top = 10.dp)
                        )
                    }
                }
            })
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WorkoutItem(
    workout: WorkoutListBean.Data,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .combinedClickable(onClick = onClick, onLongClick = onLongClick)
    ) {
        Box(contentAlignment = Alignment.BottomStart) {
            GifImage(
                imageUrl = workout.image_url!!,
                contentScale = ContentScale.FillBounds,
                placeholder = R.drawable.banner,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .drawWithCache {
                        val gradient = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = size.height / 3,
                            endY = size.height
                        )
                        onDrawWithContent {
                            drawContent()
                            drawRect(gradient, blendMode = BlendMode.Multiply)
                        }
                    }
            )
            CustomText(
                workout.title!!,
                color = White,
                isbold = true,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Preview
@Composable
fun WorkoutListPreview() {
    val list = (1..5).map { WorkoutListBean.Data(id=it,title = "Workout $it", image_url = "") }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            list,
            key = { item -> "workout_${item.id!!}" }) { item ->
            WorkoutItem(
                modifier = Modifier,
                workout = item,
                onClick = {}, onLongClick = {}
            )
        }
    }
}