package com.vcompass.domain.utils

class MyConstants {
    companion object {
        const val TYPE_PDF = "application/pdf"
        const val TYPE_DOCUMENT = "application/*"
        const val TYPE_VIDEO = "video/*"
        const val TYPE_IMAGE = "image/*"

        //Limit size file
        private const val SIZE_1_MB = 1024L
        const val LIMIT_SIZE_PDF_FILE = 5 * SIZE_1_MB
        const val SIZE_LIMIT_IMAGE = 5L
        const val SIZE_LIMIT_FILE = 10L

        const val COUNT_DOWN_TIMER_OTP = 60_000L

        private const val A_SECOND = 1000
        const val A_MINUTE = 60 * A_SECOND
        const val A_HOUR = 60 * A_MINUTE
        const val A_DAY = 24 * A_HOUR

        const val MAX_LENGTH_ITEM_MESSAGE = 200

        const val DEFAULT_SIZE_ITEM = 10
        const val LIMIT_MULTI_IMAGE = 20

        const val PHONE_NUMBER_MIN_LENGTH = 9
        const val PHONE_NUMBER_MAX_LENGTH = 15

        const val SHOW_MAX_NO_OF_MEMBER = 4
        const val DEFAULT_CURRENCY = "USD"

        const val GOOGLE_MAP_PACKAGE = "com.google.android.apps.maps"
        const val GOOGLE_MAP_API_KEY = "AIzaSyDirKvPSX_oqVjtSVx-uOlxI3mNWxqhZ0k"

        const val NOTIFICATION_TYPE_KEY = "notification_type"
        const val NOTIFICATION_CHILD_TYPE_KEY = "notification_child_type"

        const val META_URL_SCHEDULE =
            "{{recruiter.id}}/calendar?candidateId={{candidate.id}}&applicationId={{application.id}}"

        const val BASE_URL_BOX_EDITOR = "https://talent.accessed.co/pages/editor/?type="
        const val KEY_BODY_HTML_EMAIL = "bodyHtml"

        const val QR_CODE_URL = "qr_code_url"
        const val FILE_NAME = "file_name"
    }
}