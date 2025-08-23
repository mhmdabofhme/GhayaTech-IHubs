package ghayatech.ihubs.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.success
import ghayatech.ihubs.ui.components.CustomBottomSheetContent
import ghayatech.ihubs.ui.theme.AppColors
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun BottomSheetScreen(
    openBottomSheet: MutableState<Boolean>,
    title: String,
    description: String,
    buttonText: String,
    onDone: () -> Unit
) {
    val visible = openBottomSheet.value
//    val scope = rememberCoroutineScope()
    val animatedOffset = remember { Animatable(1000f) } // يبدأ خارج الشاشة

    LaunchedEffect(visible) {
        if (visible) {
            animatedOffset.animateTo(
                targetValue = 0F,
                animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
            )
        } else {
            animatedOffset.animateTo(
                targetValue = 1000f,
                animationSpec = tween(durationMillis = 200, easing = FastOutLinearInEasing)
            )
        }
    }

    if (visible || animatedOffset.value > 0.1f) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    openBottomSheet.value = false
                }
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset { IntOffset(x = 0, y = animatedOffset.value.toInt()) }
                    .fillMaxWidth()
                    .background(AppColors.White,
                        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(22.dp)
            ) {
                CustomBottomSheetContent(
                    imagePainter = painterResource(Res.drawable.success),
                    title = title,
                    description = description,
                    buttonText = buttonText,
                    onButtonClick = {
                        openBottomSheet.value = false
                        onDone()
                    }
                )
            }
        }
    }
}



//@Composable
//fun BottomSheetScreen(
//    openBottomSheet: MutableState<Boolean>,
//    imagePainter: Painter = painterResource(Res.drawable.success),
//    title: String = stringResource(Res.string.verification_success),
//    description: String = stringResource(Res.string.verification_success_message),
//    buttonText: String = stringResource(Res.string.start_experience),
//    onDone: () -> Unit
//) {
//    if (openBottomSheet.value) {
//        ModalBottomSheet(
//            onDismissRequest = { openBottomSheet.value = false },
//            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
//containerColor = AppColors.White
//            ) {
//            CustomBottomSheetContent(
//                onButtonClick = {
//                    openBottomSheet.value = false
//                    onDone()
//                },
//                imagePainter = imagePainter,
//                title = title,
//                description = description,
//                buttonText = buttonText
//            )
//        }
//    }
//}
