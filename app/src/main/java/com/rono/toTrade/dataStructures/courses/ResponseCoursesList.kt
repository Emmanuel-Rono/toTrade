package com.rono.toTrade.dataStructures.courses

import com.google.gson.annotations.SerializedName

data class ResponseCoursesList(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("results")
    val courses: List<Course?>?
)
