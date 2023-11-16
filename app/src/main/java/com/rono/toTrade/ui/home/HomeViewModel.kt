package com.rono.toTrade.ui.home

import android.app.Application
import com.rono.toTrade.dataStructures.HomeCategory
import com.rono.toTrade.repository.Repository
import com.rono.toTrade.ui.allCourses.AllCoursesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: Repository,
    application: Application
) : AllCoursesViewModel(repository, application) {

    val homeCategories: List<HomeCategory> = repository.constants.homeCategories

}