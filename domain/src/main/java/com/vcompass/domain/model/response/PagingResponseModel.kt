package com.vcompass.domain.model.response

data class PagingResponseModel<E>(
    val content: List<E>? = listOf(),
    val totalPages: Long? = 0,
    val last: Boolean? = false,
    val totalElements: Long? = 0,
    val numberOfElements: Long? = 0,
    val hasPrePage: Boolean? = null,
    val hasNextPage: Boolean? = null
)

fun PagingResponseModel<*>.checkLastPage(currentPage: Int): Boolean {
    return when {
        totalPages == null || totalPages.toInt() == 0 -> true
        else -> currentPage == totalPages.toInt()
    }
}