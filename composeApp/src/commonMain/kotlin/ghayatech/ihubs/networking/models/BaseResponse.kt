package ghayatech.ihubs.networking.models

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val data: T? = null,
    val message: String? = null,
    val status: Int? = null
)