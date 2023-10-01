package com.supersaiyanworkout.ui.screen

import androidx.lifecycle.ViewModel
import com.supersaiyanworkout.model.ExerciseListBean
import com.supersaiyanworkout.model.WorkoutListBean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {

    private val _selectedWorkout = MutableStateFlow(WorkoutListBean.Data())
    val selectedWorkout = _selectedWorkout.asStateFlow()

    fun setWorkout(workout: WorkoutListBean.Data?) {
        setRefresh(false)
        _selectedWorkout.value = workout ?: WorkoutListBean.Data()
    }

    private val _selectedExercise = MutableStateFlow(ExerciseListBean.Data())
    val selectedExercise = _selectedExercise.asStateFlow()

    fun setExercise(exercise: ExerciseListBean.Data?) {
        setRefresh(false)
        _selectedExercise.value = exercise ?: ExerciseListBean.Data()
    }

    private val _isRefreshFlow = MutableStateFlow(false)
    val isRefresh = _isRefreshFlow.asStateFlow()
    fun setRefresh(value: Boolean = false) {
        _isRefreshFlow.value = value
    }
}