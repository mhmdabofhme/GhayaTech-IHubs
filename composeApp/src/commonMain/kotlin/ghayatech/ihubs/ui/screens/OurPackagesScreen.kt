package ghayatech.ihubs.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.bold
import ihubs.composeapp.generated.resources.diamond
import ihubs.composeapp.generated.resources.ils
import ghayatech.ihubs.networking.models.CreateBookingRequest
import ghayatech.ihubs.networking.models.CreateBookingResponse
import ghayatech.ihubs.networking.models.CreateBookingWithHoursRequest
import ghayatech.ihubs.networking.models.PackagesResponse
import ghayatech.ihubs.networking.models.Workspace
import ghayatech.ihubs.networking.models.WorkspaceDetails
import ghayatech.ihubs.networking.viewmodel.HandleUiState
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.ui.components.BookingDialog
import ghayatech.ihubs.ui.components.CText
import ghayatech.ihubs.ui.components.CustomTopBar
import ghayatech.ihubs.ui.components.NetworkImage
import ghayatech.ihubs.ui.theme.AppColors
import ghayatech.ihubs.ui.theme.AppStringsProvider
import ghayatech.ihubs.utils.Logger
import ihubs.composeapp.generated.resources.first
import ihubs.composeapp.generated.resources.second
import ihubs.composeapp.generated.resources.star
import ihubs.composeapp.generated.resources.third
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.rememberKoinInject


class OurPackagesScreen(private val id: Int) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val settings: Settings = rememberKoinInject()
        val viewModel: MainViewModel = rememberKoinInject()
        val logger: Logger = rememberKoinInject()
        val tag = "TAG OurPackagesScreen:"
        val strings = AppStringsProvider.current()

        var showDialog by remember { mutableStateOf(false) }

        val hasTime = remember { mutableStateOf(false) }
        val openBottomSheet = remember { mutableStateOf(false) }
        var selectedPackageId by remember { mutableIntStateOf(-1) }
        var snackbarMessage by remember { mutableStateOf<String?>(null) }
//        val paymentInfo = remember { mutableStateOf<PaymentInfo?>(null) }
//        val workspacePackage = remember { mutableStateOf<WorkspacePackage?>(null) }
        val packagesResponse = rememberSaveable { mutableStateOf<PackagesResponse?>(null) }
        val workspacePackagesState by viewModel.workspacePackagesState.collectAsState()

        val workspace = remember { mutableStateOf<WorkspaceDetails?>(null) }
        val workspaceState by viewModel.workspaceState.collectAsState()


        val booking = remember { mutableStateOf<CreateBookingResponse?>(null) }
        val bookingState by viewModel.createBookingState.collectAsState()

        val bookingNotAvailable = strings.booking_not_available


        LaunchedEffect(id) {
            viewModel.getWorkspacePackages(id)
            viewModel.getWorkspace(id)
        }

        BottomSheetScreen(
            openBottomSheet,
            title = strings.booking_success,
            description = strings.booking_info,
            buttonText = strings.done,
        ) {
            navigator.push(BookingDetailsScreen())
        }

        Box(Modifier.fillMaxSize()) {


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppColors.White)
                    .padding(start = 22.dp, end = 22.dp, top = 60.dp),
