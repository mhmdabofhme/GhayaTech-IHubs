package ghayatech.ihubs.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import ghayatech.ihubs.ui.theme.AppColors
import ghayatech.ihubs.ui.theme.AppThemeMode


@Composable
fun ThemeIconOption(
    icon: ImageVector,
    contentDescription: String,
    mode: AppThemeMode,
    isSelected: Boolean,
    onModeSelected: (AppThemeMode) -> Unit
) {
    val selectedColor = AppColors.Primary // لون مميز عند الاختيار
    val unselectedColor = AppColors.TextSecondary // لون الأيقونة غير المختارة
    val backgroundColor = AppColors.coolBackground // لون خلفية الأيقونة المختارة

    Box(
        modifier = Modifier
            .size(32.dp) // حجم موحد للصندوق
            .clip(CircleShape) // شكل دائري للخلفية
            .background(if (isSelected) backgroundColor else AppColors.transparent)
            .clickable { onModeSelected(mode) },
        contentAlignment = Alignment.Center // لتوسيط الأيقونة داخل الصندوق
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = if (isSelected) selectedColor else unselectedColor,
            modifier = Modifier.size(18.dp)
        )
    }
}