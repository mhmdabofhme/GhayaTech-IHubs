package ghayatech.ihubs.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ghayatech.ihubs.ui.theme.AppColors
import ghayatech.ihubs.ui.theme.AppStringsProvider
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.copy
import ihubs.composeapp.generated.resources.edit
import ihubs.composeapp.generated.resources.normal
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.FontResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    maxLinesBeforeExpand: Int = 2,
    showMoreText: String = AppStringsProvider.current().show_more,
    showLessText: String = AppStringsProvider.current().show_less,
    showMoreColor: Color = AppColors.purple,
    showLessColor: Color = AppColors.TextSecondary,
//    baseTextStyle: TextStyle = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
    fontResource: FontResource = Res.font.normal,
    fontSize: TextUnit = 12.sp,
    textColor: Color = AppColors.Black.copy(alpha = 0.6f),
) {
    var expanded by remember { mutableStateOf(false) }
    var isOverflowing by remember { mutableStateOf(false) }
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    val actualTextStyle =
        TextStyle(
            fontFamily = FontFamily(Font(fontResource)),
            fontSize = fontSize,
            color = textColor
        )

    // بناء النص المعروض
    val displayedAnnotatedString = remember(text, expanded, isOverflowing, textLayoutResult) {
        buildAnnotatedString {
            if (expanded) {
                // الوضع الموسع: اعرض النص بالكامل
                append(text)
                if (isOverflowing) { // إذا كان قد تجاوز في الأصل
                    withStyle(style = actualTextStyle.toSpanStyle().copy(color = showLessColor)) {
                        append(" $showLessText")
                    }
                }
            } else {
                // الوضع المختصر: عرض جزء من النص + "عرض المزيد" إذا كان يتجاوز
                if (textLayoutResult != null && isOverflowing) {
                    // إذا كان النص سيتجاوز بعدد الأسطر المحددة (قبل التوسيع)
                    val lastLineIndex = maxLinesBeforeExpand - 1
                    val endIndex = textLayoutResult!!.getLineEnd(lastLineIndex, visibleEnd = true)
                    // نأخذ جزء النص الذي يناسب الأسطر المحددة
                    val truncatedText = text.substring(0, endIndex).trimEnd()

                    // نُقدر المساحة التي ستأخذها "..." و "عرض المزيد"
                    // هذه الطريقة ليست دقيقة 100% لكنها كافية لتبدأ
                    val suffix = "... $showMoreText"
                    val approxSuffixLength = suffix.length

                    // نحاول قص النص الأساسي بما يكفي لإفساح مجال للـ suffix
                    val safeTruncatedLength =
                        (truncatedText.length - approxSuffixLength).coerceAtLeast(0)
                    append(truncatedText.substring(0, safeTruncatedLength).trimEnd())

                    // ثم نضيف الـ suffix
                    withStyle(style = actualTextStyle.toSpanStyle().copy(color = showMoreColor)) {
                        append(suffix)
                    }
                } else {
                    // إذا كان النص لا يتجاوز أو كان مختصرا جداً
                    append(text)
                }
            }
        }
    }


    // *** استخدام BasicText بدلاً من Text ***
    BasicText(
        text = displayedAnnotatedString, // استخدام الـ AnnotatedString المُنشأ
        modifier = modifier
            .fillMaxWidth() // تأكد من أن BasicText يأخذ العرض المتاح له
            .animateContentSize()
            .clickable(enabled = isOverflowing || expanded) { // قابل للنقر إذا كان هناك تجاوز أو كان ممتدًا
                expanded = !expanded
                println("ExpandableText Debug: BasicText clicked. Expanded: $expanded")
            },
        maxLines = if (expanded) Int.MAX_VALUE else maxLinesBeforeExpand,
        overflow = TextOverflow.Clip, // *** مهم: استخدام TextOverflow.Clip الآن ***
        onTextLayout = { result ->
            textLayoutResult = result
            println("ExpandableText Debug: onTextLayout called. Line count: ${result.lineCount}, Max lines before expand: $maxLinesBeforeExpand, Expanded: $expanded, Has visual overflow: ${result.hasVisualOverflow}")

            // تحديث isOverflowing بناءً على hasVisualOverflow
            if (!expanded && result.hasVisualOverflow) {
                isOverflowing = true
                println("ExpandableText Debug: isOverflowing set to true (Text has visual overflow).")
            } else if (expanded && !result.hasVisualOverflow && isOverflowing) {
                isOverflowing = false
                println("ExpandableText Debug: isOverflowing set to false (Text fully visible).")
            }
        },
//        fontFamily = FontFamily(Font(Res.font.normal),
        style = actualTextStyle
    )
}
