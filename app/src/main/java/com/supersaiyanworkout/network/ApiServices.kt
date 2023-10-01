package com.supersaiyanworkout.network

import com.skydoves.sandwich.ApiResponse
import com.supersaiyanworkout.model.ExerciseListBean
import com.supersaiyanworkout.model.WorkoutListBean
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiServices {

    @POST("workout_list")
    suspend fun workout_list(
        @Header("api_key") api_key: String,
        @Body body: HashMap<String?, Any?>?
    ): ApiResponse<WorkoutListBean?>

    @POST("exercise_list")
    suspend fun exercise_list(
        @Header("api_key") api_key: String,
        @Body body: HashMap<String?, Any?>?
    ): ApiResponse<ExerciseListBean?>

}