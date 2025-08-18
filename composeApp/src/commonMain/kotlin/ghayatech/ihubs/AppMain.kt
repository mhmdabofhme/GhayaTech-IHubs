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
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.intl.Locale
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import cafe.adriel.voyager.transitions.SlideTransition
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.ui.screens.SplashScreen
import ghayatech.ihubs.ui.theme.AppColors
import ghayatech.ihubs.ui.theme.AppThemeMode
import ghayatech.ihubs.ui.theme.LocalLocalizationViewModel
import ghayatech.ihubs.ui.theme.LocalThemeViewModel
import ghayatech.ihubs.ui.theme.LocalizationViewModel
import ghayatech.ihubs.ui.theme.ThemeViewModel
import ghayatech.ihubs.utils.UserPreferences
import ghayatech.ihubs.utils.getLayoutDirection
import ghayatech.ihubs.utils.getLocale
import org.koin.compose.koinInject



@Composable
fun AppMain() {
    val themeViewModel: ThemeViewModel = koinInject()
    val userPreferences: UserPreferences = koinInject()
    val localizationViewModel: LocalizationViewModel = koinInject()

    // 1. الاستماع إلى تغييرات اللغة
    // هذا يجعل `currentLanguage` متغيراً من نوع State، مما يضمن إعادة البناء عند تغيره.
    val currentLanguage by localizationViewModel.currentLanguage.collectAsState()

    // 2. تحديد اتجاه الواجهة بناءً على اللغة الحالية
    // هذا يجعل `layoutDirection` ديناميكياً ويحدث مع تغير اللغة.
    val layoutDirection = getLayoutDirection(currentLanguage)

    CompositionLocalProvider(
        LocalThemeViewModel provides themeViewModel,
        LocalLocalizationViewModel provides localizationViewModel,
        LocalLayoutDirection provides layoutDirection
    ) {
        MaterialTheme {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppColors.White)
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
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

