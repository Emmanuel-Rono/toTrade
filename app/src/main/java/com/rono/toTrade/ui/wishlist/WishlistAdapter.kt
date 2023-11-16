package com.rono.toTrade.ui.wishlist

import com.rono.toTrade.ui.adapters.CourseItemOperations
import com.rono.toTrade.ui.adapters.CoursesMainAdapter

class WishlistAdapter(private val courseItemOperations: CourseItemOperations) :
    CoursesMainAdapter(courseItemOperations) {

    override fun onBindViewHolder(holder: MainCoursesVH, position: Int) {
        super.onBindViewHolder(holder, position)
        courseItemOperations.onCourseBindViewHolder(holder)
    }

}