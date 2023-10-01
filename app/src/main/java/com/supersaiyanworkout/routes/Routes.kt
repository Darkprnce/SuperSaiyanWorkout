package com.supersaiyanworkout.routes

sealed class Routes(val route:String){
    object Splash : Routes(route = "splash")
    object Home : Routes(route = "home")
    object Workout : Routes(route = "workout")
    object WorkoutDetail : Routes(route = "workout_detail")
    object Progress : Routes(route = "progress")



    fun withArgs(vararg args:String) : String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}