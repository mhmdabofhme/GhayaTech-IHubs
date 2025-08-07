package ghayatech.ihubs.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.LayoutDirection
import com.russhwolf.settings.Settings
import kotlinx.datetime.LocalDate
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import network.chaintech.kmp_date_time_picker.ui.date_range_picker.changeDateFormat
import ghayatech.ihubs.networking.models.User
import ghayatech.ihubs.networking.network.ApiRoutes
import ghayatech.ihubs.networking.util.NetworkError
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.diamond
import ihubs.composeapp.generated.resources.first
import ihubs.composeapp.generated.resources.second
import ihubs.composeapp.generated.resources.third
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource

fun getErrorMessage(error: NetworkError):String{
    return when (error) {
        is NetworkError.HttpError -> error.message
        is NetworkError.Unauthorized -> "يجب تسجيل الدخول"
        is NetworkError.Conflict -> "يوجد تعارض في البيانات"
        is NetworkError.TooManyRequests -> "عدد كبير من المحاولات"
        is NetworkError.NoInternet -> "لا يوجد اتصال بالإنترنت"
        is NetworkError.ServerError -> "خطأ في الخادم"
        is NetworkError.Serialization -> "خطأ في تحويل البيانات"
        is NetworkError.RequestTimeout -> "انتهت مهلة الاتصال"
        is NetworkError.Unknown -> "حدث خطأ غير معروف"
        else -> {
            ""
        }
    }
}



fun isValid(list: List<String>): Boolean {
    return list.all { it.isNotBlank() }
}



fun handleTime(rawTime: String): String {
    return try {
        val parts = rawTime.split(":")
        val hour = parts[0].padStart(2, '0')
        val minute = parts[1].padStart(2, '0')
        "$hour:$minute"
    } catch (e: Exception) {
        "00:00"
    }
}


//fun formatTimestamp(timestamp: String): Pair<String, String> {
//    val instant = Instant.parse(timestamp)
//    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
//
//    val date = localDateTime.date
//    val time = localDateTime.time
//
//    // Format: yyyy-MM-dd
//    val formattedDate = date.toString()
//
//    // Format time: hh:mm AM/PM
//    val hour = time.hour
//    val minute = time.minute.toString().padStart(2, '0')
//    val hour12 = if (hour % 12 == 0) 12 else hour % 12
//    val period = if (hour < 12) "AM" else "PM"
//
//    val formattedTime = "${hour12.toString().padStart(2, '0')}:$minute $period"
//
//    return formattedDate to formattedTime
//}

fun splitDateTime(raw: String): Pair<String, String> {
    val parts = raw.split(" ")
    val date = parts.getOrNull(0) ?: ""
    val time = parts.getOrNull(1)?.substring(0, 5) ?: ""
    return date to time
}

fun calculateRemainingMillis(serverTimeString: String): Long {
    val parts = serverTimeString.split(" ")
    val datePart = parts.getOrNull(0) ?: return 0L
    val timePart = parts.getOrNull(1) ?: "00:00:00"

    val (year, month, day) = datePart.split("-").map { it.toInt() }
    val (hour, minute, second) = timePart.split(":").map { it.toInt() }

    val targetDateTime = LocalDateTime(year, month, day, hour, minute, second)
    val targetInstant = targetDateTime.toInstant(TimeZone.currentSystemDefault())

    val nowInstant = Clock.System.now()

    val diffMillis = targetInstant.toEpochMilliseconds() - nowInstant.toEpochMilliseconds()

    return if (diffMillis > 0) diffMillis else 0L
}


fun showErrorPage(message:String){
    showErrorPage(message)
}


@Composable
fun getPackageIcon(packageName: String): Painter {
    return when (packageName) {
        "hour" -> painterResource(Res.drawable.third)
        "day" -> painterResource(Res.drawable.second)
        "week" -> painterResource(Res.drawable.first)
        "month" -> painterResource(Res.drawable.diamond)
        else -> painterResource(Res.drawable.diamond)
    }
}

fun boolToIntString(value: Boolean): String {
    return if (value) "1" else "0"
}

fun getLayoutDirection(languageCode: String): LayoutDirection {
    return if (languageCode == "ar") {
        LayoutDirection.Rtl
    } else {
        LayoutDirection.Ltr
    }
}


fun getLocale(languageCode: String): Locale {
    return if (languageCode == "ar") {
        Locale("ar")
    } else {
        Locale("en")
    }
}

//fun formatLocalDateToIso(localDate: LocalDate): String {
//    return "%04d-%02d-%02d".changeDateFormat(localDate.year, localDate.monthNumber, localDate.dayOfMonth)
//}

