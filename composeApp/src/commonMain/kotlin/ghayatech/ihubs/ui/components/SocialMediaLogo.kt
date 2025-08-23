package ghayatech.ihubs.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.instagram
import ihubs.composeapp.generated.resources.linkedin
import ihubs.composeapp.generated.resources.logo
import ihubs.composeapp.generated.resources.star
import org.jetbrains.compose.resources.painterResource


/**
 * دالة مساعدة لتحويل اسم المنصة إلى معرف الصورة المناسب.
 *
 * @param platformName اسم المنصة باللغة العربية أو الإنجليزية (مثل "انستجرام" أو "Instagram").
 * @return معرف الصورة (Drawable Resource ID) أو null إذا لم يتم العثور عليه.
 */
@Composable
fun getPlatformDrawableId(platformName: String): Painter? {
    val cleanName = platformName.trim().lowercase()

    return when (cleanName) {
        "انستجرام", "instagram" -> painterResource(Res.drawable.instagram)
        "لينكدين", "لينكد ان", "linkedin" -> painterResource(Res.drawable.linkedin)
        else -> null
    }
}

/**
 * دالة Composable لعرض لوجو المنصة بناءً على اسمها.
 *
 * @param platformName اسم المنصة.
 * @param modifier المعدل الخاص بـ Compose.
 */
@Composable
fun SocialMediaLogo(
    platformName: String,
    modifier: Modifier = Modifier,
    onClick: (Painter) -> Unit = {}
) {
    val logoPainter = getPlatformDrawableId(platformName)

    if (logoPainter != null) {
        Image(
            painter = logoPainter,
            contentDescription = "Social media logo for $platformName",
            modifier = modifier.size(20.dp).clickable(onClick = { onClick(logoPainter) })
        )
    }
}