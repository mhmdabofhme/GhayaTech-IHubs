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
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.copy
import ihubs.composeapp.generated.resources.edit
import ihubs.composeapp.generated.resources.normal
import ihubs.composeapp.generated.resources.show_less
import ihubs.composeapp.generated.resources.show_more
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.FontResource
import org.jetbrains.compose.resources.stringResource

//@Composable
//fun ExpandableText(
////    text: String,
////    minimizedMaxLines: Int = 1,
////    readMoreText: String = stringResource(Res.string.copy),
////    readLessText: String = stringResource(Res.string.edit),
////    readMoreColor: Color = AppColors.purple,
////    readLessColor: Color = AppColors.TextSecondary
//    text: String,
//    modifier: Modifier = Modifier,
//    minLines: Int = 1, // عدد الأسطر التي ستظهر قبل "عرض المزيد"
//    showMoreText: String = stringResource(Res.string.copy),
//    showLessText: String = stringResource(Res.string.edit),
//    showMoreColor: Color = AppColors.purple,
//    showLessColor: Color = AppColors.TextSecondary,
//    font: FontResource = Res.font.normal,
//) {
////    var isExpanded by remember { mutableStateOf(false) }
////    var isOverflowing by remember { mutableStateOf(false) }
////    var finalText by remember { mutableStateOf(text) }
////
////    val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }
////
////    // حساب النص المقتطع
////    LaunchedEffect(layoutResult.value) {
////        if (!isExpanded) {
////            layoutResult.value?.let { layout ->
////                if (layout.lineCount > minimizedMaxLines) {
////                    isOverflowing = true
////                    val end = layout.getLineEnd(minimizedMaxLines - 1, visibleEnd = true)
////                    finalText = text.substring(0, end).trimEnd() + "..."
////                }
////            }
////        }
////    }
////
////    if (text.length > 100){
////
////    }
////
////    // اختيار النص والإجراء
////    val actionText = if (isExpanded) readLessText else readMoreText
////    val actionColor = if (isExpanded) readLessColor else readMoreColor
////
//////    val displayText = if (isOverflowing && !isExpanded) {
//////        finalText + actionText
//////    } else {
//////        text + actionText
//////    }
////
////    Text(
////        text = buildAnnotatedString {
////            append(if (isOverflowing && !isExpanded) finalText else text)
////            withStyle(style = SpanStyle(color = actionColor, fontWeight = FontWeight.Bold)) {
////                append(actionText)
////            }
////        },
////        maxLines = if (isExpanded) Int.MAX_VALUE else minimizedMaxLines,
////        onTextLayout = {
////            layoutResult.value = it
////        },
////        color = AppColors.Secondary,
////        modifier = Modifier
////            .fillMaxWidth()
////            .clickable { isExpanded = !isExpanded },
////        style = TextStyle(fontSize = 14.sp),
////        fontFamily = FontFamily(Font(Res.font.normal)),
////    )
//
//
//    var expanded by remember { mutableStateOf(false) } // حالة إذا كان النص ممدودًا أم لا
//    var showExpandCollapseButton by remember { mutableStateOf(false) } // للتحكم في ظهور زر "عرض المزيد/إخفاء المزيد"
//    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) } // نتيجة قياس النص
//
//    Column(modifier = modifier) {
//        Text(
//            text = text,
//            modifier = Modifier
//                .fillMaxWidth()
//                .animateContentSize() // لتأثير التمدد/الانكماش السلس
//                .clickable(enabled = showExpandCollapseButton) { // قابل للنقر فقط إذا كان الزر ظاهرًا
//                    expanded = !expanded
//                },
//            maxLines = if (expanded) Int.MAX_VALUE else minLines, // عدد الأسطر المعروضة
//            onTextLayout = { result ->
//                textLayoutResult = result
//                // تحديد ما إذا كان النص طويلاً بما يكفي لإظهار زر "عرض المزيد"
//                if (!expanded && result.lineCount > minLines) {
//                    showExpandCollapseButton = true
//                }
//            },
//            fontFamily = FontFamily(Font(font))
//        )
//
//        // عرض زر "عرض المزيد" أو "إخفاء المزيد" إذا كان النص طويلاً
//        if (showExpandCollapseButton) {
//            val annotatedString = buildAnnotatedString {
////                withStyle(style = TextStyle.toSpanStyle().copy(color = if (expanded) showLessColor else showMoreColor)) {
//                withStyle(
//                    style = SpanStyle(color = if (expanded) showLessColor else showMoreColor),
////                    font = FontFamily(Font(font))
//                ) {
//                    append(if (expanded) showLessText else showMoreText)
//                }
//            }
//            Text(
//                text = annotatedString,
//                modifier = Modifier
//                    .clickable { expanded = !expanded }
//                    .padding(top = 4.dp), // مسافة صغيرة بين النص وزر "عرض المزيد"
//                fontFamily = FontFamily(Font(font))
//            )
//        }
//    }
//}

