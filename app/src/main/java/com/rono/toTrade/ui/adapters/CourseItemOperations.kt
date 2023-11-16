package com.rono.toTrade.ui.adapters

import android.util.Log
import com.rono.toTrade.dataStructures.courses.Course

interface CourseItemOperations {
    fun onCourseClicked(course: Course) {
        Log.i("CourseItemClick", "$course short clicked")
    }

    fun onCourseBindViewHolder(holder: CoursesMainAdapter.MainCoursesVH) {
    }

    fun onCourseClicked(
        position: Int,
        course: Course,
        holder: CoursesMainAdapter.MainCoursesVH
    ) {
        Log.i("CourseItemClick", "$course short clicked")
    }

    fun onCourseLongClicked(
        position: Int,
        course: Course,
        holder: CoursesMainAdapter.MainCoursesVH
    ) {
        Log.i("CourseItemClick", "$course long clicked")
    }
}