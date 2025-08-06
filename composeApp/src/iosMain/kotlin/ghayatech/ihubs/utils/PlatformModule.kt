package ghayatech.ihubs.utils

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults


val iosModule = module {
    single { WhatsAppHelper() }
    single<Settings> {
        NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults)
    }
}

//
//actual fun platformWhatsAppHelper(): WhatsAppHelper = WhatsAppHelper()