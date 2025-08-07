package ghayatech.ihubs.utils

import com.russhwolf.settings.Settings
import ghayatech.ihubs.networking.network.ApiService
import ghayatech.ihubs.networking.network.createPlatformHttpClient
import ghayatech.ihubs.networking.repository.ApiRepository
//import ghayatech.ihubs.networking.repository.ChatRepository
//import ghayatech.ihubs.networking.chat.ChatViewModel
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.ui.theme.LocalizationViewModel
import ghayatech.ihubs.ui.theme.ThemeViewModel
import org.koin.dsl.module

//expect fun platformWhatsAppHelper(): WhatsAppHelper


val appModule = module {
    // 1. UserPreferences: تعتمد على Settings التي سيتم توفيرها من الوحدة الخاصة بالمنصة.
    //    لا تُعرّف Settings هنا.
    single { UserPreferences(get()) } // Koin سيبحث عن Settings في Modules المحملة
//    single { LocalizationManager(get()) }
    // 2. HTTP Client وخدمات الـ API:
    //    تُفترض أن createPlatformHttpClient تحتاج إلى Settings.
    single {
        val settings: Settings = get() // Koin سيقدم Settings من الوحدة الخاصة بالمنصة
        createPlatformHttpClient(settings)
    }
    single { ApiService(get()) } // ApiService تعتمد على HTTP Client
    single { ApiRepository(get()) } // ApiRepository تعتمد على ApiService

    // 3. ViewModels:
    //    ThemeViewModel تعتمد على AppPreferences (التي تعتمد على Settings).
    single { ThemeViewModel(get()) }
    single { LocalizationViewModel(get()) }
    //    MainViewModel تعتمد على ApiRepository (ويمكن أن تعتمد على تبعيات أخرى).
    single { MainViewModel(get()) }

    single<Logger> { getLogger() }
}

//val appModule = module {
////    single<Settings> { Settings() }
////    single { UserPreferences(get()) }
////    single {
////        val settings: Settings = get()
////        createPlatformHttpClient(settings)
////    }
//    single { ApiService(get()) }
//    single { ApiRepository(get()) }
//    single { MainViewModel(get()/*, get()*/) }
//    single { ThemeViewModel(get()/*, get()*/) }
////    single<ChatSocketManager> { ChatSocketManagerImpl() }
//    single<Logger> { getLogger() }
//
//
//}