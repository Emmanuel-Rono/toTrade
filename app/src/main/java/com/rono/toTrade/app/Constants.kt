package com.rono.toTrade.app


object Constants {
    const val BASE_URL = "https://www.udemy.com/api-2.0/"
    const val UDEMY_KEY="WXdqTW5pck52WVRCOFY4Q0d2dzA3ZGRrYVMxcTBWeFZBaGE4YkF4Rjp4dVhMcTk4RHJOWERVVWdyRjRQdTFZM09uNWdRUU1RU2wzanlmd3IwWUpNYXJqOVB4aEt4Q3FITmpzYzhhNVE4NEVxOEVxR21EOHI4OGdTdFYxc3ZBMUU0eVlzUHVDTDNpYlgybUVxT3lvOE1hclI4UFg4ZGNveXQ2UGZrRURjRA=="
    // Encode client_id and client_secret
    const val HEADER_ACCESS = "Accept: application/json, text/plain, */*"
    const val HEADER_CONTENT_TYPE = "Content-Type: application/json;charset=utf-8"
  const val HEADER_AUTHORIZATION  = "Authorization: Basic $UDEMY_KEY"

    const val UDEMY_BASE_URL = "https://www.udemy.com"

    // SEARCH QUERIES
    const val SEARCH_ALL = "all"
    const val SEARCH_DEVELOPMENT = "AlgoTrading"
    const val SEARCH_FOREX = "Forex"
    const val SEARCH_MARKETING = "Risk Management"
    const val SEARCH_GRAPHIC_DESIGN = "Technical Analysis"
    const val SEARCH_IT_AND_SECURITY = "ICT"
    const val SEARCH_ACCOUNTING = "Price Action"
    const val QUERY_PRICE_FREE = "price-free"
    const val QUERY_PRICE_PAID = "price-paid"

    // Prefs
    const val COURSE_PRICE_FREE = "Free"
    const val COURSE_PRICE_PAID = "Paid"
    const val DEFAULT_COURSE_CATEGORY = "Business"
    const val PREFS = "prefs"
    const val PREF_PRICE = "prefPrice"
    const val PREF_PRICE_ID = "prefPriceId"
    const val PREF_CATEGORY = "prefCategory"
    const val PREF_CATEGORY_ID = "prefCategoryId"

    // Room DB
    const val DATABASE_NAME = "courses_database"
    const val COURSES_TABLE = "courses_table"
    const val WISHLIST_TABLE = "wishlist_table"
    const val CART_TABLE = "cart_table"


  const val PRIVACY_POLICY_URL = "https://github.com/ReemHazzaa/EduApp/blob/master/PrivacyPolicy.md#contact"


  object Constants {
    const val CONSUMER_SECRET="VyYS5Cp8CVhLnAFP"
    const val CONSUMER_KEY="0YTxkerRspqwmiqqw3th223MYuQl1tXj"
    const val PASS_KEY="MFlUeGtlclJzcHF3bWlxcXczdGgyMjNNWXVRbDF0WGo6VnlZUzVDcDhDVmhMbkFGUA=="
  }

}