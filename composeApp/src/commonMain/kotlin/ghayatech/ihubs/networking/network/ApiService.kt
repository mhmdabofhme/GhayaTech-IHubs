package ghayatech.ihubs.networking.network

import ghayatech.ihubs.networking.models.About
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.request.*
import io.ktor.http.*
import ghayatech.ihubs.networking.models.BaseResponse
import ghayatech.ihubs.networking.models.Conversation
//import ghayatech.ihubs.networking.models.ConversationListResponse
import ghayatech.ihubs.networking.models.CreateBookingRequest
import ghayatech.ihubs.networking.models.CreateBookingResponse
import ghayatech.ihubs.networking.models.CreateBookingWithHoursRequest
import ghayatech.ihubs.networking.models.CreateServiceRequest
import ghayatech.ihubs.networking.models.Governorate
import ghayatech.ihubs.networking.models.ListBaseResponse
import ghayatech.ihubs.networking.models.LoginRequest
import ghayatech.ihubs.networking.models.LoginResponse
import ghayatech.ihubs.networking.models.Message
import ghayatech.ihubs.networking.models.MessageListResponse
import ghayatech.ihubs.networking.models.NotificationResponse
import ghayatech.ihubs.networking.models.PackagesResponse
import ghayatech.ihubs.networking.models.RegisterRequest
import ghayatech.ihubs.networking.models.RegisterResponse
import ghayatech.ihubs.networking.models.SendMessage
import ghayatech.ihubs.networking.models.Service
import ghayatech.ihubs.networking.models.ServiceListResponse
import ghayatech.ihubs.networking.models.StartConversationRequest
import ghayatech.ihubs.networking.models.StaticContentResponse
import ghayatech.ihubs.networking.models.UpdateProfileRequest
import ghayatech.ihubs.networking.models.User
import ghayatech.ihubs.networking.models.VerificationResponse
import ghayatech.ihubs.networking.models.VerifyPhoneRequest
import ghayatech.ihubs.networking.models.Workspace
import ghayatech.ihubs.networking.models.WorkspaceDetails
import ghayatech.ihubs.networking.util.NetworkError
import ghayatech.ihubs.networking.util.Result
import ghayatech.ihubs.networking.util.toNetworkError
import ghayatech.ihubs.utils.boolToIntString

class ApiService(private val client: HttpClient) {


