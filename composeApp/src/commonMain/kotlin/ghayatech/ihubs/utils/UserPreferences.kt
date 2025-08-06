package ghayatech.ihubs.utils

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import ghayatech.ihubs.networking.models.User
import ghayatech.ihubs.ui.theme.AppThemeMode
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserPreferences(val settings: Settings) {

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

}