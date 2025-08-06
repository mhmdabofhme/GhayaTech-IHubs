package ghayatech.ihubs.networking.util

import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import io.ktor.util.network.UnresolvedAddressException
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import ghayatech.ihubs.networking.models.BaseResponse

suspend fun Throwable.toNetworkError(): NetworkError {

    return when (this) {
        is ClientRequestException -> {
            val code = this.response.status.value
            val message = try {
                val errorBody = this.response.bodyAsText()
                Json.decodeFromString<BaseResponse<Unit>>(errorBody).message
            } catch (e: Exception) {
                "حدث خطأ غير متوقع"
            }

            when (code) {
                HttpStatusCode.Unauthorized.value -> NetworkError.Unauthorized
                HttpStatusCode.Conflict.value -> NetworkError.Conflict
                HttpStatusCode.PayloadTooLarge.value -> NetworkError.PayloadTooLarge
                HttpStatusCode.TooManyRequests.value -> NetworkError.TooManyRequests
                in 500..599 -> NetworkError.ServerError
                else -> NetworkError.HttpError(code, message.toString())
            }
        }
        is ServerResponseException -> NetworkError.ServerError
        is SerializationException -> NetworkError.Serialization
        is IOException,
        is UnresolvedAddressException -> NetworkError.NoInternet
        is SocketTimeoutException -> NetworkError.RequestTimeout
        else -> {
            println("Unhandled exception: ${this::class.simpleName} - ${this.message}")
            NetworkError.Unknown
        }
    }
}
