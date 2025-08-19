package ghayatech.ihubs.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
//import androidx.compose.material3.icons.Icons
//import androidx.compose.material3.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import ghayatech.ihubs.ui.components.CButton
import ghayatech.ihubs.ui.components.CText
import ghayatech.ihubs.ui.components.CTextField
import ghayatech.ihubs.ui.theme.AppColors

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.painterResource
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.back
import ihubs.composeapp.generated.resources.major
import ihubs.composeapp.generated.resources.password
import ihubs.composeapp.generated.resources.welcome
import ihubs.composeapp.generated.resources.white_welcome
import ghayatech.ihubs.networking.models.RegisterRequest
import ghayatech.ihubs.networking.viewmodel.HandleUiState
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.ui.components.CustomSnackbar
import ghayatech.ihubs.utils.Constants
import ghayatech.ihubs.utils.isValid
//import ghayatech.ihubs.utils.saveUser
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.rememberKoinInject
import com.russhwolf.settings.Settings
import ghayatech.ihubs.ui.theme.AppStringsProvider
import ghayatech.ihubs.utils.ITokenManager
import ghayatech.ihubs.utils.Logger
import ghayatech.ihubs.utils.TokenManagerFactory
import ghayatech.ihubs.utils.UserPreferences

//import ghayatech.ihubs.app_navigation.Screen
//import ghayatech.ihubs.app_navigation.Config
//import ghayatech.ihubs.app_navigation.RootComponent

class SignupScreen() : Screen {
    @Composable
    override fun Content() {
        // إدارة التنقل (navigation) والبيانات (state management)
        val navigator = LocalNavigator.currentOrThrow
        val userPreferences: UserPreferences = rememberKoinInject()
        val viewModel: MainViewModel = rememberKoinInject()
        val logger: Logger = rememberKoinInject()

        // حالة الحقول (state variables)
        var fullName by remember { mutableStateOf("") }
        var phoneNumber by remember { mutableStateOf("") }
        var major by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var snackbarMessage by remember { mutableStateOf<String?>(null) }

        // حالة الواجهة (UI state)
        val registerState by viewModel.loginState.collectAsState()
        val keyboardController = LocalSoftwareKeyboardController.current
        val fcmState by viewModel.updateFcmToken.collectAsState()

        // المصادر (resources)
        val imgBack = painterResource(Res.drawable.back)
        val imgWelcome = if (isSystemInDarkTheme()) {
            painterResource(Res.drawable.welcome)
        } else {
            painterResource(Res.drawable.white_welcome)
        }
        val strings = AppStringsProvider.current()

        val tokenManager: ITokenManager = TokenManagerFactory.createTokenManager()
        val errorMessage = strings.missing_fields

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // المحتوى الرئيسي للواجهة
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppColors.White)
                    .padding(top = 60.dp, start = 22.dp, end = 22.dp)
                    // استخدام verticalScroll للسماح بالتمرير عند الحاجة
                    .verticalScroll(rememberScrollState())
                    // الحل: imePadding() لرفع المحتوى عند ظهور الكيبورد
                    .imePadding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // زر الرجوع (Back Button)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navigator.pop() }
                        .padding(bottom = 24.dp), // إضافة مسافة سفلية
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imgBack, contentDescription = "Back", tint = AppColors.TextSecondary)
                    CText(
                        strings.back,
                        fontSize = 14.sp,
                        color = AppColors.Black,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                // قسم الترحيب (Welcome Section)
                Image(imgWelcome, "welcome", modifier = Modifier.size(150.dp))
                CText(
                    strings.get_started,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
                CText(
                    strings.by_creating_a_free_account,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )

                // حقول الإدخال (Input Fields)
                Spacer(modifier = Modifier.height(48.dp))

                CTextField(
                    placeholder = strings.fullname,
                    inputType = KeyboardType.Text,
                    value = fullName,
                    onValueChange = { fullName = it }
                )
                Spacer(modifier = Modifier.height(12.dp))

                CTextField(
                    placeholder = strings.mobile_number,
                    inputType = KeyboardType.Number,
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it }
                )
                Spacer(modifier = Modifier.height(12.dp))

                CTextField(
                    placeholder = strings.major,
                    inputType = KeyboardType.Text,
                    value = major,
                    onValueChange = { major = it }
                )
                Spacer(modifier = Modifier.height(12.dp))

                CTextField(
                    placeholder = strings.password,
                    inputType = KeyboardType.Password,
                    isPassword = true,
                    value = password,
                    onValueChange = { password = it }
                )
                Spacer(modifier = Modifier.height(12.dp))

                CTextField(
                    placeholder = strings.confirm_password,
                    inputType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                    isPassword = true,
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it }
                )

                // زر إنشاء حساب (Create Account Button)
                Spacer(modifier = Modifier.height(35.dp))
                CButton(
                    text = strings.create_new_account,
                    onClick = {
                        if (isValid(listOf(phoneNumber, password, confirmPassword, major, fullName))) {
                            val data = RegisterRequest(
                                phone = phoneNumber,
                                password = password,
                                passwordConfirmation = confirmPassword,
                                specialty = major,
                                name = fullName
                            )
                            viewModel.register(data)
                            keyboardController?.hide()
                        } else {
                            snackbarMessage = errorMessage
                        }
                    }
                )

                // قسم تسجيل الدخول (Login Section)
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CText(
                        text = strings.already_have_account,
                        style = TextDecoration.Underline,
                        color = AppColors.Secondary,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .padding(start = 9.dp)
                            .clickable { navigator.pop() }
                    )
                }
            }

            // رسالة الإشعار (Snackbar)
            CustomSnackbar(
                message = snackbarMessage,
                onDismiss = { snackbarMessage = null },
                modifier = Modifier.align(Alignment.TopCenter)
            )

            // معالجة حالة الواجهة (Handle UI State)
            HandleUiState(
                state = registerState,
                onMessage = { snackbarMessage = it },
                onSuccess = { data ->
                    val token = data.token
                    userPreferences.saveToken(token)
                    userPreferences.saveUser(user = data.user)
                    navigator.push(HubsScreen())
                }
            )

            HandleUiState(
                state = registerState,
                onMessage = {
                    snackbarMessage = it
                },
                onSuccess = { data ->
                    logger.debug("Signup Success:", data.toString())
                    tokenManager.getFCMToken { fcmToken ->
                        if (fcmToken != null) {
                            val token = data.token
                            userPreferences.saveToken(token)
                            userPreferences.saveUser(data.user)
                            logger.debug("onSuccess FCM Token:", fcmToken)
                            viewModel.updateFcmToken(fcmToken)
                        }
                    }
                }
            )

            HandleUiState(
                fcmState,
                onMessage = {
                    logger.debug("FCM Token Update Error:", it)
                }, onSuccess = {
                    logger.debug("FCM Token Update Success:", "$it")
                    navigator.push(HubsScreen())
                }
            )
        }
    }
}