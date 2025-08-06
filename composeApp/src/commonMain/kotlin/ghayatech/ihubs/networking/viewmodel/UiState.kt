package ghayatech.ihubs.networking.viewmodel

import ghayatech.ihubs.networking.util.NetworkError

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val error: NetworkError) : UiState<Nothing>()
}
