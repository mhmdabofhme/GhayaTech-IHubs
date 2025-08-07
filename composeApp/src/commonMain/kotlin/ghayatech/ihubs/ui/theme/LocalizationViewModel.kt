package ghayatech.ihubs.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.ui.unit.LayoutDirection
import ghayatech.ihubs.utils.Constants
import ghayatech.ihubs.utils.UserPreferences


val LocalLocalizationViewModel = staticCompositionLocalOf<LocalizationViewModel> {
    error("No LocalizationViewModel provided")
}

class LocalizationViewModel(private val appPreferences: UserPreferences) {

    private val _currentLanguage = MutableStateFlow(
        appPreferences.getLanguage()
    )
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    fun setLanguage(languageCode: String) {
        appPreferences.saveLanguage(languageCode)
        _currentLanguage.value = languageCode
    }

    // هنا نقوم بتوفير AppStrings و LayoutDirection
    fun getAppStrings(): AppStrings {
        return stringsFor(_currentLanguage.value)
    }

    fun getLayoutDirection(): LayoutDirection {
        return getLayoutDirection(_currentLanguage.value)
    }
}