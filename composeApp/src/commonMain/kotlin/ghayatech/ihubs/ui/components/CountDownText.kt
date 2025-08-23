package ghayatech.ihubs.ui.components

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.bold
import kotlinx.coroutines.delay
import ghayatech.ihubs.ui.theme.AppColors
import org.jetbrains.compose.resources.Font

@Composable
fun CountdownText(
    totalTimeMillis: Long,
    onFinish: () -> Unit = {}
) {
    var remainingTimeMillis by remember { mutableStateOf(totalTimeMillis) }

    LaunchedEffect(remainingTimeMillis) {
        if (remainingTimeMillis > 0L) {
            delay(1000)
            remainingTimeMillis -= 1000
        } else {
            onFinish()
        }
    }

    val totalSeconds = remainingTimeMillis / 1000
    val days = totalSeconds / (24 * 3600)
    val hours = (totalSeconds % (24 * 3600)) / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    val formattedTime = "${days.toString().padStart(2, '0')} :" +
            "${hours.toString().padStart(2, '0')} :" +
            "${minutes.toString().padStart(2, '0')} :" +
            seconds.toString().padStart(2, '0')

    CText(
        text = formattedTime,
        color = AppColors.Primary,
        fontSize = 35.sp,
        fontFamily = Res.font.bold,
        textDirection = TextDirection.Ltr
    )
}
