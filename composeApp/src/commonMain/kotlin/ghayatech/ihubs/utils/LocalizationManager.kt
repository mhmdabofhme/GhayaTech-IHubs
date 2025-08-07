package ghayatech.ihubs.utils

import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.russhwolf.settings.get
import com.russhwolf.settings.set

class LocalizationManager(private val settings: Settings) {
    // استخدم الكود الموجود لديك
    fun saveLanguage(language: String) {
        settings[Constants.LANGUAGE] = language
    }

    fun getLanguage(): String {
        return settings[Constants.LANGUAGE]?: "ar"
    }

    private val _currentLanguage = MutableStateFlow(getLanguage())
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    fun setLanguage(languageCode: String) {
        saveLanguage(languageCode) // استخدام دالة الحفظ الخاصة بك
        _currentLanguage.value = languageCode
    }
}