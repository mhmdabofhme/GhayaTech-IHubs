package ghayatech.ihubs.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalContext
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import ghayatech.ihubs.Greeting
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
import ghayatech.ihubs.ui.components.CButton
import ghayatech.ihubs.ui.components.CIcon
import ghayatech.ihubs.ui.components.CText
import ghayatech.ihubs.ui.components.CountdownText
import ghayatech.ihubs.ui.components.CustomSnackbar
import ghayatech.ihubs.ui.components.CustomTopBar
import ghayatech.ihubs.ui.components.NetworkImage
import ghayatech.ihubs.ui.theme.AppColors
import ghayatech.ihubs.ui.theme.AppStringsProvider
import ghayatech.ihubs.utils.Logger
import ghayatech.ihubs.utils.SocialOpener
import ghayatech.ihubs.utils.UserPreferences
import ghayatech.ihubs.utils.WhatsAppHelper
import ghayatech.ihubs.utils.calculateRemainingMillis
import ghayatech.ihubs.utils.getPackageIcon

import ghayatech.ihubs.utils.splitDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.getKoin
import org.koin.compose.koinInject
import org.koin.compose.rememberKoinInject


class BookingDetailsScreen() : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val settings: Settings = rememberKoinInject()
        val viewModel: MainViewModel = rememberKoinInject()
        val whatsAppHelper: WhatsAppHelper = getKoin().get()
//        val socialMediaOpener: SocialMediaOpener = rememberKoinInject()
        val socialOpener: SocialOpener = koinInject()
        val logger: Logger = rememberKoinInject()
        val tag = "TAG BookingDetailsScreen:"
        val strings = AppStringsProvider.current()

        var listSize = 0
        val pagerState = rememberPagerState(pageCount = { listSize })
//        val pagerState = rememberPagerState()
        val listState = rememberLazyListState()
        val currentIndex = remember { mutableStateOf(0) }


        var snackbarMessage by rememberSaveable { mutableStateOf<String?>(null) }
        var bookingListSize by rememberSaveable { mutableIntStateOf(0) }

        val bookingState by viewModel.bookingState.collectAsState()
        val bookingsList = remember { mutableStateListOf<CreateBookingResponse>() }

        val workspaceState by viewModel.workspaceState.collectAsState()
        val workspace = remember { mutableStateOf<WorkspaceDetails?>(null) }


        LaunchedEffect(Unit) {
            logger.debug(tag, "getBookings")
            viewModel.getBookings()
        }

        // راقب تغيّر العنصر الظاهر
        LaunchedEffect(listState) {
            snapshotFlow { listState.firstVisibleItemIndex }
                .distinctUntilChanged()
                .collectLatest { index ->
                    currentIndex.value = index
                }
        }

        Box(Modifier.fillMaxSize()) {
//            val screenWidth = LocalConfiguration.current.screenWidthDp.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppColors.White)
                    .padding(top = 60.dp, start = 22.dp, end = 22.dp),
//                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                CustomTopBar(
                    showBackButton = true,
                    title = strings.booking_details,
                    endContent = {
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
                    },
                    onBackClick = {
                        navigator.pop()
                    })

//                Spacer(modifier = Modifier.size(20.dp))
//                HorizontalPager(
//                    state = pagerState,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(300.dp)
//                ) { page ->
//                    val item = bookingsList[page]

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(0.dp), // لا padding
                    state = listState,
