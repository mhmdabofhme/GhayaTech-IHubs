package ghayatech.ihubs.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.bold
import ghayatech.ihubs.ui.theme.AppColors
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.FontResource

@Composable
fun CButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = AppColors.Primary,
    contentColor: Color = AppColors.White,
    borderColor: Color = AppColors.Primary,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Medium,
    // it should be Res.font.normal
    fontFamily: FontResource = Res.font.bold,
//    cornerRadius: Dp = 10.dp,
//    horizontalPadding: Dp = 24.dp,
//    verticalPadding: Dp = 12.dp,
    enabled: Boolean = true,
    isOutlined: Boolean = false
) {
    val shape = RoundedCornerShape(10.dp)

    val initialColor = AppColors.White
    var color by remember { mutableStateOf(initialColor) }

    color = if (isOutlined) AppColors.Secondary else Color.White
    Button(
        onClick = onClick,
        modifier = modifier.height(56.dp).fillMaxWidth()
            .clip(shape)
            .then(
                if (isOutlined)
                    Modifier
                        .border(width = 3.dp, color = borderColor,shape = shape,)
                else Modifier
            ),
//        enabled = enabled,
//        colors = ButtonDefaults.buttonColors(
//            backgroundColor = if (isOutlined) AppColors.White else backgroundColor,
//            contentColor = if (isOutlined) borderColor else contentColor
//        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isOutlined) AppColors.White else backgroundColor, // لون خلفية الزر
            contentColor =   if (isOutlined) borderColor else contentColor,         // لون محتوى الزر (نص/أيقونات)

            disabledContainerColor = if (isOutlined) AppColors.White.copy(alpha = 0.12f) else backgroundColor.copy(alpha = 0.12f),
            disabledContentColor   = if (isOutlined) borderColor.copy(alpha = 0.38f) else contentColor.copy(alpha = 0.38f)
        ),
        enabled = enabled,
        shape = shape,
//        contentPadding = PaddingValues(
//            horizontal = horizontalPadding,
//            vertical = verticalPadding
//        ),
    ) {
         CText(
             color =color,
             fontWeight = fontWeight,
            text = text,
            fontSize = fontSize,
            fontFamily = fontFamily
        )
    }
}
