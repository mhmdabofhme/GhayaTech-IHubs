package ghayatech.ihubs.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ghayatech.ihubs.networking.viewmodel.UiState
import ghayatech.ihubs.ui.theme.AppColors

class ProgressBar {
}

@Composable
fun ProgressBar(state: UiState<Nothing>) {
    AnimatedVisibility(visible = state is UiState.Loading) {
        Column(
            modifier = Modifier.fillMaxSize()
                .clickable {  }
                .background(Color.Black.copy(alpha = 0.3f)) // ← هنا
                .zIndex(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                color = AppColors.Secondary,
                strokeWidth = 5.dp
            )
            Spacer(modifier = Modifier.height(12.dp))
            CText(
                "Loading...",
                color = AppColors.Secondary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}