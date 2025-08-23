package ghayatech.ihubs.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
//import com.arkivanov.essenty.backhandler.BackHandler
import org.jetbrains.compose.resources.painterResource
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.*
import ghayatech.ihubs.networking.viewmodel.MainViewModel
//import ghayatech.ihubs.app_navigation.Config
//import ghayatech.ihubs.app_navigation.RootComponentImpl
import org.jetbrains.compose.resources.stringResource
import ghayatech.ihubs.networking.viewmodel.HandleUiState
import ghayatech.ihubs.networking.viewmodel.UiState
import ghayatech.ihubs.ui.components.CustomSnackbar
import ghayatech.ihubs.ui.theme.AppStringsProvider
import ghayatech.ihubs.utils.Constants
import ghayatech.ihubs.utils.ITokenManager
import ghayatech.ihubs.utils.Logger
import ghayatech.ihubs.utils.TokenManagerFactory
import ghayatech.ihubs.utils.UserPreferences
import ghayatech.ihubs.utils.isValid
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
//import ghayatech.ihubs.utils.UserPreferences.saveUser
import org.koin.compose.rememberKoinInject

class LoginScreen() : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: MainViewModel = rememberKoinInject()
        val userPreferences: UserPreferences = rememberKoinInject()
        val logger: Logger = rememberKoinInject()
        val strings = AppStringsProvider.current()
        val tokenManager: ITokenManager = TokenManagerFactory.createTokenManager()

        var phoneNumber by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }

        val imgWelcome =
            if (isSystemInDarkTheme()) painterResource(Res.drawable.welcome)
            else painterResource(Res.drawable.white_welcome)

        var snackbarMessage by remember { mutableStateOf<String?>(null) }

//        val snackbarHostState = remember { SnackbarHostState() }
//        val coroutineScope = rememberCoroutineScope()
        val loginState by viewModel.loginState.collectAsState()
        val fcmState by viewModel.updateFcmToken.collectAsState()
        val keyboardController = LocalSoftwareKeyboardController.current


        Box(modifier = Modifier.fillMaxSize()) {

            val errorMessage = strings.missing_fields
            val phoneMessage = strings.missing_phone

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppColors.White)
                    .padding(start = 22.dp, end = 22.dp, top = 60.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CText(
                    text = strings.login,
                    fontSize = 20.sp,
                    color = AppColors.Black,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.size(30.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(imgWelcome, contentDescription = "welcome")
                    CText(
                        text = strings.welcome,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                    CText(
                        text = strings.sign_in_to_access,
                        fontSize = 14.sp,
                    )
                }

                Spacer(modifier = Modifier.size(48.dp))

                CTextField(
                    placeholder = strings.mobile_number,
                    inputType = KeyboardType.Number,
                    value = phoneNumber,
                    imeAction = ImeAction.Next,
                    onValueChange = { phoneNumber = it }
                )

                Spacer(modifier = Modifier.height(12.dp))

                CTextField(
                    placeholder = strings.password,
                    inputType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                    isPassword = true,
                    value = password,
                    onValueChange = { password = it }
                )
                Spacer(modifier = Modifier.height(10.dp))
//                CText(
//                    text = strings.forget_your_password,
//                    style = TextDecoration.Underline,
//                    color = AppColors.Secondary,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 12.sp,
//                    modifier = Modifier.fillMaxWidth().padding(start = 9.dp).clickable {
//                        if (phoneNumber.isNotEmpty()) {
//                            navigator.push(
//                                ResetPasswordScreen(
//                                    phoneNumber = phoneNumber
//                                )
//                            )
//                        } else {
//                            snackbarMessage = phoneMessage
//                        }
//                    }
//                )
                Spacer(modifier = Modifier.height(10.dp))

                CButton(
                    text = strings.login,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = loginState !is UiState.Loading,
                    onClick = {
                        if (isValid(listOf(phoneNumber, password))) {
                            keyboardController?.hide()
                            viewModel.login(phoneNumber, password)
                        } else {
                            snackbarMessage = errorMessage
                        }
                    })

                Spacer(modifier = Modifier.height(10.dp))
                CButton(
                    text = strings.create_new_account,
                    isOutlined = true,
                    onClick = {
                        navigator.push(SignupScreen())
                    })
            }


            CustomSnackbar(
                message = snackbarMessage,
                onDismiss = { snackbarMessage = null },
                modifier = Modifier.align(Alignment.TopCenter)
            )

            HandleUiState(
                state = loginState,
                onMessage = {
                    snackbarMessage = it
//                    showErrorPage(it)
                },
                onSuccess = { data ->
                    logger.debug("Login Success:", data.toString())
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