//                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    logger.debug(tag, "in lazyRow: ${bookingsList.size}")

                    items(bookingsList) { item ->
//
//                        print("TAGTAG workspaceId" + item.workspaceId)


                        LaunchedEffect(item.workspaceId) {
                            viewModel.getWorkspace(item.workspaceId.toInt())
                        }


                        Column(
                            modifier = Modifier
//                                .width(340.dp)
                                .fillParentMaxWidth()
                                .wrapContentHeight()
                                .padding(top = 20.dp)
                                .padding(5.dp)
                                .shadow(
                                    shape = RoundedCornerShape(25.dp),
                                    elevation = 5.dp, // قوة الظل
                                    clip = true // اجعله false لتطبيق الظل خارج الشك
                                )
                                .background(
                                    AppColors.Background,
                                    shape = RoundedCornerShape(25.dp)
                                )
                                .padding(vertical = 16.dp, horizontal = 12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            NetworkImage(
                                workspace.value?.logo, modifier = Modifier
                                    .size(56.dp)
                                    .shadow(
                                        shape = RoundedCornerShape(15.dp),
                                        elevation = 2.dp,
                                        clip = true
                                    )
                            )

                            Spacer(modifier = Modifier.size(8.dp))
                            CText(
                                text = strings.ghayatech,
                                fontFamily = Res.font.bold
                            )
                            Spacer(modifier = Modifier.size(20.dp))

                            // Package
                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CText(
                                    text = "${strings.`package`} :",
                                    color = AppColors.Secondary,
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
                            Spacer(modifier = Modifier.size(12.dp))


                            // Seat
                            Row(Modifier.fillMaxWidth()) {
                                CText(
                                    text = "${strings.seat_number} :",
                                    color = AppColors.Secondary,
                                    fontFamily = Res.font.bold
                                )
                                Spacer(modifier = Modifier.size(12.dp))
                                CText(
                                    text = item.seatNumber ?: strings.seat_free,
                                    color = AppColors.Black,
                                    fontFamily =
                                        Res.font.bold,
                                )
                            }
                            Spacer(modifier = Modifier.size(12.dp))
                            logger.debug(tag, "Time ${item.startAt}")
                            val (date, time) = splitDateTime(item.startAt.toString())
//                            val date = "formatTimestamp(item.startAt.toString())"
//                            val time = "formatTimestamp(item.startAt.toString())"
                            // booking date
                            Row(Modifier.fillMaxWidth()) {
                                Image(
                                    painterResource(Res.drawable.date),
                                    contentDescription = "date",
                                )
                                Spacer(modifier = Modifier.size(4.dp))
                                CText(
                                    text = "${strings.books_date} :",
                                    color = AppColors.Secondary,
                                    fontFamily = Res.font.bold
                                )
                                Spacer(modifier = Modifier.size(4.dp))
                                CText(
                                    text = date,
                                    color = AppColors.Black,
                                    fontFamily = Res.font.bold, modifier = Modifier.weight(1F)
                                )
//                                Spacer(modifier = Modifier.size(4.dp))
//                                CText(
//                                    text = "${stringResource(Res.string.left)} ${item.remainingTime}",
//                                    color = AppColors.Error,
//                                    fontFamily = FontFamily(
//                                        Font(Res.font.bold)
//                                    ),
//                                )
                            }
                            Spacer(modifier = Modifier.size(12.dp))

                            // booking time
                            if (time != "00:00"){
                                Row(Modifier.fillMaxWidth()) {
                                    Image(
                                        painterResource(Res.drawable.time),
                                        contentDescription = "time",
                                    )
                                    Spacer(modifier = Modifier.size(4.dp))

                                    CText(
                                        text = "${strings.books_time} :",
                                        color = AppColors.Secondary,
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
                            Row(Modifier.fillMaxWidth()) {
                                Spacer(modifier = Modifier.size(2.dp))

                                Image(
                                    painterResource(Res.drawable.profile),
                                    contentDescription = "username",
                                )
                                Spacer(modifier = Modifier.size(8.dp))

                                CText(
                                    text = "${strings.username} :",
                                    color = AppColors.Secondary,
                                    fontFamily = Res.font.bold
                                )
                                Spacer(modifier = Modifier.size(12.dp))
                                CText(
                                    text = item.wifiUsername ?:strings.none,
                                    color = AppColors.Black,
                                    fontFamily = Res.font.bold,
                                )
                            }
                            Spacer(modifier = Modifier.size(12.dp))


                            //password
                            Row(Modifier.fillMaxWidth()) {
                                Image(
                                    painterResource(Res.drawable.password),
                                    contentDescription = "password",
                                )
                                Spacer(modifier = Modifier.size(4.dp))

                                CText(
                                    text = "${strings.password} :",
                                    color = AppColors.Secondary,
                                    fontFamily = Res.font.bold
                                )
                                Spacer(modifier = Modifier.size(12.dp))
                                CText(
                                    text = item.wifiPassword ?:strings.none,
                                    color = AppColors.Black,
                                    fontFamily = Res.font.bold,
                                )
                            }

                            Spacer(modifier = Modifier.size(26.dp))
                            CText(
                                text = "${strings.remaining_time} :",
                                color = AppColors.Secondary,
                                fontFamily = Res.font.bold
                            )

                            logger.debug("$tag endAt" , item.endAt.toString())
                            CountdownText(calculateRemainingMillis(item.endAt.toString()))

                            Spacer(modifier = Modifier.size(13.dp))


                            Row {
                                CButton(text = strings.contactus, onClick = {
                                    // TODO PASS CONVERSATION ID
                                    socialOpener.openWhatsApp(workspace.value?.phone.toString())
                                }, modifier = Modifier.weight(1F))
//                                if (workspace.value != null) {
                                Spacer(modifier = Modifier.size(10.dp))
                                CButton(
                                    text = strings.our_services,
                                    onClick = {
                                        navigator.push(
                                            OurServicesScreen(
                                                workspace.value!!.id,
                                                item.id
                                            )
                                        )
                                    },
                                    modifier = Modifier.weight(1F)
                                )

                            }

                        }

                    }
                }

                Spacer(Modifier.size(20.dp))


                // Indicators

                if (bookingsList.isNotEmpty()) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(bookingsList.size) { index ->
//                            val isSelected = pagerState.currentPage == index
                            val isSelected = index == currentIndex.value
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .width(if (isSelected) 20.dp else 8.dp)
                                    .height(8.dp)
                                    .background(
                                        color = if (isSelected) AppColors.Secondary else AppColors.TextSecondary.copy(
                                            alpha = 0.3f
                                        ),
                                        shape = if (isSelected) RoundedCornerShape(8.dp) else CircleShape
                                    )
                            )
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
                        bookingListSize = data.size
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