//
//@Composable
//fun ExpandableText(
//    text: String,
//    modifier: Modifier = Modifier,
//    minLines: Int = 3, // عدد الأسطر التي ستظهر قبل "عرض المزيد" (القيمة الافتراضية 3 لتسهيل الاختبار)
//    showMoreText: String = stringResource(Res.string.copy),
//    showLessText: String = stringResource(Res.string.edit),
//    showMoreColor: Color = AppColors.purple,
//    showLessColor: Color = AppColors.TextSecondary,
//    // يفضل تمرير TextStyle كاملاً للتحكم الدقيق (بما في ذلك حجم الخط والوزن)
//    baseTextStyle: TextStyle = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
//    // قم بتمرير FontResource إذا كنت بحاجة إلى خط مخصص، وإلا استخدم Font(font) مباشرة
//    fontResource: Any? = null // استخدم FontResource إذا كنت تستخدم Moko Resources
//) {
//    var expanded by remember { mutableStateOf(false) } // حالة إذا كان النص ممدودًا أم لا
//    var showExpandCollapseButton by remember { mutableStateOf(false) } // للتحكم في ظهور زر "عرض المزيد/إخفاء المزيد"
//    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) } // نتيجة قياس النص
//
//    // بناء نمط الخط الفعلي بناءً على baseTextStyle و fontResource الممرر
//    val actualTextStyle = remember(baseTextStyle, fontResource) {
//        if (fontResource != null) {
//            // هنا يجب عليك تحويل fontResource إلى Font إذا لم يكن بالفعل
//            // على سبيل المثال: baseTextStyle.copy(fontFamily = FontFamily(fontResource as Font))
//            // ولكن للحصول على خط افتراضي آمن حاليًا:
//            baseTextStyle.copy(fontFamily = FontFamily.Default) // استخدم خط افتراضي إذا لم يكن لديك FontResource
//        } else {
//            baseTextStyle
//        }
//    }
//
//
//    Column(modifier = modifier) {
//        Text(
//            text = text,
//            modifier = Modifier
//                .fillMaxWidth()
//                .animateContentSize() // لتأثير التمدد/الانكماش السلس
//                .clickable(enabled = showExpandCollapseButton) { // قابل للنقر فقط إذا كان الزر ظاهرًا
//                    expanded = !expanded
//                    println("ExpandableText Debug: Main Text clicked. Expanded: $expanded")
//                },
//            maxLines = if (expanded) Int.MAX_VALUE else minLines, // عدد الأسطر المعروضة
//            onTextLayout = { result ->
//                textLayoutResult = result
//                println("ExpandableText Debug: onTextLayout called. Line count: ${result.lineCount}, Min lines: $minLines, Expanded: $expanded")
//                // تحديد ما إذا كان النص طويلاً بما يكفي لإظهار زر "عرض المزيد"
//                if (!expanded && result.lineCount > minLines) {
//                    showExpandCollapseButton = true
//                    println("ExpandableText Debug: showExpandCollapseButton set to true.")
//                } else if (expanded && result.lineCount <= minLines && showExpandCollapseButton) {
//                    // إذا أصبح النص أقصر بعد التمديد (نادرًا ما يحدث ولكن للكمال)
//                    showExpandCollapseButton = false
//                    println("ExpandableText Debug: showExpandCollapseButton set to false (text became short).")
//                }
//            },
//            style = actualTextStyle // تطبيق النمط الفعلي هنا
//        )
//
//        // عرض زر "عرض المزيد" أو "إخفاء المزيد" إذا كان النص طويلاً
//        if (showExpandCollapseButton) {
//            val annotatedString = buildAnnotatedString {
//                // استخدام نمط النص الأساسي ونسخ لونه فقط
//                withStyle(
//                    style = actualTextStyle.toSpanStyle().copy(color = if (expanded) showLessColor else showMoreColor)
//                ) {
//                    append(if (expanded) showLessText else showMoreText)
//                }
//            }
//            Text(
//                text = annotatedString,
//                modifier = Modifier
//                    .clickable {
//                        expanded = !expanded
//                        println("ExpandableText Debug: Show More/Less clicked. Expanded: $expanded")
//                    }
//                    .weight(1F)
//                    .padding(top = 4.dp), // مسافة صغيرة بين النص وزر "عرض المزيد"
//                style = actualTextStyle.copy(color = if (expanded) showLessColor else showMoreColor) // تطبيق النمط الفعلي مع اللون المخصص
//            )
//        }
//    }
//}


