package com.rono.toTrade.dataSources.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rono.toTrade.app.Constants.WISHLIST_TABLE
import com.rono.toTrade.dataStructures.courses.Course

@Entity(tableName = WISHLIST_TABLE)
class WishlistEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var course: Course
)