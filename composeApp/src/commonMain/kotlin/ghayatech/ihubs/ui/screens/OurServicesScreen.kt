package ghayatech.ihubs.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import ghayatech.ihubs.networking.models.CreateServiceRequest
import ghayatech.ihubs.networking.models.Service
import ghayatech.ihubs.networking.models.ServiceListResponse
import ihubs.composeapp.generated.resources.Res
import ghayatech.ihubs.networking.models.Workspace
import ghayatech.ihubs.networking.viewmodel.HandleUiState
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.networking.viewmodel.UiState
import ghayatech.ihubs.ui.components.CustomSnackbar
import ghayatech.ihubs.ui.components.CustomTopBar
import ghayatech.ihubs.ui.components.DescriptionDialog
import ghayatech.ihubs.ui.components.GridContent
import ghayatech.ihubs.ui.components.SearchBar
import ghayatech.ihubs.ui.components.ServiceItem
import ghayatech.ihubs.ui.theme.AppColors
import ghayatech.ihubs.ui.theme.AppStringsProvider
import ghayatech.ihubs.utils.Logger
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.rememberKoinInject


class OurServicesScreen(val id: Int, val bookingId: Int) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val viewModel: MainViewModel = rememberKoinInject()
        val logger: Logger = rememberKoinInject()
        val tag = "TAG OurPackagesScreen:"
        val strings = AppStringsProvider.current()


        var snackbarMessage by remember { mutableStateOf<String?>(null) }
        var successMessage = strings.service_created_successfully
        var isRefreshing by remember { mutableStateOf(false) }


        var showDialog by rememberSaveable { mutableStateOf(false) }
        var searchInput by rememberSaveable { mutableStateOf("") }
        var serviceType by rememberSaveable { mutableStateOf("") }
        val servicesList = remember { mutableStateListOf<Service>() }
        val servicesState by viewModel.workspaceServicesState.collectAsState()
        val createServiceState by viewModel.createServiceState.collectAsState()



        LaunchedEffect(Unit) {
            viewModel.getWorkspaceServices(id)
        }

        LaunchedEffect(servicesState) {
            if (servicesState is UiState.Success || servicesState is UiState.Error) {
                isRefreshing = false
            }
        }

        // --- UI Layout ---

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
                viewModel.getWorkspaceServices(id)
            },
            modifier = Modifier.fillMaxSize()
        ) {


            Column(
                modifier = Modifier.background(AppColors.White).fillMaxSize()
                    .padding(start = 14.dp, end = 14.dp, top = 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CustomTopBar(title = strings.our_services, onBackClick = {
                    navigator.pop()
                })
                Spacer(modifier = Modifier.size(24.dp))

                // Search bar and profile icon
                Row(
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SearchBar(
                        placeholder = strings.search_on_services,
                        value = searchInput,
                        onValueChange = { searchInput = it },
                        modifier = Modifier.height(58.dp).padding(horizontal = 10.dp),
                        hasTrailing = false
                    )

                }

                Spacer(modifier = Modifier.size(14.dp))

                ServiceItem(list = servicesList) { service ->

                    logger.debug(tag, "Service clicked: ${service.name}")
                    logger.debug(tag, "Service clicked type: ${service.type}")
                    serviceType = service.type
                    showDialog = true

                }

            }

            CustomSnackbar(
                message = snackbarMessage,
                onDismiss = { snackbarMessage = null },
                modifier = Modifier.align(Alignment.TopCenter)

            )

            HandleUiState(
                state = servicesState,
                onMessage = {
                    snackbarMessage = it
                },
                onSuccess = { data ->
                    servicesList.clear()
                    servicesList.addAll(data.services)
                },
            )


            HandleUiState(
                state = createServiceState,
                onMessage = {
                    snackbarMessage = it
                },
                onSuccess = { data ->
                    snackbarMessage = successMessage
                },
            )


            if (showDialog) {
                DescriptionDialog(
                    onDismiss = { showDialog = false },
                    onItemClick = {
                        viewModel.createServiceRequest(
                            bookingId,
                            CreateServiceRequest(type = serviceType, it)
                        )
                        showDialog = false

                    }
                )
            }

        }

    }
}