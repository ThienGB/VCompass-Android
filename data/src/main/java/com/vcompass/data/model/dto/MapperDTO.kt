package com.vcompass.data.model.dto

interface MapperDTO<T> {
    fun toDomain(): T
}