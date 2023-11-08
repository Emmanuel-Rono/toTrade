package com.reemmousa.toTrade.dataSources.remote

import com.reemmousa.toTrade.dataSources.remote.retrofit.CoursesApi
import com.reemmousa.toTrade.dataStructures.courses.Course
import com.reemmousa.toTrade.dataStructures.courses.ResponseCoursesList
import com.reemmousa.toTrade.dataStructures.lectures.ResponseCourseLectures
import com.reemmousa.toTrade.dataStructures.reviews.ResponseCourseReview
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val coursesApi: CoursesApi
) {
    suspend fun getCourses(
        searchString: String?,
        pageNo: Int,
        price: String? = null
    ): Response<ResponseCoursesList?>? {
        return coursesApi.getCoursesList(
            searchString,
            pageNo,
            price
        )
    }

    suspend fun getCourseDetails(
        courseId: String
    ): Response<Course?>? {
        return coursesApi.getCourseDetails(courseId)
    }

    suspend fun getCourseReviews(
        courseId: String,
        pageNo: Int
    ): Response<ResponseCourseReview?>? {
        return coursesApi.getCourseReviews(courseId, pageNo)
    }

    suspend fun getCourseLectures(
        courseId: String,
        pageNo: Int
    ): Response<ResponseCourseLectures?>? {
        return coursesApi.getCourseLectures(courseId, pageNo)
    }
}