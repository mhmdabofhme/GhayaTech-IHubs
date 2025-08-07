package ghayatech.ihubs.networking.util

import androidx.compose.runtime.Composable
import ghayatech.ihubs.ui.theme.AppStrings
import ghayatech.ihubs.ui.theme.AppStringsProvider
import ihubs.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.stringResource


sealed class NetworkError : Error {
    data class HttpError(val code: Int, val message: String) : NetworkError()
    data object RequestTimeout : NetworkError()
    data object Unauthorized : NetworkError()
    data object Conflict : NetworkError()
    data object TooManyRequests : NetworkError()
    data object NoInternet : NetworkError()
    data object PayloadTooLarge : NetworkError()
    data object ServerError : NetworkError()
    data object Serialization : NetworkError()
    data object Unknown : NetworkError()
}

@Composable
fun NetworkError.getMessage(): String {

    val strings = AppStringsProvider.current()

    return when (this) {
        is NetworkError.Unauthorized -> strings.unauthorized
        is NetworkError.Conflict -> strings.conflict
        is NetworkError.PayloadTooLarge -> strings.too_many_requests
        is NetworkError.TooManyRequests -> strings.too_many_requests
        is NetworkError.ServerError -> strings.server_error
        is NetworkError.Serialization -> strings.unknown
        is NetworkError.NoInternet -> strings.no_internet
        is NetworkError.RequestTimeout -> strings.request_timeout
        is NetworkError.Unknown -> strings.unknown
        is NetworkError.HttpError -> this.message
    }
}