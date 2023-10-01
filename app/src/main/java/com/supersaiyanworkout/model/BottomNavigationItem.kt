package com.supersaiyanworkout.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.supersaiyanworkout.routes.Routes

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Build,
    val route : String = ""
) {

    //function to get the list of bottomNavigationItems
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Home",
                icon = Icons.Filled.Home,
                route = Routes.Home.route
            ),
            BottomNavigationItem(
                label = "Workouts",
                icon = Icons.Filled.List,
                route = Routes.Workout.route
            ),
            BottomNavigationItem(
                label = "Progress",
                icon = Icons.Filled.DateRange,
                route = Routes.Home.route
            ),
        )
    }
}