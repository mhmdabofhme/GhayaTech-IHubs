package ghayatech.ihubs.ui.components

import androidx.compose.foundation.Image
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
import ghayatech.ihubs.ui.theme.AppColors
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.no_result
import ihubs.composeapp.generated.resources.no_results
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun NoResult(message: String = stringResource(Res.string.no_results)) {
    Column(
        modifier = Modifier.fillMaxSize()
//            .background(AppColors.White)
                ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(Res.drawable.no_result), contentDescription = "error")
        Spacer(modifier = Modifier.height(12.dp))
        CText(
            message,
            color = AppColors.Secondary,
            fontWeight = FontWeight.Bold
        )
    }
}