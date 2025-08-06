package ghayatech.ihubs

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import cafe.adriel.voyager.transitions.SlideTransition
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.ui.screens.SplashScreen
import ghayatech.ihubs.ui.theme.AppColors
import ghayatech.ihubs.ui.theme.AppThemeMode
import ghayatech.ihubs.ui.theme.LocalThemeViewModel
import ghayatech.ihubs.ui.theme.ThemeViewModel
import ghayatech.ihubs.utils.SetSystemUiColors
import org.koin.compose.koinInject

@Composable
fun AppMain() {
    val themeViewModel: ThemeViewModel = koinInject()
//    val currentThemeMode by themeViewModel.currentThemeMode.collectAsState()
//
//    val useDarkIcons = when(currentThemeMode) {
//        AppThemeMode.LIGHT -> true  // الوضع النهاري: الخلفية فاتحة -> نريد أيقونات داكنة
//        AppThemeMode.DARK -> false // الوضع الليلي: الخلفية داكنة -> نريد أيقونات فاتحة
//        AppThemeMode.SYSTEM -> !isSystemInDarkTheme() // الوضع الافتراضي: إذا كان النظام داكناً، نريد أيقونات فاتحة (لا داكنة)
//    }
//    SetSystemUiColors(useDarkIcons = useDarkIcons)

    CompositionLocalProvider(LocalThemeViewModel provides themeViewModel) {
        MaterialTheme {
            Surface(
                modifier = Modifier.fillMaxSize().background(AppColors.White)
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
//            val viewModel: MainViewModel = rememberKoinInject()
                Navigator(SplashScreen()) { navigator ->
                    SlideTransition(navigator)
                }
            }
        }
    }
}

//
//@HotPreview(widthDp = 411, heightDp = 891, density = 2.625f)
//@Composable
//fun PreviewScreen() {
//    CText(text = "Hello from Preview")
//}

class PlatformGreeting {
    fun greet(): String = "Hello from KMP"
}

