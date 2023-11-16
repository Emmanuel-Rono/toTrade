package com.rono.toTrade.dataSources.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rono.toTrade.app.Constants.CART_TABLE
import com.rono.toTrade.dataStructures.courses.Course

@Entity(tableName = CART_TABLE)
class CartEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var course: Course
)