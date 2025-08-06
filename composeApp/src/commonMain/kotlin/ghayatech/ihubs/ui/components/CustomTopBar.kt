package ghayatech.ihubs.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.back
import ihubs.composeapp.generated.resources.bold
import ghayatech.ihubs.ui.theme.AppColors
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CustomTopBar(
    modifier: Modifier = Modifier,
    title: String,
    showBackButton: Boolean = true,
    backButtonText: String = stringResource(Res.string.back),
    onBackClick: (() -> Unit)? = null,
    endContent: (@Composable () -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
//            .height(56.dp)
//            .padding(horizontal = 22.dp),
                ,
        contentAlignment = Alignment.Center
    ) {
        // الزر الخلفي (اختياري)
        if (showBackButton && onBackClick != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable { onBackClick() }
            ) {
                Icon(
                    painter = painterResource(Res.drawable.back), // أو SVG مخصص

                    contentDescription = "back",
                    modifier = Modifier
                        .size(24.dp)
                    , tint = AppColors.Black
                )
//                Spacer(modifier = Modifier.size(9.dp))
                CText(backButtonText, fontSize = 14.sp, fontWeight = FontWeight.Bold,)
            }
        }

        // عنوان المنتصف
        CText(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Res.font.bold,
            modifier = Modifier.align(Alignment.Center)
        )

        // محتوى النهاية (اختياري)
        if (endContent != null) {
            Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                endContent()
            }
        }
    }
}
