package ghayatech.ihubs.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.bold
import ihubs.composeapp.generated.resources.light
import ihubs.composeapp.generated.resources.on_boarding
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.ui.components.CButton
import ghayatech.ihubs.ui.components.CText
import ghayatech.ihubs.ui.theme.AppColors
import ghayatech.ihubs.ui.theme.AppStringsProvider
import ghayatech.ihubs.utils.Constants
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.rememberKoinInject


// HomeScreen.kt
class OnBoardingScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val settings: Settings = rememberKoinInject()
        val viewModel: MainViewModel = rememberKoinInject()
        val strings = AppStringsProvider.current()

        Column(
            modifier = Modifier.fillMaxSize().background(AppColors.White)
                .padding(top = 60.dp, start = 22.dp, end = 22.dp, bottom = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(40.dp))
            Image(
                painterResource(Res.drawable.on_boarding),
                contentDescription = "onBoarding",
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
            Spacer(modifier = Modifier.size(40.dp))
            Column(Modifier.weight(1F)) {

                CText(
                    text = strings.effective_workspace_management,
                    fontSize = 20.sp,
                    fontFamily = Res.font.bold
                )
                Spacer(modifier = Modifier.size(40.dp))
                CText(
                    text = strings.onboarding_description,
                    fontSize = 18.sp,
                    fontFamily = Res.font.light
                )
                Spacer(modifier = Modifier.size(40.dp))
                CButton(
                    text = strings.start_experience,
                    onClick = {
                        settings.putBoolean(Constants.IS_ONBOARDING, true)
                        if (settings.getStringOrNull(Constants.TOKEN) != null) {
                            navigator.push(HubsScreen())
                        } else {
                            navigator.push(LoginScreen())
                        }
                    }
                )


            }
        }
    }
}