//                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {


                CustomTopBar(title = "Our packages", onBackClick = { navigator.pop() })


                Spacer(modifier = Modifier.size(40.dp))

                if (workspace.value != null) {

                    CText(text = workspace.value?.name ?: "", fontWeight = FontWeight.Bold)
                    NetworkImage(
                        url = workspace.value?.logo,
                        modifier = Modifier
                            .size(75.dp)
                            .background(
                                AppColors.coolBackground,
                                shape = RoundedCornerShape(15.dp)
                            ).clip(RoundedCornerShape(15.dp))
                    )

                }

                Spacer(modifier = Modifier.height(30.dp))

                val packages = packagesResponse.value?.packages.orEmpty()

                val packageThemeFromEnd: List<PackageTheme> = listOf(
                    PackageTheme(
                        painterResource(Res.drawable.diamond),
                        AppColors.monthly
                    ),    // للعنصر الأخير (Index from end = 0)
                    PackageTheme(
                        painterResource(Res.drawable.first),
                        AppColors.weekly
                    ),    // للعنصر قبل الأخير (Index from end = 1)
                    PackageTheme(
                        painterResource(Res.drawable.second),
                        AppColors.daily
                    ),  // للعنصر الثالث من النهاية (Index from end = 2)
                    PackageTheme(
                        painterResource(Res.drawable.third),
                        AppColors.hours
                    ),   // للعنصر الرابع من النهاية (Index from end = 3)
                )

                LazyColumn {
                    itemsIndexed(packages) { index, item ->


                        val reverseIndex = packages.size - 1 - index
                        // الحصول على اللون المناسب باستخدام الـ reverseIndex
                        // getOrNull() يضمن عدم حدوث كراش إذا كان الاندكس خارج النطاق (أي إذا كانت القائمة صغيرة)
                        val packageTheme =
                            packageThemeFromEnd.getOrNull(reverseIndex) ?: PackageTheme(
                                painterResource(Res.drawable.third),
                                AppColors.monthly
                            )


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(7.dp)
                                .height(100.dp)
                                .background(
                                    packageTheme.color,
                                    shape = RoundedCornerShape(15.dp)
                                )
                                .clickable {
                                    logger.debug(
                                        tag,
                                        " ${packagesResponse.value?.payment?.bankPaymentSupported}"
                                    )
//                                    print("TAG Booking: ${packagesResponse.value?.payment?.bankPaymentSupported}")

                                    if (packagesResponse.value?.payment?.bankPaymentSupported != null && packagesResponse.value?.payment?.bankPaymentSupported.equals(
                                            "true"
                                        )
                                    ) {
                                        logger.debug(tag, " hasTime ${hasTime.value}")
//                                        print("TAG Booking:hasTime ${hasTime.value}")

                                        hasTime.value = item.name == "hour"

                                        selectedPackageId = item.id
                                        showDialog = true
                                    } else {
                                        //TODO SHOW ERROR MESSAGE OR HANDLE IT
                                        snackbarMessage = bookingNotAvailable
                                    }
                                }
                                .padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                packageTheme.image,
                                contentDescription = item.name
                            )
                            CText(
                                item.name,
                                modifier = Modifier
                                    .weight(1F)
                                    .padding(10.dp),
                                color = AppColors.White,
                                fontSize = 20.sp,
//                        fontWeight = FontWeight.Bold,
                                fontFamily = Res.font.bold

                            )

                            CText(
                                item.price,
                                modifier = Modifier.padding(10.dp),
                                color = AppColors.White,
                                fontSize = 20.sp,
//                        fontWeight = FontWeight.Bold,
                                fontFamily = Res.font.bold
                            )
                            Image(
                                painterResource(Res.drawable.ils),
                                contentDescription = "currency"
                            )

                        }
                    }
                }

//                    for (item in packages) {

//                        val backgroundColor: Color = when (item.id) {
//                            packages[packages.size - 1].id -> AppColors.monthly
//                            packages[packages.size - 2].id -> AppColors.weekly
//                            packages[packages.size - 3].id -> AppColors.daily
//                            else -> AppColors.hours
//                        }


