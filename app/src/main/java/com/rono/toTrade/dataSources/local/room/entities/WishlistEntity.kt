package com.reemmousa.toTrade.dataSources.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.reemmousa.toTrade.app.Constants.COURSES_TABLE
import com.reemmousa.toTrade.app.Constants.WISHLIST_TABLE
import com.reemmousa.toTrade.dataStructures.courses.Course
import com.reemmousa.toTrade.dataStructures.courses.ResponseCoursesList

@Entity(tableName = WISHLIST_TABLE)
class WishlistEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var course: Course
)