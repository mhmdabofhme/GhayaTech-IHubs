package ghayatech.ihubs.utils



import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.ui.theme.ThemeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import kotlin.text.get

val androidModule = module {
    single<WhatsAppHelper> { WhatsAppHelper(androidContext()) }
    single<Settings> {
        SharedPreferencesSettings(androidContext().getSharedPreferences("app_settings", Context.MODE_PRIVATE))
    }
//    single<SocialMediaOpener> { SocialMediaOpenerAndroid(androidContext()) }
    single<SocialOpener> {
        // Android
        SocialOpenerAndroid(androidContext())
    }

}

//actual fun platformWhatsAppHelper(): WhatsAppHelper = get()


