package com.mkabore.yt.util

/**
 * Created by @author mkabore
 * 2020-10-25
 */
data class Resource<out T>(val status: ResultStatus, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(ResultStatus.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ResultStatus.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(ResultStatus.LOADING, data, null)
        }

    }

}