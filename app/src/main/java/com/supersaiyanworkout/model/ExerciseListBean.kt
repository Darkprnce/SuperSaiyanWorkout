package com.supersaiyanworkout.model

data class ExerciseListBean(
    var `data`: List<Data>? = null,
    var msg: String? = null,
    var response_time: String? = null,
    var status: String? = null
) {
    data class Data(
        var name: String? = null,
        var duration: Int? = null,
        var bodypart: String? = null,
        var equipment: String? = null,
        var gifurl: String? = null,
        var id: Int? = null,
        var reps: Int? = null,
        var rest_after_completion: Int? = null,
        var rests: Int? = null,
        var sets: Int? = null,
        var target: String? = null,
        var isshow: String? = null,
        var selected: Boolean = false
    )
}