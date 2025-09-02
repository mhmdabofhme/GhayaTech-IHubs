package ghayatech.ihubs.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.bold
import ihubs.composeapp.generated.resources.date
import ihubs.composeapp.generated.resources.ghayatech
import ihubs.composeapp.generated.resources.password
import ihubs.composeapp.generated.resources.profile
import ihubs.composeapp.generated.resources.time
import ghayatech.ihubs.networking.models.CreateBookingResponse
import ghayatech.ihubs.networking.models.WorkspaceDetails
import ghayatech.ihubs.networking.viewmodel.HandleUiState
import ghayatech.ihubs.networking.viewmodel.MainViewModel
//import ghayatech.ihubs.app_navigation.Screen
import ghayatech.ihubs.ui.components.CText
import ghayatech.ihubs.ui.components.CustomSnackbar
import ghayatech.ihubs.ui.components.CustomTopBar
import ghayatech.ihubs.ui.components.NetworkImage
import ghayatech.ihubs.ui.theme.AppColors
import ghayatech.ihubs.ui.theme.AppStringsProvider
import ghayatech.ihubs.utils.Logger
import ghayatech.ihubs.utils.getPackageIcon
import ghayatech.ihubs.utils.splitDateTime
import ihubs.composeapp.generated.resources.diamond
import ihubs.composeapp.generated.resources.first
import ihubs.composeapp.generated.resources.second
import ihubs.composeapp.generated.resources.third
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.rememberKoinInject


