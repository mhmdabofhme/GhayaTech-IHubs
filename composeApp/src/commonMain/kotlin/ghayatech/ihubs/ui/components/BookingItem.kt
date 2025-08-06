package ghayatech.ihubs.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ghayatech.ihubs.ui.theme.AppColors

@Composable
fun BookingItem(
    text: String,
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    onItemClick: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(58.dp).background(AppColors.Background, shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 20.dp)
            .clickable {
                onItemClick()
            }
        , verticalAlignment = Alignment.CenterVertically
    ) {
        CText(
            text = text,
            fontWeight = FontWeight.Bold,
            color = AppColors.TextSecondary,
            modifier = Modifier.weight(1F)
        )
        if (icon != null) {
            Icon(painter = icon, contentDescription = "", tint = AppColors.Secondary)
        }
    }

}