//@Composable
//fun ExpandableText(
//    text: String,
//    modifier: Modifier = Modifier,
//    maxLinesBeforeExpand: Int = 1, // عدد الأسطر التي ستظهر قبل "عرض المزيد" (تم تغيير الاسم للتوضيح)
//    showMoreText: String = stringResource(Res.string.copy),
//    showLessText: String = stringResource(Res.string.edit),
//    showMoreColor: Color = AppColors.purple,
//    showLessColor: Color = AppColors.TextSecondary,
//    baseTextStyle: TextStyle = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
//    fontResource: Any? = null
//) {
//    var expanded by remember { mutableStateOf(false) }
//    // هذه الحالة ستُضبط إذا كان النص يتجاوز maxLinesBeforeExpand
//    var isOverflowing by remember { mutableStateOf(false) }
//    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
//
//    val actualTextStyle = remember(baseTextStyle, fontResource) {
//        if (fontResource != null) {
//            baseTextStyle.copy(fontFamily = FontFamily.Default) // استخدم خط افتراضي أو قم بتحويل fontResource
//        } else {
//            baseTextStyle
//        }
//    }
//
//    Column(modifier = modifier) {
//        Text(
//            text = text,
//            modifier = Modifier
//                .fillMaxWidth()
//                .animateContentSize()
//                .clickable(enabled = isOverflowing) { // قابل للنقر فقط إذا كان هناك تجاوز
//                    expanded = !expanded
//                    println("ExpandableText Debug: Main Text clicked. Expanded: $expanded")
//                },
//            // إذا لم يكن ممتدًا، حدد عدد الأسطر الأقصى وقطع النص
//            maxLines = if (expanded) Int.MAX_VALUE else maxLinesBeforeExpand,
//            overflow = if (expanded) TextOverflow.Clip else TextOverflow.Ellipsis, // *** إضافة مهمة هنا ***
//            onTextLayout = { result ->
//                textLayoutResult = result
//                println("ExpandableText Debug: onTextLayout called. Line count: ${result.lineCount}, Max lines before expand: $maxLinesBeforeExpand, Expanded: $expanded, Has visual overflow: ${result.hasVisualOverflow}")
//                // نتحقق مما إذا كان النص يتجاوز عدد الأسطر الأقصى (حتى لو كان لا يلتف بصريًا)
//                // نستخدم result.hasVisualOverflow هنا لأنه يشير إلى ما إذا كان النص قد تم قصه
//                if (!expanded && result.hasVisualOverflow) {
//                    isOverflowing = true
//                    println("ExpandableText Debug: isOverflowing set to true (Text has visual overflow).")
//                } else if (expanded && !result.hasVisualOverflow) {
//                    isOverflowing = false // إذا تم عرضه بالكامل ولم يعد هناك تجاوز
//                    println("ExpandableText Debug: isOverflowing set to false (Text fully visible).")
//                }
//            },
//            style = actualTextStyle
//        )
//
//        // عرض زر "عرض المزيد" أو "إخفاء المزيد" إذا كان النص يتجاوز
//        if (isOverflowing) { // نستخدم isOverflowing للتحكم في ظهور الزر
//            val annotatedString = buildAnnotatedString {
//                withStyle(
//                    style = actualTextStyle.toSpanStyle().copy(color = if (expanded) showLessColor else showMoreColor)
//                ) {
//                    append(if (expanded) showLessText else showMoreText)
//                }
//            }
//            Text(
//                text = annotatedString,
//                modifier = Modifier
//                    .clickable {
//                        expanded = !expanded
//                        println("ExpandableText Debug: Show More/Less clicked. Expanded: $expanded")
//                    }
//                    .padding(top = 4.dp),
//                style = actualTextStyle.copy(color = if (expanded) showLessColor else showMoreColor)
//            )
//        }
//    }
//}


