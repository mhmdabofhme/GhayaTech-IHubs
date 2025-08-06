package ghayatech.ihubs.ui.theme

import ghayatech.ihubs.utils.UserPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ThemeViewModel(private val appPreferences: UserPreferences) {

    private val _currentThemeMode = MutableStateFlow(AppThemeMode.SYSTEM)
    val currentThemeMode: StateFlow<AppThemeMode> = _currentThemeMode.asStateFlow()

    private val vmScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        // قم بتحميل وضع الثيم المحفوظ عند تهيئة ViewModel
        vmScope.launch {
            _currentThemeMode.value = appPreferences.getThemeMode()
        }
    }

    fun setThemeMode(mode: AppThemeMode) {
        vmScope.launch {
            appPreferences.saveThemeMode(mode)
            _currentThemeMode.value = mode
        }
    }
}