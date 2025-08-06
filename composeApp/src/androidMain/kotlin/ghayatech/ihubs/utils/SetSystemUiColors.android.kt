package ghayatech.ihubs.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import ghayatech.ihubs.ui.theme.AppColors


@Composable
actual fun SetSystemUiColors(useDarkIcons: Boolean) {
    val view = LocalView.current

    // ************ الحل هنا: احصل على اللون من Composable context أولاً ************
    // بما أن AppColors.Background هي @Composable، يجب استدعاؤها هنا.
    val statusBarAndNavBarColor = AppColors.Background
    // *************************************************************************

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? android.app.Activity)?.window ?: return@SideEffect

            // لا تكرر WindowCompat.setDecorFitsSystemWindows(window, false) هنا،
            // فهو يُستدعى بواسطة enableEdgeToEdge() في MainActivity.

            val insetsController = WindowCompat.getInsetsController(window, view)

            // 3. ضبط لون شريط الحالة
            // الآن نستخدم القيمة العادية (غير الكومبوزابل) التي حصلنا عليها.
            window.statusBarColor = statusBarAndNavBarColor.toArgb()
            window.navigationBarColor = statusBarAndNavBarColor.toArgb()

            // 4. ضبط نمط الأيقونات (فاتحة أم داكنة)
            insetsController.isAppearanceLightStatusBars = !useDarkIcons
            insetsController.isAppearanceLightNavigationBars = !useDarkIcons
        }
    }
}

// دالة مساعدة لتحويل Compose Color إلى Android Color Int
// هذه الدالة صحيحة ولا تحتاج لتغيير.
private fun Color.toArgb(): Int {
    return android.graphics.Color.argb(
        (alpha * 255).toInt(),
        (red * 255).toInt(),
        (green * 255).toInt(),
        (blue * 255).toInt()
    )
}