//@Composable
//fun ExpandableText(
//    text: String,
//    modifier: Modifier = Modifier,
//    maxLinesBeforeExpand: Int = 1,
//    showMoreText: String = stringResource(Res.string.show_more),
//    showLessText: String = stringResource(Res.string.show_less),
//    showMoreColor: Color = AppColors.purple,
//    showLessColor: Color = AppColors.TextSecondary,
//    baseTextStyle: TextStyle = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
//    fontResource: Any? = null
//) {
//    var expanded by remember { mutableStateOf(false) }
//    var isOverflowing by remember { mutableStateOf(false) }
//    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
//
//    val actualTextStyle = remember(baseTextStyle, fontResource) {
//        if (fontResource != null) {
//            // قم بتحويل fontResource إلى Font بشكل صحيح هنا
//            // مثال: baseTextStyle.copy(fontFamily = FontFamily(fontResource as Font))
//            baseTextStyle.copy(fontFamily = FontFamily.Default) // استخدم خط افتراضي إذا لم يكن لديك FontResource
//        } else {
//            baseTextStyle
//        }
//    }
//
//    // النص الذي سيتم عرضه (بما في ذلك "عرض المزيد" أو "إخفاء المزيد")
//    val displayText = remember(text, expanded, isOverflowing, showMoreText, showLessText, showMoreColor, showLessColor, actualTextStyle) {
//        buildAnnotatedString {
//            if (expanded) {
//                // إذا كان النص ممتدًا، اعرض النص بالكامل
//                append(text)
//                // ثم أضف "إخفاء المزيد" بلون مختلف
//                if (isOverflowing) { // تأكد من وجود تجاوز أصلاً لإظهار "إخفاء المزيد"
//                    withStyle(style = actualTextStyle.toSpanStyle().copy(color = showLessColor)) {
//                        append(" $showLessText") // مسافة قبل النص
//                    }
//                }
//            } else {
//                // إذا لم يكن ممتدًا، اعرض النص كما هو أولاً
//                append(text)
//                // ثم، إذا كان هناك تجاوز، أضف "عرض المزيد"
//                if (isOverflowing) {
//                    // هنا سنقوم بإضافة النص فقط، سيتولى TextOverflow.Ellipsis القص
//                    // لا نضيف "..." هنا لأن TextOverflow.Ellipsis يضيفها تلقائيًا
//                    withStyle(style = actualTextStyle.toSpanStyle().copy(color = showMoreColor)) {
//                        append(" $showMoreText") // مسافة قبل النص
//                    }
//                }
//            }
//        }
//    }
//
//
//    Text(
//        text = displayText, // استخدام الـ AnnotatedString الذي تم بناؤه
//        modifier = modifier // استخدم modifier الذي يأتي من الخارج مباشرةً
//            .fillMaxWidth() // تأكد من أن الـ Text يأخذ العرض المتاح له
//            .animateContentSize()
//            .clickable(enabled = isOverflowing) { // قابل للنقر فقط إذا كان هناك تجاوز
//                expanded = !expanded
//                println("ExpandableText Debug: Text clicked. Expanded: $expanded")
//            },
//        maxLines = if (expanded) Int.MAX_VALUE else maxLinesBeforeExpand,
//        overflow = TextOverflow.Ellipsis, // *** حافظ على Ellipsis هنا دائمًا لضمان القطع ***
//        onTextLayout = { result ->
//            textLayoutResult = result
//            println("ExpandableText Debug: onTextLayout called. Line count: ${result.lineCount}, Max lines before expand: $maxLinesBeforeExpand, Expanded: $expanded, Has visual overflow: ${result.hasVisualOverflow}")
//            // نتحقق مما إذا كان النص يتجاوز عدد الأسطر الأقصى عندما لا يكون ممتدًا
//            if (!expanded && result.hasVisualOverflow) {
//                isOverflowing = true
//                println("ExpandableText Debug: isOverflowing set to true (Text has visual overflow).")
//            } else if (expanded && !result.hasVisualOverflow && isOverflowing) {
//                // إذا تم عرضه بالكامل ولم يعد هناك تجاوز، نضبط isOverflowing إلى false
//                isOverflowing = false
//                println("ExpandableText Debug: isOverflowing set to false (Text fully visible).")
//            }
//        },
//        style = actualTextStyle
//    )
//}


@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    maxLinesBeforeExpand: Int = 2,
    showMoreText: String = stringResource(Res.string.show_more),
    showLessText: String = stringResource(Res.string.show_less),
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

//    val actualTextStyle = remember(baseTextStyle, fontResource) {
////        if (fontResource != null) {
////            // قم بتحويل fontResource إلى Font بشكل صحيح هنا
////            // مثال: baseTextStyle.copy(fontFamily = FontFamily(fontResource as Font))
////            baseTextStyle.copy(fontFamily = FontFamily(Font(fontResource = Res.font.normal))) // استخدم خط افتراضي
////        } else {
//        baseTextStyle.copy(
//            fontFamily = FontFamily(
//                Font(fontResource)
//            )
//        ) // استخدم FontResource إذا كان متاحًا
////        }
//    }
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
