package ghayatech.ihubs.networking.models

import kotlinx.serialization.Serializable

@Serializable
data class ListBaseResponse<T>(
    val data: List<T> = listOf(),
    val message: String? = null,
    val status: Int? = null
)