package ghayatech.ihubs.networking.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val name: String,
    val phone: String,
    val specialty: String,
    val password: String,
    @SerialName("password_confirmation") val passwordConfirmation: String
)

@Serializable
data class VerifyPhoneRequest(
    val phone: String,
    val code: String
)

@Serializable
data class LoginRequest(
    val phone: String,
    val password: String
)

@Serializable
data class UpdateProfileRequest(
    val name: String,
    val phone: String,
    val specialty: String
)

@Serializable
data class CreateBookingRequest(
    @SerialName("workspace_id") val workspaceId: Int,
    @SerialName("package_id") val packageId: Int,
    val date: String
)

@Serializable
data class CreateBookingWithHoursRequest(
    @SerialName("workspace_id") val workspaceId: Int,
    @SerialName("package_id") val packageId: Int,
    val date: String,
    val time: String,
    @SerialName("number_of_hours") val numberOfHours: Int
)

@Serializable
data class CreateServiceRequest(
    val type: String,  // "cafe_request", "seat_change"
    val details: String
)

@Serializable
data class StartConversationRequest(
    @SerialName("secretary_id") val secretaryId: Int
)

@Serializable
data class SendMessage(
    val body: String,
    val attachment: String? = null
)

@Serializable
data class GetGovernorates(
    val governorate: Governorate
)
