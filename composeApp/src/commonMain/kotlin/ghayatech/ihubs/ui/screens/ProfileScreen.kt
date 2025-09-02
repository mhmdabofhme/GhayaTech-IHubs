package ghayatech.ihubs.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoMode
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.SlideTransition
import com.russhwolf.settings.Settings
import ghayatech.ihubs.networking.models.About
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.bold
import ihubs.composeapp.generated.resources.camera
import ihubs.composeapp.generated.resources.camera_add
import ihubs.composeapp.generated.resources.correct
import ihubs.composeapp.generated.resources.edit
import ihubs.composeapp.generated.resources.file
import ihubs.composeapp.generated.resources.info
import ihubs.composeapp.generated.resources.logout
import ihubs.composeapp.generated.resources.major
import ihubs.composeapp.generated.resources.notification
import ihubs.composeapp.generated.resources.page
import ihubs.composeapp.generated.resources.phone
import ihubs.composeapp.generated.resources.profile
import ihubs.composeapp.generated.resources.theme
import ihubs.composeapp.generated.resources.user
import ghayatech.ihubs.networking.models.UpdateProfileRequest
import ghayatech.ihubs.networking.models.User
import ghayatech.ihubs.networking.viewmodel.HandleUiState
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.networking.viewmodel.UiState
import ghayatech.ihubs.ui.components.CText
import ghayatech.ihubs.ui.components.CTextField
import ghayatech.ihubs.ui.components.CustomSnackbar
import ghayatech.ihubs.ui.components.CustomTopBar
import ghayatech.ihubs.ui.components.LanguageSwitcher
import ghayatech.ihubs.ui.components.NetworkImage
import ghayatech.ihubs.ui.components.ThemeIconOption
import ghayatech.ihubs.ui.theme.AppColors
import ghayatech.ihubs.ui.theme.AppStringsProvider
import ghayatech.ihubs.ui.theme.AppThemeMode
import ghayatech.ihubs.ui.theme.LocalThemeViewModel
import ghayatech.ihubs.ui.theme.ThemeViewModel
import ghayatech.ihubs.utils.UserPreferences
import ihubs.composeapp.generated.resources.language
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.rememberKoinInject

class ProfileScreen() : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val settings: Settings = rememberKoinInject()
        val userPreferences: UserPreferences = rememberKoinInject()
        val viewModel: MainViewModel = rememberKoinInject()
        val themeViewModel: ThemeViewModel = rememberKoinInject()
        val strings = AppStringsProvider.current()

        val currentThemeMode by themeViewModel.currentThemeMode.collectAsState()


        var isEditing by rememberSaveable { mutableStateOf(false) }
        var username by rememberSaveable { mutableStateOf("") }
        var changedUsername by rememberSaveable { mutableStateOf("") }
        var mobileNumber by rememberSaveable { mutableStateOf("") }
        var changedMobileNumber by rememberSaveable { mutableStateOf("") }
        var major by rememberSaveable { mutableStateOf("") }
        var changedMajor by rememberSaveable { mutableStateOf("") }
        var isDarkMode by rememberSaveable { mutableStateOf(false) }
        var img = painterResource(Res.drawable.camera)
        var isRefreshing by remember { mutableStateOf(false) }


        var snackbarMessage by rememberSaveable { mutableStateOf<String?>(null) }
        val profileState by viewModel.profileState.collectAsState()
        var user by remember { mutableStateOf<User?>(null) }

//        val updateProfileState by viewModel.updateProfileState.collectAsState()


        LaunchedEffect(Unit) {
            viewModel.getProfile()
//            if (isEditing){
//                viewModel.updateProfileState
//            }
        }