class BookingsScreen() : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val settings: Settings = rememberKoinInject()
        val viewModel: MainViewModel = rememberKoinInject()
        val logger: Logger = rememberKoinInject()
        val tag = "TAG BookingsScreen:"
        val strings = AppStringsProvider.current()

        var snackbarMessage by rememberSaveable { mutableStateOf<String?>(null) }
        val bookingState by viewModel.bookingState.collectAsState()
        val bookingsList = remember { mutableStateListOf<CreateBookingResponse>() }


        val workspaceState by viewModel.workspaceState.collectAsState()
        val workspace = remember { mutableStateOf<WorkspaceDetails?>(null) }



        LaunchedEffect(Unit) {
            logger.debug(tag, "getBookings")
            viewModel.getBookings("history")
        }


        Box(Modifier.fillMaxSize()) {


            Column(
                modifier = Modifier.fillMaxSize()
                    .background(AppColors.White)
                    .padding(top = 60.dp, start = 16.dp, end = 16.dp)

            ) {
                CustomTopBar(title = strings.bookings, onBackClick = {
                    navigator.pop()
                })

                Spacer(modifier = Modifier.size(24.dp))


                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    logger.debug(tag, "in lazyColumn: ${bookingsList.size}")


                    items(bookingsList) { item ->


                        LaunchedEffect(item.workspaceId) {
                            viewModel.getWorkspace(item.workspaceId.toInt())
                        }



                        Column(
                            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                                .padding(vertical = 7.dp)

                                .shadow(
                                    elevation = 5.dp, // قوة الظل
                                    shape = RoundedCornerShape(25.dp),
                                    clip = false // اجعله false لتطبيق الظل خارج الشك
                                )
                                .background(
                                    color = AppColors.Background,
                                    shape = RoundedCornerShape(25.dp)
                                )
                                .padding(horizontal = 20.dp, vertical = 16.dp)
                        ) {

                            Row {
                                Column {
                                    NetworkImage(
                                        workspace.value?.logo, modifier = Modifier
                                            .size(56.dp)
                                            .shadow(
                                                5.dp,
                                                shape = RoundedCornerShape(15.dp),
                                                clip = true
                                            )
                                    )

//                                    Image(
//                                        painterResource(Res.drawable.ghayatech),
//                                        contentDescription = "hub image",
//                                        modifier = Modifier
//                                            .size(56.dp)
//                                            .background(
//                                                AppColors.White,
//                                                shape = RoundedCornerShape(15.dp)
//                                            )
//                                            .padding(10.dp),
//                                    )
                                    Spacer(modifier = Modifier.size(6.dp))
                                    CText(
                                        text = item.workspaceName.en,
                                        fontFamily = Res.font.bold,
                                        fontSize = 12.sp
                                    )
                                }
                                Spacer(modifier = Modifier.size(10.dp))
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Spacer(modifier = Modifier.size(10.dp))
                                    // Package
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        CText(
                                            text = "${strings.`package`} :",
                                            color = AppColors.Primary,
                                            fontFamily = Res.font.bold
                                        )
                                        Spacer(modifier = Modifier.size(12.dp))
                                        CText(
                                            text = item.packageName,
                                            color = AppColors.Black,
                                            fontFamily = Res.font.bold,
                                        )
                                        Spacer(modifier = Modifier.size(8.dp))
                                        Image(
                                            getPackageIcon(item.packageName),
                                            contentDescription = "package icon",
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
//                        Spacer(modifier = Modifier.size(12.dp))


                                    // Seat
                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        CText(
                                            text = "${strings.seat_number} :",
                                            color = AppColors.Primary,
                                            fontFamily = Res.font.bold
                                        )
                                        Spacer(modifier = Modifier.size(12.dp))
                                        CText(
                                            text = item.seatNumber
                                                ?: strings.seat_free,
                                            color = AppColors.Black,
                                            fontFamily = Res.font.bold,
                                        )
                                    }
                                }
                            }


                            Spacer(modifier = Modifier.size(12.dp))
                            val (date, time) = splitDateTime(item.startAt.toString())

                            // booking date
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Image(
                                    painterResource(Res.drawable.date),
                                    contentDescription = "date",
                                )
                                CText(
                                    text = "${strings.books_date} :",
                                    color = AppColors.Primary,
                                    fontFamily = Res.font.bold
                                )
                                Spacer(modifier = Modifier.size(4.dp))
                                CText(
                                    text = date,
                                    color = AppColors.Black,
                                    fontFamily = Res.font.bold, modifier = Modifier.weight(1F)
                                )

                            }
                            Spacer(modifier = Modifier.size(12.dp))


                            // booking time
                            if (time != "00:00") {

                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Image(
                                        painterResource(Res.drawable.time),
                                        contentDescription = "time",
                                    )
                                    CText(
                                        text = "${strings.books_time} :",
                                        color = AppColors.Primary,
                                        fontFamily = Res.font.bold
                                    )
                                    Spacer(modifier = Modifier.size(12.dp))
                                    CText(
                                        text = time,
                                        color = AppColors.Black,
                                        fontFamily = Res.font.bold,
                                    )
                                }
                                Spacer(modifier = Modifier.size(12.dp))

                            }

                            //username
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Spacer(modifier = Modifier.size(2.dp))

                                Image(
                                    painterResource(Res.drawable.profile),
                                    contentDescription = "username",
                                )
                                Spacer(modifier = Modifier.size(4.dp))

                                CText(
                                    text = "${strings.username} :",
                                    color = AppColors.Primary,
                                    fontFamily = Res.font.bold
                                )
                                Spacer(modifier = Modifier.size(12.dp))
                                CText(
                                    text = item.wifiUsername ?:strings.booking_not_confirmed,
                                    color = AppColors.Black,
                                    fontFamily = Res.font.bold,
                                )
                            }
                            Spacer(modifier = Modifier.size(12.dp))


                            //password
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Image(
                                    painterResource(Res.drawable.password),
                                    contentDescription = "password",
                                )

                                CText(
                                    text = "${strings.password} :",
                                    color = AppColors.Primary,
                                    fontFamily = Res.font.bold
                                )
                                Spacer(modifier = Modifier.size(12.dp))
                                CText(
                                    text = item.wifiPassword ?:strings.booking_not_confirmed,
                                    color = AppColors.Black,
                                    fontFamily = Res.font.bold,
                                )
                            }


                        }


                    }

                }

            }

            CustomSnackbar(
                message = snackbarMessage,
                onDismiss = { snackbarMessage = null },
                modifier = Modifier.align(Alignment.TopCenter)
            )

            HandleUiState(
                state = bookingState,
                onMessage =
                    {
                        snackbarMessage = it
                    },
                onSuccess =
                    { data ->
                        logger.debug(tag, "onSuccess: $data")

                        bookingsList.clear()
                        bookingsList.addAll(data)
                    }
            )


            HandleUiState(
                state = workspaceState,
                onMessage =
                    {
                        snackbarMessage = it
                    },
                onSuccess =
                    { data ->
                        workspace.value = data
                    }
            )

        }
    }

}