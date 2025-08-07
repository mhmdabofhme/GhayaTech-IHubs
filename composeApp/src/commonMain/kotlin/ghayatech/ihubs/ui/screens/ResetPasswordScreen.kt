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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import ihubs.composeapp.generated.resources.back
import ihubs.composeapp.generated.resources.bold
import ihubs.composeapp.generated.resources.verification
import kotlinx.coroutines.delay
import ghayatech.ihubs.networking.models.VerifyPhoneRequest
import ghayatech.ihubs.networking.viewmodel.HandleUiState
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.ui.components.CButton
import ghayatech.ihubs.ui.components.CText
import ghayatech.ihubs.ui.components.CustomTopBar
import ghayatech.ihubs.ui.components.OtpCodeInput
//import ghayatech.ihubs.components.OtpInputField
import ghayatech.ihubs.ui.theme.AppColors
import ghayatech.ihubs.ui.theme.AppStringsProvider
import ghayatech.ihubs.utils.Constants
import ghayatech.ihubs.utils.UserPreferences
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.bold
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.rememberKoinInject


class ResetPasswordScreen(
    var code: String? = null,
    var phoneNumber: String? = null
) : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val userPreferences: UserPreferences = rememberKoinInject()
        val viewModel: MainViewModel = rememberKoinInject()
        val strings = AppStringsProvider.current()

        val openBottomSheet = remember { mutableStateOf(false) }
        BottomSheetScreen(
            openBottomSheet,
            title = strings.verification_success,
            description = strings.verification_success_message,
            buttonText = strings.start_experience,
        ) {

        }


        var number by remember { mutableStateOf(0) }
        val imgBack = painterResource(Res.drawable.back)
        var totalTimeMillis = 60000L
        var remainingTimeMillis by remember { mutableStateOf(totalTimeMillis) }

        var snackbarMessage by remember { mutableStateOf<String?>(null) }
//
//        val snackbarHostState = remember { SnackbarHostState() }
//        val coroutineScope = rememberCoroutineScope()
        val verifyPhoneState by viewModel.verifyPhoneState.collectAsState()
        val keyboardController = LocalSoftwareKeyboardController.current

        LaunchedEffect(remainingTimeMillis) {
            if (remainingTimeMillis > 0L) {
                delay(1000)
                remainingTimeMillis -= 1000
            } else {
                // TODO ON FINISH TIME VERIFICATION CODE

            }
        }
        val totalSeconds = remainingTimeMillis / 1000


        HandleUiState(
            state = verifyPhoneState,
            onMessage = { snackbarMessage = it },
            onSuccess = { data ->
                val token = data.token
                userPreferences.saveToken(token)
                userPreferences.saveUser( user = data.user)

                navigator.push(HubsScreen())
            }
        )


        Box(
            Modifier.fillMaxSize()
        ) {
            val errorMessage = strings.missing_fields

            snackbarMessage = code
            print("Code is: $code")

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppColors.White)
                    .padding(top = 60.dp, start = 22.dp, end = 22.dp)

                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CustomTopBar(title = strings.verification_code, onBackClick = {
                    navigator.pop()
                })

                Spacer(modifier = Modifier.size(22.dp))

                Image(painterResource(Res.drawable.verification), "Verification Code")

                CText(
                    strings.verification_message,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(10.dp)
                )


//        Column {
                Spacer(modifier = Modifier.size(30.dp))

                OtpCodeInput(4, onCodeComplete = {
                    if (it == code && !phoneNumber.isNullOrEmpty()) {
                        viewModel.verifyPhone(VerifyPhoneRequest(phoneNumber!!, it))
                        keyboardController?.hide()
                    } else {
                        snackbarMessage = errorMessage
                    }
                })
                Spacer(modifier = Modifier.size(20.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    CText(
                        strings.receive_code,
                        fontSize = 16.sp,
                        fontFamily = Res.font.bold,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    CText(
                        strings.resend_code,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        style = TextDecoration.Underline
                    )
                    CText(
                        "$totalSeconds ${strings.s}",
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                        color = AppColors.Error,
                        style = TextDecoration.Underline
                    )
                }
                Spacer(modifier = Modifier.size(20.dp))

                CButton(text = strings.check_now, onClick = {
                    openBottomSheet.value = true
                })

            }
        }

    }
}