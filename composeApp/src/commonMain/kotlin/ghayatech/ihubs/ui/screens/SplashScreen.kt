package ghayatech.ihubs.ui.screens


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import ghayatech.ihubs.networking.viewmodel.HandleUiState
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.ghayatech
import ihubs.composeapp.generated.resources.logo
import ihubs.composeapp.generated.resources.white_logo
import kotlinx.coroutines.delay
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.ui.components.CText
import ghayatech.ihubs.ui.components.UpdatePage
import ghayatech.ihubs.ui.theme.AppColors
import ghayatech.ihubs.ui.theme.AppColors.isDarkThemeBasedOnMode
import ghayatech.ihubs.ui.theme.AppStrings
import ghayatech.ihubs.ui.theme.AppStringsProvider
import ghayatech.ihubs.utils.Constants
import ghayatech.ihubs.utils.Logger
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.rememberKoinInject

class SplashScreen : Screen {
    @Composable
    override fun Content() {
        val settings: Settings = rememberKoinInject()
        val viewModel: MainViewModel = rememberKoinInject()
        val logger: Logger = rememberKoinInject()
        val strings = AppStringsProvider.current()

        val navigator = LocalNavigator.currentOrThrow
        val backgroundColor = AppColors.White

        var showUpdatePage by remember { mutableStateOf(false) }
        var startApp by remember { mutableStateOf(false) }
        var showLogo by remember { mutableStateOf(false) }
        var showDeveloperInfo by remember { mutableStateOf(false) }

        val versionState by viewModel.versionState.collectAsState()

        val alphaLogo = remember { Animatable(0f) }
        val alphaContent = remember { Animatable(0f) }

        // أول مرة نفتح الشاشة، نجيب الإصدار
        LaunchedEffect(Unit) {
            viewModel.getVersion()
        }


        // أنيميشن الشعار + النص
        LaunchedEffect(startApp) {
            if (!startApp) return@LaunchedEffect

//            delay(300)
            showLogo = true
            alphaLogo.animateTo(1f, tween(1500))

            delay(200)
            showDeveloperInfo = true
            alphaContent.animateTo(1f, tween(1000))

            logger.debug("TAG Splash screen", "${settings.getStringOrNull(Constants.TOKEN)}")

            // بعد ما يخلص الأنيميشن، نعمل التنقل
            delay(1000)
            navigateNext(navigator, settings)
        }

        // واجهة العرض
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            if (showLogo) {
                val logo: Painter = if (isDarkThemeBasedOnMode()) {
                    painterResource(Res.drawable.white_logo)
                } else {
                    painterResource(Res.drawable.logo)
                }
                Image(
                    painter = logo,
                    contentDescription = "Logo",
                    modifier = Modifier
                        .graphicsLayer { alpha = alphaLogo.value }
                        .size(150.dp)
                )
            }

            if (showDeveloperInfo) {
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 32.dp)
                        .graphicsLayer { alpha = alphaContent.value },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val color = if (isDarkThemeBasedOnMode()) AppColors.Black else AppColors.Primary
                    CText(text = strings.powered_by, color = color)
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(Res.drawable.ghayatech),
                        modifier = Modifier.size(56.dp),
                        contentDescription = "Developer Logo"
                    )
                }
            }

            if (showUpdatePage) {
                UpdatePage()
            }

            // معالجة نتيجة التحقق من الإصدار
            HandleUiState(
                versionState,
                onMessage = {
                    logger.debug("TAG Splash screen", it)
                },
                onSuccess = { data ->
                    showUpdatePage = !data.isLatest
                    startApp = data.isLatest
                },
                hasProgressBar = false
            )
        }
    }

    private fun navigateNext(navigator: Navigator, settings: Settings) {
        when {
            settings.getBooleanOrNull(Constants.IS_ONBOARDING) == null -> {
                navigator.push(OnBoardingScreen())
            }

            settings.getStringOrNull(Constants.TOKEN) != null -> {
                navigator.push(HubsScreen())
            }

            else -> {
                navigator.push(LoginScreen())
            }
        }
    }
}







