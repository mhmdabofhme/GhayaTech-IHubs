package ghayatech.ihubs.utils

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import ghayatech.ihubs.networking.models.User
import ghayatech.ihubs.ui.theme.AppThemeMode
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserPreferences(val settings: Settings) {
    fun clear() {
        settings.clear()
    }

    fun saveToken(token: String) {
        settings[Constants.TOKEN] = token
    }

    fun getToken(): String? {
        return settings[Constants.TOKEN]
    }

    fun clearToken() {
        settings.remove(Constants.TOKEN)
    }

    fun saveUser(user: User?) {
        val userJson = Json.encodeToString(user)

        settings.putString(Constants.USER_DATA, userJson)
    }

    fun loadUser(): User? {
        val userJson = settings.getStringOrNull(Constants.USER_DATA)
        return userJson?.let {
            Json.decodeFromString<User>(it)
        }
    }

    fun saveThemeMode(mode: AppThemeMode) {
        settings[Constants.THEME_MODE] = mode.name
    }

    fun getThemeMode(): AppThemeMode {
        val modeString = settings[Constants.THEME_MODE, AppThemeMode.SYSTEM.name]
        return AppThemeMode.valueOf(modeString)
    }

    fun saveLanguage(language: String) {
        settings[Constants.LANGUAGE] = language
    }

    fun getLanguage(): String {
        return settings[Constants.LANGUAGE]?: "ar"
    }

    // اللغة الافتراضية
//    var currentLanguage: String
//        get() = settings.getString(Constants.LANGUAGE, "en")
//        set(value) {
//            settings.putString(Constants.LANGUAGE, value)
//        }
//
//    fun setLanguage(languageCode: String) {
//        currentLanguage = languageCode
//    }

}
