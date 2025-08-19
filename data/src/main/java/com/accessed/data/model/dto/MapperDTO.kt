package com.accessed.data.model.dto

interface MapperDTO<T> {
    fun toDomain(): T
}