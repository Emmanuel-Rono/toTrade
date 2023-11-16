package com.rono.toTrade.dataSources.constants

import android.app.Application
import com.rono.toTrade.R
import com.rono.toTrade.app.Constants
import com.rono.toTrade.app.loadDrawable
import com.rono.toTrade.dataStructures.HomeCategory
import javax.inject.Inject

class ConstantsDataSource @Inject constructor(
    application: Application
) {
    val homeCategories: List<HomeCategory> = listOf(
        HomeCategory(Constants.SEARCH_DEVELOPMENT, application.loadDrawable(R.drawable.coding)),
        HomeCategory(Constants.SEARCH_FOREX, application.loadDrawable(R.drawable.business)),
        HomeCategory(Constants.SEARCH_ACCOUNTING, application.loadDrawable(R.drawable.accounting)),
        HomeCategory(
            Constants.SEARCH_IT_AND_SECURITY,
            application.loadDrawable(R.drawable.it_and_security)
        ),
        HomeCategory(
            Constants.SEARCH_GRAPHIC_DESIGN,
            application.loadDrawable(R.drawable.graphic_design)
        ),
        HomeCategory(Constants.SEARCH_MARKETING, application.loadDrawable(R.drawable.marketing))
    )

    val topSearchList = listOf(
        "Forex", "Crypto", "Technical Analysis", "ICT",
        "SMC", "Risk Management", "AlgoTrading", "Candlesticks", "News", "Trading",
        "Options", "Forex News"
    )
}