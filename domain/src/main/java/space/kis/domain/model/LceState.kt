package space.kis.domain.model

sealed class LceState<out T> {
    object Loading : LceState<Nothing>()

    data class Content<T>(val country: T) : LceState<T>()

    data class Error(val throwable: Throwable) : LceState<Nothing>()
}