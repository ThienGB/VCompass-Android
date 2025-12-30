package com.vcompass.data.util

object DataConstants {
    const val PAGE_SIZE_20 = 20
    const val TIME_OUT = 30L //30s
    const val NETWORK_ERROR_CODE = 1001
    const val NETWORK_ERROR_MESS = "Network connection failed"

    //named Service
    const val FEED_SERVICE = "FEED_SERVICE"
    const val PARTY_SERVICE = "PARTY_SERVICE"
    const val JOB_SERVICE = "JOB_SERVICE"
    const val OTHER_SERVICE = "OTHER_SERVICE"
    const val MESSAGE_SERVICE = "MESSAGE_SERVICE"
    const val PAYMENT_SERVICE = "PAYMENT_SERVICE"
    const val AUTH_SERVICE = "AUTH_SERVICE"

    //suffix URL
    const val SUFFIX_URL_CALENDAR_SERVICE = "calendar-service/"
    const val SUFFIX_URL_FEED_SERVICE = "feed-service/"
    const val SUFFIX_URL_PARTY_SERVICE = "party-service/"
    const val SUFFIX_URL_JOB_SERVICE = "job-service/"
    const val SUFFIX_URL_MESSAGE_SERVICE = "message-service/"
    const val SUFFIX_URL_PAYMENT_SERVICE = "payment-service/"
    const val SUFFIX_URL_AUTH_SERVICE = "auth/"

    //version
    const val SUFFIX_URL_V1 = "api/v1/"
}