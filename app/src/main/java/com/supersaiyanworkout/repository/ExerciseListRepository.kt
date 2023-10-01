package com.supersaiyanworkout.repository

import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import com.supersaiyanworkout.BuildConfig
import com.supersaiyanworkout.network.ApiServices
import com.supersaiyanworkout.network.sealed.ApiResp
import com.supersaiyanworkout.utils.NO_INTERNET_CONNECTION
import com.supersaiyanworkout.utils.SERVER_ERROR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.HashMap
import javax.inject.Inject

class ExerciseListRepository @Inject constructor(
    private val workoutClient: ApiServices
) {

    suspend fun getExerciseList(data: HashMap<String?, Any?>) =
        flow {
            val tag = "exercise_list"
            emit(ApiResp.Loading(tag))
            data["username"] = "darkprnce"
            for (item in data.values) {
                if (item is String || item is Int || item is Double || item is Float) {
                    item.toString().trim()
                }
            }
            workoutClient.exercise_list(BuildConfig.API_KEY, data)
                .suspendOnSuccess {
                    if (this.data != null) {
                        if (this.data!!.status.equals("success")) {
                            emit(ApiResp.Success(tag, this.data))
                        } else {
                            emit(ApiResp.Error(tag, SERVER_ERROR, this.data!!.msg))
                        }
                    } else {
                        emit(ApiResp.Error(tag, SERVER_ERROR, null))
                    }
                }.suspendOnError {
                    emit(ApiResp.Error(tag, SERVER_ERROR, null))
                }.suspendOnException {
                    emit(ApiResp.Error(tag, NO_INTERNET_CONNECTION, null))
                }
        }.flowOn(Dispatchers.IO)

//    suspend fun addExercise(data: HashMap<String?, Any?>) =
//        flow {
//            val tag = "add_exercise"
//            emit(ApiResp.Loading(tag))
//            data.put("username", "darkprnce")
//            for (item in data.values) {
//                if (item is String || item is Int || item is Double || item is Float) {
//                    item.toString().trim()
//                }
//            }
//            workoutClient.exercise_add(BuildConfig.API_KEY, data)
//                .suspendOnSuccess {
//                    if (this.data != null) {
//                        if (this.data!!.status.equals("success")) {
//                            emit(ApiResp.Success(tag, this.data))
//                        } else {
//                            emit(ApiResp.Error(tag, SERVER_ERROR, this.data!!.msg))
//                        }
//                    } else {
//                        emit(ApiResp.Error(tag, SERVER_ERROR, null))
//                    }
//                }.suspendOnError {
//                    emit(ApiResp.Error(tag, SERVER_ERROR, null))
//                }.suspendOnException {
//                    emit(ApiResp.Error(tag, NO_INTERNET_CONNECTION, null))
//                }
//        }.flowOn(Dispatchers.IO)
//
//    suspend fun removeExercise(data: HashMap<String?, Any?>) =
//        flow {
//            val tag = "remove_exercise"
//            emit(ApiResp.Loading(tag))
//            data.put("username", "darkprnce")
//            for (item in data.values) {
//                if (item is String || item is Int || item is Double || item is Float) {
//                    item.toString().trim()
//                }
//            }
//            workoutClient.exercise_remove(BuildConfig.API_KEY, data)
//                .suspendOnSuccess {
//                    if (this.data != null) {
//                        if (this.data!!.status.equals("success")) {
//                            emit(ApiResp.Success(tag, this.data))
//                        } else {
//                            emit(ApiResp.Error(tag, SERVER_ERROR, this.data!!.msg))
//                        }
//                    } else {
//                        emit(ApiResp.Error(tag, SERVER_ERROR, null))
//                    }
//                }.suspendOnError {
//                    emit(ApiResp.Error(tag, SERVER_ERROR, null))
//                }.suspendOnException {
//                    emit(ApiResp.Error(tag, NO_INTERNET_CONNECTION, null))
//                }
//        }.flowOn(Dispatchers.IO)
}