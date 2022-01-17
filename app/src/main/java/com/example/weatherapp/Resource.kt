package com.example.weatherapp

import androidx.annotation.NonNull
import androidx.annotation.Nullable


class Resource<T>(
    val status: Status,
    @field:Nullable @param:Nullable val data: T?,
    @field:Nullable @param:Nullable val message: String?
) {
    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(@Nullable data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, @Nullable data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(@Nullable data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}