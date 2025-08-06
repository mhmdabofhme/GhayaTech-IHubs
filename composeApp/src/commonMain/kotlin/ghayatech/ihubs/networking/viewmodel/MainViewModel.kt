package ghayatech.ihubs.networking.viewmodel


import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ghayatech.ihubs.networking.models.BaseResponse
import ghayatech.ihubs.networking.models.Conversation
import ghayatech.ihubs.networking.models.CreateBookingRequest
import ghayatech.ihubs.networking.models.CreateBookingResponse
import ghayatech.ihubs.networking.models.CreateBookingWithHoursRequest
import ghayatech.ihubs.networking.models.CreateServiceRequest
import ghayatech.ihubs.networking.models.Governorate
import ghayatech.ihubs.networking.models.ListBaseResponse
import ghayatech.ihubs.networking.models.LoginRequest
import ghayatech.ihubs.networking.models.LoginResponse
import ghayatech.ihubs.networking.models.Message
import ghayatech.ihubs.networking.models.PackagesResponse
import ghayatech.ihubs.networking.models.RegisterRequest
import ghayatech.ihubs.networking.models.RegisterResponse
import ghayatech.ihubs.networking.models.SendMessage
import ghayatech.ihubs.networking.models.Service
import ghayatech.ihubs.networking.models.ServiceListResponse
import ghayatech.ihubs.networking.models.StartConversationRequest
import ghayatech.ihubs.networking.models.UpdateProfileRequest
import ghayatech.ihubs.networking.models.User
import ghayatech.ihubs.networking.models.VerificationResponse
import ghayatech.ihubs.networking.models.VerifyPhoneRequest
import ghayatech.ihubs.networking.models.Workspace
import ghayatech.ihubs.networking.models.WorkspaceDetails
import ghayatech.ihubs.networking.repository.ApiRepository
import ghayatech.ihubs.networking.util.NetworkError
import ghayatech.ihubs.networking.util.Result
import ghayatech.ihubs.utils.PushTokenProvider
import org.koin.compose.koinInject
import org.koin.compose.rememberKoinInject

// Syntax highlighting has been temporarily turned off in file MainViewMode.kt because of an internal error

