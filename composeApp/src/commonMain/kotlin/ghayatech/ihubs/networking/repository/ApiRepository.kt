package ghayatech.ihubs.networking.repository

import ghayatech.ihubs.networking.models.BaseResponse
import ghayatech.ihubs.networking.models.*
import ghayatech.ihubs.networking.network.ApiService
import ghayatech.ihubs.networking.util.NetworkError
import ghayatech.ihubs.networking.util.Result
import ghayatech.ihubs.networking.viewmodel.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ApiRepository(private val api: ApiService) {

    // Auth
    suspend fun login(data: LoginRequest): Result<BaseResponse<LoginResponse>, NetworkError> =
        api.login(data)

    suspend fun register(data: RegisterRequest): Result<BaseResponse<LoginResponse>, NetworkError> =
        api.register(data)

    suspend fun verifyPhone(data: VerifyPhoneRequest): Result<BaseResponse<VerificationResponse>, NetworkError> =
        api.verifyPhone(data)

    suspend fun logout(): Result<BaseResponse<Unit>, NetworkError> =
        api.logout()

    // Profile
    suspend fun getProfile(): Result<BaseResponse<User>, NetworkError> =
        api.getProfile()

    suspend fun updateProfile(data: UpdateProfileRequest): Result<BaseResponse<User>, NetworkError> =
        api.updateProfile(data)

    // Workspaces
    suspend fun getWorkspaces(): Result<ListBaseResponse<Workspace>, NetworkError> =
        api.getWorkspaces()

    suspend fun getWorkspaces(
        search: String? = null,
        governorate: String? = null,
        region: String? = null,
        hasBank: Boolean? = null,
        hasShift: Boolean? = null,
        hasFree: Boolean? = null,
    ): Result<ListBaseResponse<Workspace>, NetworkError> {
        return api.getWorkspaces(
            search = search,
            governorate = governorate,
            region = region,
            hasBank = hasBank,
            hasShift = hasShift,
            hasFree = hasFree,
        )
    }


//    suspend fun getFilteredWorkspaces(url: String): Result<ListBaseResponse<Workspace>, NetworkError> {
//        return api.getFilteredWorkspaces(url)
//    }


//    suspend fun searchWorkspaces(query:String): Result<ListBaseResponse<Workspace>, NetworkError> =
//        api.searchWorkspaces(query)
//
//    suspend fun filterLocationWorkspaces(query:String): Result<ListBaseResponse<Workspace>, NetworkError> =
//        api.filterLocationWorkspaces(query)
//
//    suspend fun filterBankWorkspaces(query:Boolean): Result<ListBaseResponse<Workspace>, NetworkError> =
//        api.filterBankWorkspaces(query)

    suspend fun getWorkspace(id: Int): Result<BaseResponse<WorkspaceDetails>, NetworkError> =
        api.getWorkspace(id)

    suspend fun getWorkspaceServices(id: Int): Result<BaseResponse<ServiceListResponse>, NetworkError> =
        api.getWorkspaceServices(id)

    suspend fun getWorkspacePackages(id: Int): Result<BaseResponse<PackagesResponse>, NetworkError> =
        api.getWorkspacePackages(id)

    // Bookings
    suspend fun createBooking(data: CreateBookingRequest): Result<BaseResponse<CreateBookingResponse>, NetworkError> =
        api.createBooking(data)

    suspend fun createBookingWithHours(data: CreateBookingWithHoursRequest): Result<BaseResponse<CreateBookingResponse>, NetworkError> =
        api.createBookingWithHours(data)

//    suspend fun getBookings(): Result<ListBaseResponse<CreateBookingResponse>, NetworkError> =
//        api.getBookings()


    suspend fun getBookings(
        query: String? = null,
    ): Result<ListBaseResponse<CreateBookingResponse>, NetworkError> {
        return api.getBookings(
            query = query
        )
    }

//    suspend fun getBookingsHistory(
//        query: String? = null,
//    ): Result<ListBaseResponse<CreateBookingResponse>, NetworkError> {
//        return api.getBookingsHistory(
//            query = query
//        )
//    }


    // Service Requests
    suspend fun createServiceRequest(
        bookingId: Int,
        data: CreateServiceRequest
    ): Result<BaseResponse<ServiceRequestResponse>, NetworkError> =
        api.createServiceRequest(bookingId, data)

    suspend fun getServiceRequests(bookingId: Int): Result<BaseResponse<ServiceListResponse>, NetworkError> =
        api.getServiceRequests(bookingId)

    // Notifications
    suspend fun getNotifications(): Result<ListBaseResponse<NotificationResponse>, NetworkError> =
        api.getNotifications()

    suspend fun markNotificationsAsRead(): Result<BaseResponse<Unit>, NetworkError> =
        api.markNotificationsAsRead()

    // Conversations & Messages
//    suspend fun startConversation(data: StartConversationRequest): Result<BaseResponse<Conversation>, NetworkError> =
//        api.startConversation(data)
//
//    suspend fun getConversations(): Result<ListBaseResponse<Conversation>, NetworkError> =
//        api.getConversations()
//
//    // Conversations & Messages
//    suspend fun sendMessage(conversationId: Int,data: SendMessage): Result<BaseResponse<Message>, NetworkError> =
//        api.sendMessage(conversationId,data)
//
//    suspend fun deleteConversation(id: Int): Result<BaseResponse<Unit>, NetworkError> =
//        api.deleteConversation(id)
//
//    suspend fun getMessages(conversationId: Int): Result<BaseResponse<MessageListResponse>, NetworkError> =
//        api.getMessages(conversationId)

    // Static Content
    suspend fun getTerms(): Result<BaseResponse<About>, NetworkError> =
        api.getTerms()

    suspend fun getAbout(): Result<BaseResponse<About>, NetworkError> =
        api.getAbout()


    suspend fun getGovernorate(): Result<ListBaseResponse<Governorate>, NetworkError> =
        api.getGovernorate()


    suspend fun getVersion(): Result<BaseResponse<VersionResponse>, NetworkError> =
        api.getVersion()



    suspend fun updateFcmToken(fcmToken: String): Result<BaseResponse<FcmTokenResponse>, NetworkError> =
        api.updateFcmToken(fcmToken)


}
