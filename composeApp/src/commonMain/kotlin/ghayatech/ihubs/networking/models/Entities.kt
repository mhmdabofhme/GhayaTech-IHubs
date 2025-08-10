package ghayatech.ihubs.networking.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
    val user: User? = null
)

@Serializable
data class User(
    val id: Int,
    val name: String,
    val phone: String,
    val specialty: String? = null,
    @SerialName("profile_image") val profileImage: String? = null
)


@Serializable
data class RegisterResponse(
    val code: String,
)


@Serializable
data class VerificationResponse(
    val token: String,
    val user: User? = null
)


@Serializable
data class WorkspaceListResponse(
    val workspaces: List<Workspace>
)

@Serializable
data class Workspace(
    val id: Int,
    val name: String,
    val location: String,
    val bank_payment_supported: Boolean,
    val description: String? = null,
    val logo: String? = null,
    val governorate: SimpleData? = null,
    val region: SimpleData? = null,
    @SerialName("has_evening_shift") val hasEventShift: Boolean,
    @SerialName("has_free") val hasFree: Boolean,
)

@Serializable
data class WorkspaceDetails(
    val id: Int,
    val name: String,
    val location: String,
    val description: String? = null,
    val logo: String? = null,
    val governorate: SimpleData? = null,
    val region: SimpleData? = null,
    val images: List<String>,
    val features: List<String>,
    @SerialName("short_services") val shortServices: List<String>,
    val secretary: Secretary,
)


@Serializable
data class Secretary(val id: Int, val name: String, val phone: String)


@Serializable
data class SimpleData(
    val id: String,
    val name: String
)


@Serializable
data class PackagesResponse(
    val payment: PaymentInfo,
    val packages: List<WorkspacePackage>
)


@Serializable
data class PaymentInfo(
    @SerialName("bank_payment_supported") val bankPaymentSupported: String,
    @SerialName("bank_account_number") val bankAccountNumber: String,
    @SerialName("mobile_payment_number") val mobilePaymentNumber: String
)


@Serializable
data class WorkspacePackage(
    val id: Int,
    @SerialName("workspace_id") val workspaceId: String,
    val name: String,
    val price: String,
    val duration: String
)


@Serializable
data class ServiceListResponse(
    @SerialName("workspace_id") val workspaceId: Int,
    val services: List<Service>
)


@Serializable
data class Service(
    val id: Int,
    val name: String,
    val type: String
)


@Serializable
data class Booking(
    val id: Int,
    val date: String,
    val time: String? = null,
    val number_of_hours: Int? = null,
    @SerialName("workspace_id") val workspaceId: Int,
    @SerialName("package_id") val packageId: Int
)


@Serializable
data class CreateBookingResponse(
    val id: Int,
    @SerialName("workspace_name") val workspaceName: WorkspaceName,
    @SerialName("workspace_id") val workspaceId: String,
    @SerialName("package_name") val packageName: String,
    @SerialName("start_at") val startAt: String? = null,
    @SerialName("end_at") val endAt: String? = null,
    @SerialName("seat_number") val seatNumber: String? = null,
    @SerialName("wifi_username") val wifiUsername: String? = null,
    @SerialName("wifi_password") val wifiPassword: String? = null,
    @SerialName("remaining_time") val remainingTime: String? = null,
)

@Serializable
data class WorkspaceName(
    val ar: String,
    val en: String
)


@Serializable
data class NotificationResponse(
    val notifications: List<Notification>
)

@Serializable
data class Notification(
    val id: Int,
    val title: String,
    val body: String,
    val read: Boolean
)


@Serializable
data class MessageListResponse(
    val messages: List<Message>
)


@Serializable
data class Message(
    val id: Int,
    val body: String,
    val attachment: String? = null,
    @SerialName("sender_id") val senderId: Int
)

@Serializable
data class Conversation(
    val id: Int,
    @SerialName("secretary_id") val secretaryId: Int,
    val messages: List<Message>? = null
)


@Serializable
data class StaticContentResponse(
    val content: String // مثل terms أو about
)

@Serializable
data class Governorate(
    val id: Int,
    val name: String,
    val regions: List<Region> = emptyList(),
)


@Serializable
data class Region(
    val id: Int,
    val name: String,
)


@Serializable
data class About(
    val info: List<MapData> = emptyList(),
    val contacts: List<MapData>? = null,
    val links: List<MapData>? = null
)

@Serializable
data class MapData(val key: String, val description: String)