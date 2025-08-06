package ghayatech.ihubs

import androidx.compose.ui.window.ComposeUIViewController
import ghayatech.ihubs.utils.appModule
import ghayatech.ihubs.utils.iosModule
import org.koin.core.context.startKoin


fun MainViewController() = ComposeUIViewController {
    startKoin {
        modules(iosModule,appModule,)
    }
    AppMain()
}