class MainViewModel(
    private val repository: ApiRepository/*, private val socketManager: ChatSocketManager*/
){

    private val vmScope = CoroutineScope(Dispatchers.Main + SupervisorJob())


    // Auth
    private val _loginState = MutableStateFlow<UiState<BaseResponse<LoginResponse>>?>(null)
    val loginState: StateFlow<UiState<BaseResponse<LoginResponse>>?> = _loginState

//    private val _registerState = MutableStateFlow<UiState<BaseResponse<RegisterResponse>>?>(null)
//    val registerState: StateFlow<UiState<BaseResponse<LoginResponse>>?> = _registerState

    private val _verifyPhoneState =
        MutableStateFlow<UiState<BaseResponse<VerificationResponse>>?>(null)
    val verifyPhoneState: StateFlow<UiState<BaseResponse<VerificationResponse>>?> =
        _verifyPhoneState

    private val _logoutState = MutableStateFlow<UiState<BaseResponse<Unit>>?>(null)
    val logoutState: StateFlow<UiState<BaseResponse<Unit>>?> = _logoutState

    // Profile
    private val _profileState = MutableStateFlow<UiState<BaseResponse<User>>?>(null)
    val profileState: StateFlow<UiState<BaseResponse<User>>?> = _profileState

    private val _updateProfileState = MutableStateFlow<UiState<BaseResponse<User>>?>(null)
    val updateProfileState: StateFlow<UiState<BaseResponse<User>>?> = _updateProfileState

    // Workspaces
    private val _workspacesState =
        MutableStateFlow<UiState<ListBaseResponse<Workspace>>?>(null)
    val workspacesState: StateFlow<UiState<ListBaseResponse<Workspace>>?> = _workspacesState

    private var allWorkspaces: List<Workspace> = emptyList()
    private val _filteredHubsList = MutableStateFlow<List<Workspace>>(emptyList())
    val filteredHubsList: StateFlow<List<Workspace>> = _filteredHubsList

    // 3. حالة الفلاتر
    private val _searchQuery = MutableStateFlow("")
    private val _selectedGovernorate = MutableStateFlow("")
    private val _selectedRegion = MutableStateFlow("")
    private val _isBankPaymentEnabled = MutableStateFlow(false)

    private val _isShiftEnabled = MutableStateFlow(false)
    private val _isFreeEnabled = MutableStateFlow(false)

    //    private val _searchWorkspacesState =
//        MutableStateFlow<UiState<ListBaseResponse<Workspace>>?>(null)
//    val searchWorkspacesState: StateFlow<UiState<ListBaseResponse<Workspace>>?> = _workspacesState
//    val filterLocationWorkspacesState: StateFlow<UiState<ListBaseResponse<Workspace>>?> =
//        _workspacesState
//    val filterBankWorkspacesState: StateFlow<UiState<ListBaseResponse<Workspace>>?> =
//        _workspacesState

    private val _workspaceState = MutableStateFlow<UiState<BaseResponse<WorkspaceDetails>>?>(null)
    val workspaceState: StateFlow<UiState<BaseResponse<WorkspaceDetails>>?> = _workspaceState

    private val _workspaceServicesState =
        MutableStateFlow<UiState<BaseResponse<ServiceListResponse>>?>(null)
    val workspaceServicesState: StateFlow<UiState<BaseResponse<ServiceListResponse>>?> =
        _workspaceServicesState

    private val _workspacePackagesState =
        MutableStateFlow<UiState<BaseResponse<PackagesResponse>>?>(null)
    val workspacePackagesState: StateFlow<UiState<BaseResponse<PackagesResponse>>?> =
        _workspacePackagesState


    private val _createBookingState =
        MutableStateFlow<UiState<BaseResponse<CreateBookingResponse>>?>(null)
    val createBookingState: StateFlow<UiState<BaseResponse<CreateBookingResponse>>?> =
        _createBookingState


    private val _createServiceState =
        MutableStateFlow<UiState<BaseResponse<Service>>?>(null)

    val createServiceState: StateFlow<UiState<BaseResponse<Service>>?> =
        _createServiceState


    private val _bookingState =
        MutableStateFlow<UiState<ListBaseResponse<CreateBookingResponse>>?>(null)
    val bookingState: StateFlow<UiState<ListBaseResponse<CreateBookingResponse>>?> = _bookingState

    // Governorates and Regions
    private val _governoratesState =
        MutableStateFlow<UiState<ListBaseResponse<Governorate>>?>(null)
    val governoratesState: StateFlow<UiState<ListBaseResponse<Governorate>>?> = _governoratesState


//    private val pushTokenProvider: PushTokenProvider


    // Conversation
//    private val _conversationState = MutableStateFlow<UiState<BaseResponse<Conversation>>?>(null)
//    val conversationState: StateFlow<UiState<BaseResponse<Conversation>>?> = _conversationState


    // Messages
//    private val _conversationState = MutableStateFlow<UiState<BaseResponse<Conversation>>?>(null)
//    val conversationState: StateFlow<UiState<BaseResponse<Conversation>>?> = _conversationState


//    private val _messages = MutableStateFlow<List<Message>>(emptyList())
//    val messages = _messages.asStateFlow()

    // Message
//    private val _sendMessageState = MutableStateFlow<UiState<BaseResponse<Message>>?>(null)
//    val sendMessageState: StateFlow<UiState<BaseResponse<Message>>?> = _sendMessageState


    // Generalized executor
//    private fun <T> executeApiCall(
//        key: String,
//        call: suspend () -> Result<T, NetworkError>,
//        stateFlow: MutableStateFlow<UiState<T>?>
//    ) {
//        viewModelScope.launch {
//            stateFlow.value = UiState.Loading
//            when (val result = call()) {
//                is Result.Success -> stateFlow.value = UiState.Success(result.data)
//                is Result.Error -> stateFlow.value = UiState.Error(result.error)
//            }
//        }
//    }

//    private val apiJobs = mutableMapOf<String, Job>()

//        private fun <T> executeApiCall(
//        key: String,
//        call: suspend () -> Result<T, NetworkError>,
//        stateFlow: MutableStateFlow<UiState<T>?>
//    ) {
//        apiJobs[key]?.cancel() // إلغاء الطلب السابق بهذا المفتاح
//
//        val job = vmScope.launch {
//            stateFlow.value = UiState.Loading
//            when (val result = call()) {
//                is Result.Success -> stateFlow.value = UiState.Success(result.data)
//                is Result.Error -> stateFlow.value = UiState.Error(result.error)
//            }
//        }
//
//        apiJobs[key] = job
//    }


//    private fun <T> executeApiCall(
//        key: String,
//        call: suspend () -> Result<T, NetworkError>,
//        stateFlow: MutableStateFlow<UiState<T>?>
//    ) {
//        apiJobs[key]?.cancel()
//
//        // استخدم viewModelScope مباشرةً مع تحديد Dispatchers.IO للعمليات الشبكية
//        val job = viewModelScope.launch(Dispatchers.IO) {
//            // ... (باقي الكود الخاص بك)
//            try {
//                stateFlow.value = UiState.Loading
//                when (val result = call()) {
//                    is Result.Success -> {
//                        stateFlow.value = UiState.Success(result.data)
//                    }
//
//                    is Result.Error -> {
//                        stateFlow.value = UiState.Error(result.error)
//                    }
//                }
//            } catch (e: Exception) {
//                if (e is CancellationException) throw e
//                stateFlow.value = UiState.Error(NetworkError.Unknown)
//            }
//        }
//        apiJobs[key] = job
//    }

    private val apiJobs = mutableMapOf<String, Job>()

    private fun <T> executeApiCall(
        key: String,
        call: suspend () -> Result<T, NetworkError>,
        stateFlow: MutableStateFlow<UiState<T>?>
    ) {
        apiJobs[key]?.cancel() // إلغاء الطلب السابق بهذا المفتاح

        val job = vmScope.launch {
            stateFlow.value = UiState.Loading
            when (val result = call()) {
                is Result.Success -> stateFlow.value = UiState.Success(result.data)
                is Result.Error -> stateFlow.value = UiState.Error(result.error)
            }
        }

        apiJobs[key] = job
    }


    // Auth functions
    fun login(phone: String, password: String) {
        val data = LoginRequest(phone, password)
        executeApiCall("Login", {
            repository.login(data)
        }, _loginState)
    }

    fun register(data: RegisterRequest) {
        executeApiCall("Register", { repository.register(data) }, _loginState)
    }

    fun verifyPhone(data: VerifyPhoneRequest) {
        executeApiCall("verifyPhone", { repository.verifyPhone(data) }, _verifyPhoneState)
    }

    fun logout() {
        executeApiCall("logout", { repository.logout() }, _logoutState)
    }

    // Profile functions
    fun getProfile() {
        executeApiCall("getProfile", { repository.getProfile() }, _profileState)
    }

    fun updateProfile(data: UpdateProfileRequest) {
        executeApiCall("updateProfile", { repository.updateProfile(data) }, _updateProfileState)
    }

    // Workspaces functions
//    fun getWorkspaces() {
//        executeApiCall({ repository.getWorkspaces() }, _workspacesState)
//    }
    fun getWorkspaces() {
        executeApiCall("getWorkspaces", {
            repository.getWorkspaces()
        }, _workspacesState)
    }

//    fun searchWorkspaces(query:String) {
//        executeApiCall({ repository.searchWorkspaces(query) }, _workspacesState)
//    }
//
//    fun filterLocationWorkspaces(query:String) {
//        executeApiCall({ repository.filterLocationWorkspaces(query) }, _workspacesState)
//    }
//
//    fun filterBankWorkspaces(query:Boolean) {
//        executeApiCall({ repository.filterBankWorkspaces(query) }, _workspacesState)
//    }

    fun getWorkspace(id: Int) {
        executeApiCall("getWorkspace", { repository.getWorkspace(id) }, _workspaceState)
    }

    fun getWorkspaceServices(id: Int) {
        executeApiCall(
            "getWorkspaceServices",
            { repository.getWorkspaceServices(id) },
            _workspaceServicesState
        )
    }

    fun getWorkspacePackages(id: Int) {
        executeApiCall(
            "getWorkspacePackages",
            { repository.getWorkspacePackages(id) },
            _workspacePackagesState
        )
    }


    fun createBooking(data: CreateBookingRequest) {
        executeApiCall("createBooking", { repository.createBooking(data) }, _createBookingState)
    }

    fun createBookingWithHours(data: CreateBookingWithHoursRequest) {
        executeApiCall(
            "createBookingWithHours",
            { repository.createBookingWithHours(data) },
            _createBookingState
        )
    }

    fun createServiceRequest(id: Int, data: CreateServiceRequest) {
        executeApiCall(
            "createBookingWithHours",
            { repository.createServiceRequest(id,data) },
            _createServiceState
        )
    }

//    fun getBookings() {
//        executeApiCall({ repository.getBookings() }, _bookingState)
//    }


//    fun getBookings() {
//        executeApiCall({
//            repository.getBooking()
//        }, _bookingState)
//    }

//    private var activeConversationId: Int? = null


//    fun startOrGetConversation(secretaryId: Int) {
//        val request = StartConversationRequest(secretaryId)
//        executeApiCall(
//            { repository.startConversation(request) },
//            _conversationState
//        )
//        executeApiCall(
//            call = { repository.startConversation(request) },
//            onSuccess = { conversation ->
//                activeConversationId = conversation.id
//
//                socketManager.connect(conversation.id) { newMessage ->
//                    _messages.update { it + newMessage }
//                }
//
//                _conversationState.value = UiState.Success(conversation)
//            },
//            onError = {
//                _conversationState.value = UiState.Error(it)
//            },
//            onLoading = {
//                _conversationState.value = UiState.Loading
//            }
//        )
//    }

//    private val _messages = MutableStateFlow<List<Message>>(emptyList())
//    val messages = _messages.asStateFlow()
//
//    init {
//        vmScope.launch {
//            conversationState.collectLatest { state ->
//                if (state is UiState.Success) {
////                    val conversation = state.data
////                    activeConversationId = conversation.id
////
////                    socketManager.connect(conversation.id) { newMessage ->
////                        _messages.update { it + newMessage }
////                    }
//                }
//            }
//        }
//    }


//
//    private var activeConversationId: Int? = null
//
//    fun startConversation(receiverId: Int) {
//        executeApiCall({
//            repository.startConversation(StartConversationRequest(receiverId))
//        }, onSuccess = { conversation ->
////            activeConversationId = conversation.id
////            listenToMessages(conversation.id)
//        })
//    }


//    fun sendMessage(conversationId: Int, body: String, attachment: String? = null) {
//        val request = SendMessage(body = body, attachment = attachment)
//        executeApiCall(
//            { repository.sendMessage(conversationId, request) },
//            _sendMessageState
//        )
//    }


//    fun onCleared() {
//        vmScope.cancel()
//    }

//    fun clearLoginState() {
//        _loginState.value = null
//    }

//    fun getFilteredWorkspaces(
//        search: String? = null,
//        location: String? = null,
//        bankPayment: Boolean? = null
//    ) {
//        val baseUrl = ApiConstants.BASE_URL + ApiRoutes.LIST_WORKSPACES
//        val queryParams = mutableListOf<String>()
//
//        if (!search.isNullOrBlank()) queryParams += "search=$search"
//        if (!location.isNullOrBlank()) queryParams += "location=$location"
//        if (bankPayment != null) queryParams += "bank_payment_supported=$bankPayment"
//
//        val fullUrl = if (queryParams.isNotEmpty()) {
//            "$baseUrl?${queryParams.joinToString("&")}"
//        } else baseUrl
//
//        executeApiCall({
//            repository.getFilteredWorkspaces(fullUrl)
//        }, _workspacesState)
//    }


    fun getFilteredWorkspaces(
        search: String? = null,
        governorate: String? = null,
        region: String? = null,
        hasBank: Boolean? = null,
        hasShift: Boolean? = null,
        hasFree: Boolean? = null,
    ) {
        executeApiCall("getFilteredWorkspaces", {
            repository.getWorkspaces(search,governorate,region,hasBank,hasShift,hasFree)
        }, _workspacesState)
    }


    fun getBookings(
        query: String? = null,
    ) {
        executeApiCall("getBookings", {
            repository.getBookings(query)
        }, _bookingState)

    }

    fun getGovernorates() {
        executeApiCall("getGovernorates", {
            repository.getGovernorate()
        }, _governoratesState)
    }

//    fun disconnect() {
//        socketManager.disconnect()
//    }

    // 5. دالة تحديث الفلاتر
//    fun onSearchQueryChanged(query: String) {
//        _searchQuery.value = query
//        applyFilters()
//    }
//
//    fun onCitySelected(city: String) {
//        _selectedCity.value = city
//        applyFilters()
//    }
//
//    fun onBankPaymentToggled(isEnabled: Boolean) {
//        _isBankPaymentEnabled.value = isEnabled
//        applyFilters()
//    }
//
//    // 6. دالة تطبيق الفلاتر الرئيسية
//    private fun applyFilters() {
//        var tempFilteredList = _workspacesState
//
//        // فلتر البحث
//        if (_searchQuery.value.isNotEmpty()) {
//            tempFilteredList = tempFilteredList.filter {
//                it. .contains(_searchQuery.value, ignoreCase = true)
//            }
//        }
//
//        // فلتر المدينة
//        if (_selectedCity.value.isNotEmpty()) {
//            tempFilteredList = tempFilteredList.filter {
//                it.city == _selectedCity.value
//            }
//        }
//
//        // فلتر الدفع البنكي
//        if (_isBankPaymentEnabled.value) {
//            tempFilteredList = tempFilteredList.filter {
//                it.isBankPaymentAvailable
//            }
//        }
//
//        // تحديث قائمة البيانات المعروضة
//        _filteredHubsList.value = tempFilteredList
//    }

}