    suspend fun login(data: LoginRequest): Result<BaseResponse<LoginResponse>, NetworkError> {
        return try {
            val response = client.post(ApiConstants.BASE_URL + ApiRoutes.LOGIN) {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body<BaseResponse<LoginResponse>>()
            Result.Success(response)
        } catch (e: Exception) {
            object : Logger {
                override fun log(message: String) {
                    println(e.message)
                }
            }
            Result.Error(e.toNetworkError())
        }
    }

//    suspend fun register(data: RegisterRequest): Result<BaseResponse<RegisterResponse>, NetworkError> {
//        return try {
//            val response = client.post(ApiConstants.BASE_URL + ApiRoutes.REGISTER) {
//                contentType(ContentType.Application.Json)
//                setBody(data)
//            }.body<BaseResponse<RegisterResponse>>()
//            Result.Success(response)
//        } catch (e: Exception) {
//            Result.Error(e.toNetworkError())
//        }
//    }

    // Auth
    suspend fun register(data: RegisterRequest): Result<BaseResponse<LoginResponse>, NetworkError> {
        return try {
            val response = client.post(ApiConstants.BASE_URL + ApiRoutes.REGISTER) {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body<BaseResponse<LoginResponse>>()
            Result.Success(response)
        } catch (e: Exception) {
            println(e.message)
            Result.Error(e.toNetworkError())
        }
    }

    suspend fun verifyPhone(data: VerifyPhoneRequest): Result<BaseResponse<VerificationResponse>, NetworkError> {
        return try {
            val response = client.post(ApiConstants.BASE_URL + ApiRoutes.VERIFY_PHONE) {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body<BaseResponse<VerificationResponse>>()
            Result.Success(response)
        } catch (e: Exception) {
            println(e.message)
            Result.Error(e.toNetworkError())
        }
    }

    suspend fun logout(): Result<BaseResponse<Unit>, NetworkError> {
        return try {
            val response = client.delete(ApiConstants.BASE_URL + ApiRoutes.LOGOUT)
                .body<BaseResponse<Unit>>()
            Result.Success(response)
        } catch (e: Exception) {
            println(e.message)
            Result.Error(e.toNetworkError())
        }
    }

    // Profile
    suspend fun getProfile(): Result<BaseResponse<User>, NetworkError> {
        return try {
            val response = client.get(ApiConstants.BASE_URL + ApiRoutes.GET_PROFILE)
                .body<BaseResponse<User>>()
            Result.Success(response)
        } catch (e: Exception) {
            println(e.message)
            Result.Error(e.toNetworkError())
        }
    }

    suspend fun updateProfile(data: UpdateProfileRequest): Result<BaseResponse<User>, NetworkError> {
        return try {
            val response = client.put(ApiConstants.BASE_URL + ApiRoutes.UPDATE_PROFILE) {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body<BaseResponse<User>>()
            Result.Success(response)
        } catch (e: Exception) {
            println(e.message)
            Result.Error(e.toNetworkError())
        }
    }

    // Workspaces
    suspend fun getWorkspaces(): Result<ListBaseResponse<Workspace>, NetworkError> {
        return try {
            val response = client.get(ApiConstants.BASE_URL + ApiRoutes.LIST_WORKSPACES)
                .body<ListBaseResponse<Workspace>>()
            Result.Success(response)
        } catch (e: Exception) {
            println(e.message)
            Result.Error(e.toNetworkError())
        }
    }

    suspend fun getWorkspaces(
        search: String? = null,
        governorate: String? = null,
        region: String? = null,
        hasBank: Boolean? = null,
        hasShift: Boolean? = null,
        hasFree: Boolean? = null,
    ): Result<ListBaseResponse<Workspace>, NetworkError> {
        return try {
            val response = client.get(ApiConstants.BASE_URL + ApiRoutes.LIST_WORKSPACES) {
                url {
                    search?.let { parameters.append("search", it) }
                    governorate?.let { parameters.append("governorate", it) }
                    region?.let { parameters.append("region", it) }
                    hasBank?.let { parameters.append("bank_payment_supported", boolToIntString(it)) }
                    hasShift?.let { parameters.append("has_evening_shift", boolToIntString(it)) }
                    hasFree?.let { parameters.append("has_free", boolToIntString(it)) }
//                    location?.let { parameters.append("location", it) }
//                    bankPaymentSupported?.let {
//                        parameters.append(
//                            "bank_payment_supported",
//                            it.toString()
//                        )
//                    }
                }
            }.body<ListBaseResponse<Workspace>>()

            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.toNetworkError())
        }
    }


    suspend fun getBookings(
        query: String? = null,
    ): Result<ListBaseResponse<CreateBookingResponse>, NetworkError> {
        return try {
            val endpoint = if (!query.isNullOrBlank()) {
                "${ApiConstants.BASE_URL}${ApiRoutes.GET_BOOKING}?=\"${query}\""
            } else {
                "${ApiConstants.BASE_URL}${ApiRoutes.GET_BOOKING}"
            }
            val response = client.get(endpoint)
                .body<ListBaseResponse<CreateBookingResponse>>()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.toNetworkError())
        }
    }


//    suspend fun getFilteredWorkspaces(fullUrl: String): Result<ListBaseResponse<Workspace>, NetworkError> {
//        return try {
//            val response = client.get(fullUrl).body<ListBaseResponse<Workspace>>()
//            Result.Success(response)
//        } catch (e: Exception) {
//            println(e.message)
//            Result.Error(e.toNetworkError())
//        }
//    }


//    suspend fun searchWorkspaces(query: String): Result<ListBaseResponse<Workspace>, NetworkError> {
//        return try {
//            val response = client.get(ApiConstants.BASE_URL + ApiRoutes.SEARCH_WORKSPACE(query))
//                .body<ListBaseResponse<Workspace>>()
//            Result.Success(response)
//        } catch (e: Exception) {
//            println(e.message)
//            Result.Error(e.toNetworkError())
//        }
//    }
//
//
//    suspend fun filterLocationWorkspaces(query: String): Result<ListBaseResponse<Workspace>, NetworkError> {
//        return try {
//            val response = client.get(ApiConstants.BASE_URL + ApiRoutes.SEARCH_WORKSPACE(query))
//                .body<ListBaseResponse<Workspace>>()
//            Result.Success(response)
//        } catch (e: Exception) {
//            println(e.message)
//            Result.Error(e.toNetworkError())
//        }
//    }
//
//    suspend fun filterBankWorkspaces(query: Boolean): Result<ListBaseResponse<Workspace>, NetworkError> {
//        return try {
//            val response = client.get(ApiConstants.BASE_URL + ApiRoutes.SEARCH_WORKSPACE(query))
//                .body<ListBaseResponse<Workspace>>()
//            Result.Success(response)
//        } catch (e: Exception) {
//            println(e.message)
//            Result.Error(e.toNetworkError())
//        }
//    }

    suspend fun getWorkspace(id: Int): Result<BaseResponse<WorkspaceDetails>, NetworkError> {
        return try {
            val response = client.get(ApiConstants.BASE_URL + ApiRoutes.WORKSPACE_DETAILS(id))
                .body<BaseResponse<WorkspaceDetails>>()
            Result.Success(response)
        } catch (e: Exception) {
            println(e.message)
            Result.Error(e.toNetworkError())
        }
    }

    suspend fun getWorkspaceServices(id: Int): Result<BaseResponse<ServiceListResponse>, NetworkError> {
        return try {
            val response = client.get(ApiConstants.BASE_URL + ApiRoutes.WORKSPACE_SERVICES(id))
                .body<BaseResponse<ServiceListResponse>>()
            Result.Success(response)
        } catch (e: Exception) {
            println(e.message)
            Result.Error(e.toNetworkError())
        }
    }

    suspend fun getWorkspacePackages(id: Int): Result<BaseResponse<PackagesResponse>, NetworkError> {
        return try {
            val response = client.get(ApiConstants.BASE_URL + ApiRoutes.WORKSPACE_PACKAGES(id))
                .body<BaseResponse<PackagesResponse>>()
            Result.Success(response)
        } catch (e: Exception) {
            println(e.message)
            Result.Error(e.toNetworkError())
        }
    }

    // Bookings
    suspend fun createBooking(data: CreateBookingRequest): Result<BaseResponse<CreateBookingResponse>, NetworkError> {
        return try {
            val response = client.post(ApiConstants.BASE_URL + ApiRoutes.CREATE_BOOKING) {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body<BaseResponse<CreateBookingResponse>>()
            Result.Success(response)
        } catch (e: Exception) {
            println(e.message)
            Result.Error(e.toNetworkError())
        }
    }

    suspend fun createBookingWithHours(data: CreateBookingWithHoursRequest): Result<BaseResponse<CreateBookingResponse>, NetworkError> {
        return try {
            val response = client.post(ApiConstants.BASE_URL + ApiRoutes.CREATE_BOOKING) {
                contentType(ContentType.Application.Json)
                setBody(data)
            }.body<BaseResponse<CreateBookingResponse>>()
            Result.Success(response)
        } catch (e: Exception) {
            println(e.message)
            Result.Error(e.toNetworkError())
        }
    }

//    suspend fun getBooking(): Result<ListBaseResponse<CreateBookingResponse>, NetworkError> {
//        return try {
//            val response = client.get(ApiConstants.BASE_URL + ApiRoutes.GET_BOOKING)
//                .body<ListBaseResponse<CreateBookingResponse>>()
//            Result.Success(response)
//        } catch (e: Exception) {
//            println(e.message)
//            Result.Error(e.toNetworkError())
//        }
//    }

    // Service Requests
    suspend fun createServiceRequest(
        bookingId: Int,
        data: CreateServiceRequest
    ): Result<BaseResponse<Service>, NetworkError> {
        return try {
            val response =
                client.post(ApiConstants.BASE_URL + ApiRoutes.CREATE_SERVICE_REQUEST(bookingId)) {
                    contentType(ContentType.Application.Json)
                    setBody(data)
                }.body<BaseResponse<Service>>()
            Result.Success(response)
        } catch (e: Exception) {
            println(e.message)
            Result.Error(e.toNetworkError())
        }
    }
    // Upload image
    suspend fun uploadImageRequest(
        bookingId: Int,
        data: CreateServiceRequest
    ): Result<BaseResponse<Service>, NetworkError> {
        return try {
            val response =
                client.post(ApiConstants.BASE_URL + ApiRoutes.CREATE_SERVICE_REQUEST(bookingId)) {
                    contentType(ContentType.Application.Json)
                    setBody(data)
                }.body<BaseResponse<Service>>()
            Result.Success(response)
        } catch (e: Exception) {
            println(e.message)
            Result.Error(e.toNetworkError())
        }
    }

    suspend fun getServiceRequests(id: Int): Result<BaseResponse<ServiceListResponse>, NetworkError> {
        return try {
            val response = client.get(ApiConstants.BASE_URL + ApiRoutes.GET_SERVICE_REQUESTS(id))
                .body<BaseResponse<ServiceListResponse>>()
            Result.Success(response)
        } catch (e: Exception) {
            println(e.message)
            Result.Error(e.toNetworkError())
        }
    }

    // Notifications
    suspend fun getNotifications(): Result<BaseResponse<NotificationResponse>, NetworkError> {
        return try {
            val response = client.get(ApiConstants.BASE_URL + ApiRoutes.GET_NOTIFICATIONS)
                .body<BaseResponse<NotificationResponse>>()
            Result.Success(response)
        } catch (e: Exception) {
            println(e.message)
            Result.Error(e.toNetworkError())
        }
    }

    suspend fun markNotificationsAsRead(): Result<BaseResponse<Unit>, NetworkError> {
        return try {
            val response = client.post(ApiConstants.BASE_URL + ApiRoutes.MARK_NOTIFICATIONS_AS_READ)
                .body<BaseResponse<Unit>>()
            Result.Success(response)
        } catch (e: Exception) {
            println(e.message)
            Result.Error(e.toNetworkError())
        }
    }

//    // Conversations & Messages
//    suspend fun startConversation(data: StartConversationRequest): Result<BaseResponse<Conversation>, NetworkError> {
//        return try {
//            val response = client.post(ApiConstants.BASE_URL + ApiRoutes.START_CONVERSATION) {
//                contentType(ContentType.Application.Json)
//                setBody(data)
//            }.body<BaseResponse<Conversation>>()
//            Result.Success(response)
//        } catch (e: Exception) {
//            println(e.message)
//            Result.Error(e.toNetworkError())
//        }
//    }
//
//    suspend fun getConversations(): Result<ListBaseResponse<Conversation>, NetworkError> {
//        return try {
//            val response = client.get(ApiConstants.BASE_URL + ApiRoutes.GET_CONVERSATIONS)
//                .body<ListBaseResponse<Conversation>>()
//            Result.Success(response)
//        } catch (e: Exception) {
//            println(e.message)
//            Result.Error(e.toNetworkError())
//        }
//    }

//     val response = client.get(ApiConstants.BASE_URL + ApiRoutes.WORKSPACE_SERVICES(id))
//                .body<BaseResponse<ServiceListResponse>>()

//    suspend fun sendMessage(
//        conversationId: Int,
//        data: SendMessage
//    ): Result<BaseResponse<Message>, NetworkError> {
//        return try {
//            val response = client.post(ApiConstants.BASE_URL + ApiRoutes.SEND_MESSAGE(conversationId)) {
//                contentType(ContentType.Application.Json)
//                setBody(data)
//            }.body<BaseResponse<Message>>()
//            Result.Success(response)
//        } catch (e: Exception) {
//            println(e.message)
//            Result.Error(e.toNetworkError())
//        }
//    }
//
//    suspend fun deleteConversation(id: Int): Result<BaseResponse<Unit>, NetworkError> {
//        return try {
//            val response = client.delete(ApiConstants.BASE_URL + ApiRoutes.DELETE_CONVERSATION(id))
//                .body<BaseResponse<Unit>>()
//            Result.Success(response)
//        } catch (e: Exception) {
//            println(e.message)
//            Result.Error(e.toNetworkError())
//        }
//    }

//    suspend fun getMessages(conversationId: Int): Result<BaseResponse<MessageListResponse>, NetworkError> {
//        return try {
//            val response =
//                client.get(ApiConstants.BASE_URL + ApiRoutes.GET_MESSAGES(conversationId))
//                    .body<BaseResponse<MessageListResponse>>()
//            Result.Success(response)
//        } catch (e: Exception) {
//            println(e.message)
//            Result.Error(e.toNetworkError())
//        }
//    }

    // Static Content
    suspend fun getTerms(): Result<BaseResponse<About>, NetworkError> {
        return try {
            val response = client.get(ApiConstants.BASE_URL + ApiRoutes.TERMS)
                .body<BaseResponse<About>>()
            Result.Success(response)
        } catch (e: Exception) {
            println(e.message)
            Result.Error(e.toNetworkError())
        }
    }

    suspend fun getAbout(): Result<BaseResponse<About>, NetworkError> {
        return try {
            val response = client.get(ApiConstants.BASE_URL + ApiRoutes.ABOUT)
                .body<BaseResponse<About>>()
            Result.Success(response)
        } catch (e: Exception) {
            println(e.message)
            Result.Error(e.toNetworkError())
        }
    }


    suspend fun getGovernorate(): Result<ListBaseResponse<Governorate>, NetworkError> {
        return try {
            val response = client.get(ApiConstants.BASE_URL + ApiRoutes.GET_GOVERNORATES)
                .body<ListBaseResponse<Governorate>>()
            Result.Success(response)
        } catch (e: Exception) {
            println(e.message)
            Result.Error(e.toNetworkError())
        }
    }

}