//
//        LaunchedEffect(profileState) {
//
//                        // حفظ التغييرات
//                        username = changedUsername
//                        mobileNumber = changedMobileNumber
//                        major = changedMajor
//                        // هنا يمكنك إضافة الكود لحفظ البيانات في الخادم أو قاعدة البيانات
//
//        }
        LaunchedEffect(profileState) {
            if (profileState is UiState.Success || profileState is UiState.Error) {
                isRefreshing = false
            }
        }

        // --- UI Layout ---

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
                viewModel.getProfile()
            },
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = Modifier.fillMaxSize().background(AppColors.White)
                    .padding(top = 60.dp, start = 22.dp, end = 22.dp)
                    .verticalScroll(
                        rememberScrollState()
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CustomTopBar(
                    title = strings.profile,
                    onBackClick = { navigator.pop() },
                    endContent = {
                        if (isEditing) {
                            Row(
                                modifier = Modifier.clickable {
                                    if (username.trim().isNotEmpty() && major.trim()
                                            .isNotEmpty() && mobileNumber.trim().length > 9
                                    ) {
                                        viewModel.updateProfile(
                                            UpdateProfileRequest(
                                                name = username,
                                                phone = mobileNumber,
                                                specialty = major
                                            )
                                        )
                                        isEditing = !isEditing
                                    } else {
                                        snackbarMessage = strings.edit_error_message
                                    }
                                },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painterResource(Res.drawable.correct),
                                    contentDescription = "Done"
                                )
                                Spacer(modifier = Modifier.size(4.dp))
                                CText(
                                    text = strings.done,
                                    color = AppColors.Secondary
                                )
//                                img = painterResource(Res.drawable.camera_add)
                            }
                        } else {
                            Column(
                                modifier = Modifier.clickable { isEditing = !isEditing },
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painterResource(Res.drawable.edit),
                                    contentDescription = "Edit"
                                )
                                CText(
                                    text = strings.edit,
                                    color = AppColors.Secondary
                                )
//                                img = painterResource(Res.drawable.camera)

                            }
                        }
                    })

//                ProfileImageSection(
//                    imageUrl = null,
//                    isEditing = isEditing,
//                    onImageClick = {
//                        /* افتح المعرض أو الكاميرا */
//                    }
//                )

                Spacer(modifier = Modifier.size(20.dp))

                // الحقول القابلة للتعديل
                EditableField(
                    label = strings.fullname,
                    value = username,
                    isEditing = isEditing,
                    icon = painterResource(Res.drawable.user),
                    onValueChange = { username = it }
                )

                EditableField(
                    label = strings.mobile_number,
                    value = mobileNumber,
                    isEditing = isEditing,
                    onValueChange = { mobileNumber = it },
                    icon = painterResource(Res.drawable.phone),

                    keyboardType = KeyboardType.Phone
                )

                EditableField(
                    label = strings.major,
                    value = major,
                    isEditing = isEditing,
                    icon = painterResource(Res.drawable.major),

                    onValueChange = { major = it }

                )

