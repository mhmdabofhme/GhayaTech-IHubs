package ghayatech.ihubs.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.sp
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.normal
import ghayatech.ihubs.ui.theme.AppColors
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.FontResource

@Composable
fun CText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = AppColors.Black,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    fontFamily: FontResource = Res.font.normal,
    textAlign: TextAlign? = null,
    style: TextDecoration = TextDecoration.None,
    textDirection: TextDirection = TextDirection.Content,
    maxLines: Int = Int.MAX_VALUE,
    softWrap: Boolean = true
) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontFamily = FontFamily(Font(fontFamily)),
        textAlign = textAlign,
        modifier = modifier,
        style = TextStyle(
            textDecoration = style,
            textDirection = textDirection
        ),
        maxLines = maxLines,
        softWrap = softWrap,
    )
}
