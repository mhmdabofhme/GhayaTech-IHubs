package ghayatech.ihubs.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.error
import ghayatech.ihubs.ui.theme.AppColors
import ghayatech.ihubs.ui.theme.AppStringsProvider
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ErrorPage(message: String = AppStringsProvider.current().unknown) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(AppColors.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(Res.drawable.error), contentDescription = "error")
        Spacer(modifier = Modifier.height(12.dp))
        CText(
            message,
            color = AppColors.Secondary,
            fontWeight = FontWeight.Bold
        )
    }
}