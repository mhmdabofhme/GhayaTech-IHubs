package ghayatech.ihubs.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ghayatech.ihubs.ui.theme.AppColors
import ghayatech.ihubs.ui.theme.LocalizationViewModel
import org.koin.compose.koinInject

@Composable
fun LanguageSwitcher() {
    val localizationViewModel = koinInject<LocalizationViewModel>()

    val currentLanguage by localizationViewModel.currentLanguage.collectAsState()
    val isArabicSelected = currentLanguage == "ar"


    Row(
        modifier = Modifier
            .width(120.dp)
            .height(36.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(AppColors.coolBackground)
    ) {
        // زر اللغة العربية
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
                .background(if (isArabicSelected) AppColors.Secondary else AppColors.transparent)
                .clickable { localizationViewModel.setLanguage("ar")},
            contentAlignment = Alignment.Center
        ) {
            CText(
                text = "العربية",
                color = if (isArabicSelected) AppColors.White else AppColors.Secondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // زر اللغة الإنجليزية
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
                .background(if (!isArabicSelected) AppColors.Secondary else AppColors.transparent)
                .clickable {localizationViewModel.setLanguage("en") },
            contentAlignment = Alignment.Center
        ) {
            CText(
                text = "EN",
                color = if (!isArabicSelected) AppColors.White else AppColors.Secondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}