//        // العناصر الثابتة
                ProfileItem(
                    label = strings.bookings,
                    icon = painterResource(Res.drawable.file)
                ) {
                    navigator.push(BookingsScreen())
                }

                ProfileItem(
                    label = strings.notifications,
                    icon = painterResource(Res.drawable.notification)
                ) {
                    navigator.push(NotificationsScreen())
                }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        Image(
                            painterResource(Res.drawable.theme),
                            contentDescription = null,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        ThemeSelectionRow(
                            currentThemeMode = currentThemeMode,
                            themeViewModel = themeViewModel
                        )
//                        CText(
//                            stringResource(Res.string.dark_theme),
//                            modifier = Modifier.weight(1f),
//                            fontFamily = Res.font.bold
//                        )
//                        Switch(checked = isDarkMode, onCheckedChange = { isDarkMode = it })
                    }
                    Box(
                        modifier = Modifier.height(2.dp).fillMaxWidth()
                            .background(AppColors.itemBackground)

                    )
                }

                LanguageItem()

                ProfileItem(
                    label = strings.about_us,
                    icon = painterResource(Res.drawable.info)
                ) {
                    navigator.push(AboutUsScreen())
//            onNavigate("about")
                }

                ProfileItem(
                    label = strings.privacy_policy,
                    icon = painterResource(Res.drawable.page)
                ) {
                    navigator.push(PrivacyScreen())
                }

                ProfileItem(
                    label = strings.logout,
                    icon = painterResource(Res.drawable.logout)
                ) {
                    settings.clear()
                    userPreferences.clearToken()
                    userPreferences.clear()
                    navigator.push(LoginScreen())
                }


            }

            CustomSnackbar(
                message = snackbarMessage,
                onDismiss = { snackbarMessage = null },
                modifier = Modifier.align(Alignment.TopCenter)
            )


            HandleUiState(
                state = profileState,
                onMessage =
                    {
                        snackbarMessage = it
                    },
                onSuccess =
                    { data ->
                        user = data
                        username = data.name
                        mobileNumber = data.phone
                        major = data.specialty.toString()
                    }
            )

        }
    }


    @Composable
    fun EditableField(
        label: String,
        value: String,
        isEditing: Boolean,
        onValueChange: (String) -> Unit,
        icon: Painter? = null,
        keyboardType: KeyboardType = KeyboardType.Text
    ) {
        Column {

            if (isEditing) {
                CTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )

            } else {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (icon != null) Image(
                        painter = icon,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    CText(value, fontFamily = Res.font.bold)
                }
            }
            Box(
                modifier = Modifier.height(2.dp).fillMaxWidth()
                    .background(AppColors.itemBackground)

            )
        }
    }

    @Composable
    fun ProfileItem(label: String, icon: Painter, onClick: () -> Unit) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick() }
                    .padding(vertical = 12.dp)
            ) {
                Image(icon, contentDescription = null, modifier = Modifier.size(22.dp))
                Spacer(modifier = Modifier.width(8.dp))
                CText(label, fontFamily = Res.font.bold)

            }
            Box(
                modifier = Modifier.height(2.dp).fillMaxWidth()
                    .background(AppColors.itemBackground)

            )
        }
    }

    @Composable
    fun ProfileImageSection(
        imageUrl: String?,
        isEditing: Boolean,
        onImageClick: () -> Unit
    ) {
        var img = painterResource(Res.drawable.camera)

        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .clickable { onImageClick() },
            contentAlignment = Alignment.Center
        ) {
            if (imageUrl != null) {
                // صورة المستخدم
//                val painterResource = asyncPainterResource(imageUrl)
                NetworkImage(
                    imageUrl, modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
//                NetworkImage(
//                    resource = painterResource,
//                    contentDescription = "صورة المستخدم",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .clip(CircleShape)
//                )
            } else {
                profileImage()
            }

            if (isEditing) {
                img = painterResource(Res.drawable.camera_add)
                profileImage()
            }
        }
    }

    @Composable
    fun profileImage() {
        val img = painterResource(Res.drawable.camera)

        Image(
            painter = img,
            contentDescription = "add photo",
            modifier = Modifier.size(100.dp)
                .border(width = 3.dp, color = AppColors.Primary, shape = CircleShape)
                .background(AppColors.coolBackground, shape = CircleShape)
                .border(width = 8.dp, color = AppColors.White, shape = CircleShape)
                .padding(30.dp),

            )
    }


    @Composable
    fun LanguageItem() {
        Column {
            val strings = AppStringsProvider.current()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Image(
                    painterResource(Res.drawable.language),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                CText(strings.language, fontFamily = Res.font.bold, modifier = Modifier.weight(1f))
                LanguageSwitcher()

            }
            Box(
                modifier = Modifier.height(2.dp).fillMaxWidth()
                    .background(AppColors.itemBackground)

            )
        }
    }

    @Composable
    fun ThemeSelectionRow(
        modifier: Modifier = Modifier,
        currentThemeMode: AppThemeMode,
        themeViewModel: ThemeViewModel // حقن ThemeViewModel
    ) {
//        val currentThemeMode by themeViewModel.currentThemeMode.collectAsState()
        val strings = AppStringsProvider.current()

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), // مسافة رأسية لتباعد الصفوف
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // لتوزيع العناصر على الأطراف
        ) {
            // النص على اليسار ويأخذ المساحة المتاحة
            CText(
                strings.dark_theme,
                modifier = Modifier.weight(1f),
                fontFamily = Res.font.bold
            )

            // حاوية الأيقونات على اليمين
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp) // مسافة بين الأيقونات
            ) {
//                // أيقونة الوضع النهاري
                ThemeIconOption(
                    icon = Icons.Default.LightMode,
                    contentDescription = "LightMode",
                    mode = AppThemeMode.LIGHT,
                    isSelected = currentThemeMode == AppThemeMode.LIGHT,
                    onModeSelected = { themeViewModel.setThemeMode(it) }
                )

                // أيقونة الوضع التلقائي (النظام)
                ThemeIconOption(
                    icon = Icons.Default.Sync, // أو Devices
                    contentDescription = "SettingsSuggest",
                    mode = AppThemeMode.SYSTEM,
                    isSelected = currentThemeMode == AppThemeMode.SYSTEM,
                    onModeSelected = { themeViewModel.setThemeMode(it) }
                )

                // أيقونة الوضع الليلي
                ThemeIconOption(
                    icon = Icons.Default.DarkMode,
                    contentDescription = "DarkMode",
                    mode = AppThemeMode.DARK,
                    isSelected = currentThemeMode == AppThemeMode.DARK,
                    onModeSelected = { themeViewModel.setThemeMode(it) }
                )
            }
        }
    }

}

@Preview
@Composable
fun PreviewProfileScreen() {
    Text("Hello from ProfileScreen Preview")
}