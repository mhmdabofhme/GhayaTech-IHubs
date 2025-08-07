package ghayatech.ihubs.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import ghayatech.ihubs.networking.models.Governorate
import ghayatech.ihubs.networking.models.Region
import ghayatech.ihubs.networking.models.Workspace
import ghayatech.ihubs.networking.viewmodel.HandleUiState
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.networking.viewmodel.UiState
import ghayatech.ihubs.ui.components.*
import ghayatech.ihubs.ui.theme.AppColors
import ghayatech.ihubs.ui.theme.AppStrings
import ghayatech.ihubs.ui.theme.AppStringsProvider
import ghayatech.ihubs.utils.Constants
import ghayatech.ihubs.utils.Logger
import ihubs.composeapp.generated.resources.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.rememberKoinInject

class HubsScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val settings: Settings = rememberKoinInject()
        val viewModel: MainViewModel = rememberKoinInject()
        val logger: Logger = rememberKoinInject()
        val strings = AppStringsProvider.current()
        // --- State Management: Group related variables together ---

        // UI State
        var isList by rememberSaveable { mutableStateOf(true) }
        val layoutIcon =
            if (isList) painterResource(Res.drawable.gridlayout) else painterResource(Res.drawable.list)
        var snackbarMessage by rememberSaveable { mutableStateOf<String?>(null) }

        // Data State
        val workspacesState by viewModel.workspacesState.collectAsState()
        val governoratesState by viewModel.governoratesState.collectAsState()
        val allWorkspaces = remember { mutableStateListOf<Workspace>() }
        var filteredWorkspaces by remember { mutableStateOf(emptyList<Workspace>()) }

        // Filter State
        var searchInput by rememberSaveable { mutableStateOf("") }
        var hasBank by rememberSaveable { mutableStateOf<Boolean?>(null) }
        var hasShift by rememberSaveable { mutableStateOf<Boolean?>(null) }
        var hasFree by rememberSaveable { mutableStateOf<Boolean?>(null) }

        val governoratesList = remember { mutableStateListOf<Governorate>() }
        var selectedGovernorate by remember { mutableStateOf<Governorate?>(null) }
        var selectedRegion by remember { mutableStateOf<Region?>(null) }
        val regionsList by remember(selectedGovernorate) {
            mutableStateOf(selectedGovernorate?.regions ?: emptyList())
        }

        var isRefreshing by remember { mutableStateOf(false) }


        // --- Side Effects & Logic ---

        fun applyFilters() {
            var tempFilteredList = allWorkspaces.toList()

            tempFilteredList = tempFilteredList.filter { workspace ->
                (hasShift != true || workspace.hasEventShift) &&
                        (hasFree != true || workspace.hasFree) &&
                        (hasBank != true || workspace.bank_payment_supported) &&
                        (searchInput.isEmpty() || workspace.name.contains(
                            searchInput,
                            ignoreCase = true
                        )) &&
                        (selectedGovernorate == null || workspace.governorate?.name == selectedGovernorate?.name) &&
                        (selectedRegion == null || workspace.region?.name == selectedRegion?.name)
            }

            filteredWorkspaces = tempFilteredList
        }

        // Fetch data once when the screen is first launched
        LaunchedEffect(Unit) {
            viewModel.getWorkspaces()
            viewModel.getGovernorates()
        }

        // Apply filters whenever a filter state changes
        LaunchedEffect(
            searchInput,
            hasBank,
            hasShift,
            hasFree,
            selectedGovernorate,
            selectedRegion
        ) {
            applyFilters()
        }

        // مراقبة حالة الـ ViewModel لإيقاف مؤشر التحديث
        LaunchedEffect(workspacesState) {
            if (workspacesState is UiState.Success || workspacesState is UiState.Error) {
                isRefreshing = false
            }
        }

        // --- UI Layout ---

        PullToRefreshBox(

            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
                viewModel.getWorkspaces()
            },
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .background(AppColors.White)
                    .fillMaxSize()
                    .padding(top = 60.dp, start = 14.dp, end = 14.dp, bottom = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Search bar and profile icon
                Row(
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SearchBar(
                        value = searchInput,
                        onValueChange = { searchInput = it },
                        modifier = Modifier.weight(1F).padding(horizontal = 10.dp),
                        trailingIcon = layoutIcon,
                        onTrailingIconClick = { isList = !isList }
                    )
                    Box(
                        Modifier
                            .shadow(10.dp, shape = CircleShape, clip = true)
                            .clickable { navigator.push(ProfileScreen()) }
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.profile),
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(AppColors.Background)
                                .padding(10.dp),
                            tint = AppColors.TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.size(10.dp))

                // Filters
                Row(horizontalArrangement = Arrangement.Center) {
                    Filter(
                        dataList = governoratesList.map { it.name },
                        selectedItem = selectedGovernorate?.name,
                        placeholder = strings.governorates,
                        onItemSelected = { selectedName ->
                            selectedGovernorate = governoratesList.find { it.name == selectedName }
                            selectedRegion = null // Reset region on governorate change
                        }
                    )
                    Filter(
                        dataList = regionsList.map { it.name },
                        selectedItem = selectedRegion?.name,
                        placeholder = strings.regions,
                        onItemSelected = { selectedName ->
                            selectedRegion = regionsList.find { it.name == selectedName }
                        }
                    )
                }

                Row {
                    CCheckBox(
                        modifier = Modifier.fillMaxWidth().weight(1F),
                        text = strings.payment_available,
                        checkbox = hasBank ?: false,
                        onCheckedChange = { hasBank = it }
                    )
                    CCheckBox(
                        modifier = Modifier.fillMaxWidth().weight(1F),
                        text = strings.evening_shift,
                        checkbox = hasShift ?: false,
                        onCheckedChange = { hasShift = it }
                    )
                    CCheckBox(
                        modifier = Modifier.fillMaxWidth().weight(1F),
                        text = strings.free,
                        checkbox = hasFree ?: false,
                        onCheckedChange = { hasFree = it }
                    )
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    if (filteredWorkspaces.isEmpty()) {
                        // TODO: Add your NoResult() composable here
//                        NoResult()
                    } else {
                        if (isList) {
                            ListContent(filteredWorkspaces) { item ->
                                navigator.push(HubDetailsScreen(item.id))
                            }
                        } else {
                            GridContent(filteredWorkspaces, onItemClicked = { item ->
                                navigator.push(HubDetailsScreen(item.id))
                            })
                        }
                    }
                    CButton(
                        text = strings.current_bookings,
                        modifier = Modifier.align(Alignment.BottomCenter),
                        onClick = {
                            navigator.push(BookingDetailsScreen())
                        }
                    )
                }
            }

            CustomSnackbar(
                message = snackbarMessage,
                onDismiss = { snackbarMessage = null },
                modifier = Modifier.align(Alignment.TopCenter)
            )

            // UI State Handling for Workspaces
            HandleUiState(
                state = workspacesState,
                onMessage = { snackbarMessage = it },
                onSuccess = { data ->
                    allWorkspaces.clear()
                    allWorkspaces.addAll(data)
                    filteredWorkspaces = allWorkspaces
                    // The LaunchedEffect will handle calling applyFilters()
                },
                hasProgressBar = true
            )

            // UI State Handling for Governorates
            HandleUiState(
                state = governoratesState,
                onMessage = { snackbarMessage = it },
                onSuccess = { data ->
                    governoratesList.clear()
                    governoratesList.addAll(data)
                },
                hasProgressBar = true
            )
        }
    }
}