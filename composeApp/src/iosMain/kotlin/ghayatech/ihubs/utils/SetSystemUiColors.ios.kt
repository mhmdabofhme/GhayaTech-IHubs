package ghayatech.ihubs.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import platform.UIKit.UIApplication
import platform.UIKit.setStatusBarStyle


@Composable
actual fun SetSystemUiColors(useDarkIcons: Boolean) {
    SideEffect {
        val window = UIApplication.sharedApplication.keyWindow ?: return@SideEffect
        val controller = window.rootViewController

        controller?.setNeedsStatusBarAppearanceUpdate()

        // ************ التعديل الرئيسي هنا: استخدام القيم العددية مباشرة ************
        // UIStatusBarStyleLightContent = 1 (أيقونات فاتحة/بيضاء، لخلفية داكنة)
        // UIStatusBarStyleDarkContent = 3 (أيقونات داكنة/سوداء، لخلفية فاتحة)

        UIApplication.sharedApplication.setStatusBarStyle(
            if (useDarkIcons) 3L // 3L تعني UIStatusBarStyleDarkContent (أيقونات داكنة)
            else 1L // 1L تعني UIStatusBarStyleLightContent (أيقونات فاتحة)
        )
        // ************************************************************************
    }
}