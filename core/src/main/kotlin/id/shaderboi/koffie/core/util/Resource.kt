package id.shaderboi.koffie.core.util

sealed class Resource<T> {
    class Loading<T> : Resource<T>()
    data class Loaded<T>(val data: T) : Resource<T>()
    data class Error<T>(val error: Throwable) : Resource<T>()
}
