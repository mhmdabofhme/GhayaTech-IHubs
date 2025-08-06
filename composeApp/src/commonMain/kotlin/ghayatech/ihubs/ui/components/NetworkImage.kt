package ghayatech.ihubs.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import com.seiko.imageloader.rememberAsyncImagePainter
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.resource_default
import ghayatech.ihubs.ui.theme.AppColors
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.painterResource


@Composable
fun NetworkImage(
    url: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    onSuccess: () -> Unit = {}
) {
    if (url.isNullOrBlank()) return

    val resource = asyncPainterResource(url)

    if (resource is Resource.Success) {
        onSuccess()
    }

    KamelImage(
        resource = resource,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier,
        onLoading = {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = AppColors.Secondary,
                    strokeWidth = 2.dp
                )
            }
        }
    )
}


//
//@Composable
//fun NetworkImage(
//    url: String?,
//    placeholder: Painter = painterResource(Res.drawable.resource_default),
//    modifier: Modifier = Modifier,
//    contentDescription: String? = null
//) {
//    if (url.isNullOrBlank()) return
//
//    KamelImage(
//        resource = asyncPainterResource(url),
//        contentDescription = contentDescription,
//        contentScale = ContentScale.Crop,
//        modifier = modifier,
//        onLoading = {
//            // أثناء التحميل نعرض مؤشر تحميل دائري
////            Box(
////                modifier = modifier,
////                contentAlignment = Alignment.Center
////            ) {
////                CircularProgressIndicator(
////                    color = AppColors.Secondary,
////                    strokeWidth = 2.dp
////                )
////            }
//        },
//        onFailure = {
////            Image(
////                placeholder, "Default",
////                modifier = Modifier.clip(RoundedCornerShape(12.dp)),
////                contentScale = ContentScale.Crop
////            )
//        }
//    )
//}


//@Composable
//fun NetworkImage(
//    url: String?, modifier: Modifier = Modifier,
//    placeholder: Painter = painterResource(Res.drawable.resource_default),
//) {
//    val painter = if (url != null) {
//        rememberAsyncImagePainter(url)
//    } else placeholder
//
//    Image(
//        painter = painter,
//        contentDescription = "image",
////        modifier = Modifier
////            .clip(RoundedCornerShape(12.dp)),
//        modifier = modifier,
//        contentScale = ContentScale.Crop
//    )
//}
