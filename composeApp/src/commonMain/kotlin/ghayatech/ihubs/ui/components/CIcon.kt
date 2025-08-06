package ghayatech.ihubs.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.fingerprint
import ihubs.composeapp.generated.resources.profile
import ghayatech.ihubs.ui.theme.AppColors
import org.jetbrains.compose.resources.painterResource


@Composable
fun CIcon(
    img: Painter = painterResource(Res.drawable.profile),
    size: Dp = 56.dp,
    onClick: () -> Unit,
    background: Color = AppColors.Background,
    padding: Dp = 10.dp,
    contentDescription: String = "none",
    modifier: Modifier = Modifier,
    shadow: Dp = 0.dp,
    tint: Color = AppColors.TextSecondary
) {

    Icon(
        img, contentDescription = contentDescription,
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .shadow(shadow, shape = CircleShape, clip = false)
            .background(background)
            .padding(padding)
            .clickable { onClick() }

        , tint = tint
    )

}