//                        Row(
//                            modifier = Modifier.fillMaxWidth().padding(7.dp).height(100.dp)
//                                .background(
//                                    backgroundColor,
//                                    shape = RoundedCornerShape(15.dp)
//                                )
//                                .clickable {
//                                    if (packagesResponse.value?.payment?.bankPaymentSupported != null && packagesResponse.value?.payment?.bankPaymentSupported.equals(
//                                            "1"
//                                        )
//                                    ) {
//                                        selectedPackageId = item.id
//                                        showDialog = true
//                                    } else {
//                                        //TODO SHOW ERROR MESSAGE OR HANDLE IT
//                                        snackbarMessage = bookingNotAvailable
//                                    }
//                                }.padding(20.dp),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Image(
//                                painterResource(Res.drawable.diamond),
//                                contentDescription = item.name
//                            )
//                            CText(
//                                item.name,
//                                modifier = Modifier.weight(1F).padding(10.dp),
//                                color = AppColors.White,
//                                fontSize = 20.sp,
////                        fontWeight = FontWeight.Bold,
//                                fontFamily = Res.font.bold
//
//                            )
//
//                            CText(
//                                item.price,
//                                modifier = Modifier.padding(10.dp),
//                                color = AppColors.White,
//                                fontSize = 20.sp,
////                        fontWeight = FontWeight.Bold,
//                                fontFamily = Res.font.bold
//                            )
//                            Image(
//                                painterResource(Res.drawable.ils),
//                                contentDescription = "currency"
//                            )
//
//                        }
//                    }
//                }
//                else {
//                    CText(
//                        text = stringResource(Res.string.no_data),
//                        color = AppColors.TextSecondary,
//                    )
//                }


            }


            if (showDialog) {
                logger.debug(tag, " ShowDialog")
                logger.debug(tag, " ${packagesResponse.value}")

                if (packagesResponse.value != null) {

                    BookingDialog(
                        hasTime = hasTime.value,
                        paymentInfo = packagesResponse.value!!.payment,
                        onDismiss = { showDialog = false },
                        onBookClick =
                            {
                                logger.debug(tag, " Booking hasTime${hasTime.value}")
                                logger.debug(tag, " Booking id $id")
                                logger.debug(tag, " Booking date ${it.date}")


                                if (hasTime.value) {
                                    logger.debug(tag, " Booking true date ${it.date}")
                                    logger.debug(tag, " Booking true time ${it.time}")
                                    logger.debug(
                                        tag,
                                        " Booking true numberOfHours ${it.numberOfHours}"
                                    )
//
//                                    print("TAG Booking:true in")
//                                    print("TAG Booking:true ${it.date}")
//                                    print("TAG Booking:true ${it.time}")
//                                    print("TAG Booking:true ${it.numberOfHours}")

                                    viewModel.createBookingWithHours(
                                        CreateBookingWithHoursRequest(
                                            workspaceId = id,
                                            packageId = selectedPackageId,
                                            date = it.date,
                                            time = it.time.toString(),
                                            numberOfHours = it.numberOfHours ?: 0
                                        )
                                    )

                                } else {
                                    logger.debug(tag, " Booking false ${it.date}")

                                    print("TAG Booking:false in")
                                    print("TAG Booking:false ${it.date}")

                                    viewModel.createBooking(
                                        CreateBookingRequest(
                                            workspaceId = id,
                                            packageId = selectedPackageId,
                                            date = it.date
                                        )
                                    )
                                }
                                openBottomSheet.value = true
                                showDialog = false
                            },
                    )

                }
            }


            HandleUiState(
                state = workspacePackagesState,
                onMessage =
                    {
                        snackbarMessage = it
                    },
                onSuccess =
                    { data ->
                        packagesResponse.value = data
                        logger.debug(tag, " Packages response ${data}")

//                        println("TAGTAG Packages: $data")
                    }
            )


            HandleUiState(
                state = workspaceState,
                onMessage = {
                    snackbarMessage = it
                },
                onSuccess = { data ->
                    workspace.value = data
                }
            )


            HandleUiState(
                state = bookingState,
                onMessage = {
                    snackbarMessage = it
                },
                onSuccess = { data ->
                    booking.value = data
                    navigator.push(HubsScreen())
                    logger.debug(tag, " Booking onSuccess ${data.id}")
                    logger.debug(tag, " Booking onSuccess ${data.packageName}")
                    logger.debug(tag, " Booking onSuccess ${data.workspaceName}")

//                    print("TAG Booking onSuccess: ${data.id}")
//                    print("TAG Booking onSuccess: ${data.packageName}")
//                    print("TAG Booking onSuccess: ${data.wifiPassword}")
                }
            )


        }

    }
}

data class PackageTheme(val image: Painter, val color: Color)
