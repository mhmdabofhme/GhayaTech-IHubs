package ghayatech.ihubs.networking.network

object ApiRoutes {
    // Auth
    const val REGISTER = "/auth/register"
    const val VERIFY_PHONE = "/auth/phone/verify"
    const val LOGIN = "/auth/login"
    const val LOGOUT = "/auth/logout"

    // Profile
    const val GET_PROFILE = "/users/me"
    const val UPDATE_PROFILE = "/users/me"
    const val UPLOAD_PROFILE_IMAGE = "/users/me/avatar"

    // Workspaces
    const val LIST_WORKSPACES = "/workspaces"

    fun WORKSPACE_DETAILS(id: Int) = "/workspaces/$id"
//    fun SEARCH_WORKSPACE(query: String) = "/workspaces?search=$query"
//    fun LOCATION_WORKSPACE(query: String) = "/workspaces?location=$query"
//    fun BANK_WORKSPACE(query: Boolean) = "/workspaces?bank_payment_supported=$query"
    fun WORKSPACE_SERVICES(id: Int) = "/workspaces/$id/services"
    fun WORKSPACE_PACKAGES(id: Int) = "/workspaces/$id/packages"

    // Bookings
    const val CREATE_BOOKING = "/bookings"
    const val GET_BOOKING = "/bookings"
    fun BOOKING_DETAILS(id: Int) = "/bookings/$id"
    const val ACTIVE_BOOKINGS = "/bookings?=\"active\""
    const val BOOKING_HISTORY = "/bookings?=\"history\""

    // Service Requests
    fun CREATE_SERVICE_REQUEST(bookingId: Int) = "/bookings/$bookingId/service-requests"
    fun GET_SERVICE_REQUESTS(bookingId: Int) = "/bookings/$bookingId/service-requests"

    // Notifications
    const val GET_NOTIFICATIONS = "/notifications"
    const val MARK_NOTIFICATIONS_AS_READ = "/notifications/mark-as-read"

    // Messages
    fun SEND_MESSAGE(conversationId: Int) = "/conversations/$conversationId/messages"
    fun GET_MESSAGES(conversationId: Int) = "/conversations/$conversationId/messages"

    // Conversations
    const val START_CONVERSATION = "/conversations"
    const val GET_CONVERSATIONS = "/conversations"
    fun DELETE_CONVERSATION(id: Int) = "/conversations/$id"

    // Static content
    const val TERMS = "/static/terms"
    const val ABOUT = "/static/about"

    const val GET_GOVERNORATES = "/governorates"


}
