package com.covid.covidapps

import java.lang.Exception

sealed class Result<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Init<T>(): Result<T>()
    class Success<T>(data: T) : Result<T>(data)
    class Loading<T>(data: T? = null) : Result<T>(data)
    class Error<T>(message: String, data: T? = null) : Result<T>(data, message)
}