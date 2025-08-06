package ghayatech.ihubs.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import ghayatech.ihubs.ui.theme.AppColors

@Composable
fun CustomSnackbar(
    message: String?,
    modifier: Modifier = Modifier,
    visibleDuration: Long = 3000, // مدة الظهور بالملي ثانية
    onDismiss: () -> Unit
) {
    // التحكم في الظهور والاختفاء
    val visible = remember(message) { mutableStateOf(message != null) }

    LaunchedEffect(message) {
        if (message != null) {
            visible.value = true
            delay(visibleDuration)
            visible.value = false
            delay(300) // وقت الأنيميشن
            onDismiss()
        }
    }

    AnimatedVisibility(
        visible = visible.value,
        enter = slideInVertically(
            initialOffsetY = { -100 },
            animationSpec = tween(300)
        ) + fadeIn(),
        exit = slideOutVertically(
            targetOffsetY = { -100 },
            animationSpec = tween(300)
        ) + fadeOut(),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .zIndex(1f)
    ) {
        Box(
            modifier = Modifier
                .padding(top = 40.dp)
                .shadow(8.dp, RoundedCornerShape(12.dp))
//                .border(2.dp, AppColors.Secondary, RoundedCornerShape(12.dp))
                .background(AppColors.coolBackground, RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            CText(
                text = message ?: "",
                color = AppColors.Secondary,
//                fontFamily = Res.font.bold,
                fontSize = 14.sp,
            )
        }
    }
}