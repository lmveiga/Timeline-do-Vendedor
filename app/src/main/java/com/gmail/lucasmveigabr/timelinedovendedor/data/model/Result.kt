package com.gmail.lucasmveigabr.timelinedovendedor.data.model

sealed class Result<T> {

    data class Success<T>(val data: T) : Result<T>()
    data class Failure<T>(val error: Throwable) : Result<T>()

}