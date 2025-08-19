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
    val secretary: Secretary? = null,
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
data class ServiceRequestResponse(
    val id: Int,
    val details: String,
    val type: String,
    val status: String,
    @SerialName("created_at") val createdAt: String,
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
    @SerialName("workspace_id") val workspaceId: Int,
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


//@Serializable
//data class NotificationResponse(
//    val notifications: List<Notification>
//)

//@Serializable
//data class Notification(
//    val id: Int,
//    val title: String,
//    val body: String,
//    val read: Boolean
//)


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

//"data":{
//        "version": "v1",
//        "latest": true,
//        "supported": [
//            "v1"
//        ],
//        "deprecated": []
//       }

@Serializable
data class VersionResponse(
    val version: String,
    @SerialName("latest") val isLatest: Boolean,
    val supported: List<String>,
    val deprecated: List<String>
)

@Serializable
data class FcmTokenResponse(
    @SerialName("device_token") val deviceToken: String
)


//{
//    "id": "9ef3e75b-cbf1-43c4-aafe-74e136337fff",
//    "type": "App\\Notifications\\FirebaseNotification",
//    "data": {
//    "title": "Your booking has been confirmed!",
//    "body": "Your workspace booking ghayatech has been successfully confirmed. You can verify your username and password.",
//    "data": {
//        "booking_id": "51",
//        "status": "confirmed",
//        "workspace_name": "ghayatech"
//    }
//},
//    "read_at": null,
//    "created_at": "2025-08-18T09:29:36.000000Z"
//}

@Serializable
data class NotificationResponse(
    val id: String,
    val type: String,
    val data: NotificationData,
    @SerialName("read_at") val readAt: String? = null,
    @SerialName("created_at") val createdAt: String
)

@Serializable
data class NotificationData(
    val title: String,
    val body: String,
    val data: NotificationDetails
)

@Serializable
data class NotificationDetails(
    @SerialName("booking_id") val bookingId: String,
    val status: String,
    @SerialName("workspace_name") val workspaceName: String
)