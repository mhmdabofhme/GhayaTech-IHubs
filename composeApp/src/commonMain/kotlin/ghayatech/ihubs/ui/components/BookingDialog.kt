package ghayatech.ihubs.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.bold
import ihubs.composeapp.generated.resources.copy
import ihubs.composeapp.generated.resources.date
import ihubs.composeapp.generated.resources.time
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import network.chaintech.kmp_date_time_picker.ui.datepicker.WheelDatePickerView
import network.chaintech.kmp_date_time_picker.ui.timepicker.WheelTimePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.TimeFormat
import network.chaintech.kmp_date_time_picker.utils.now
import ghayatech.ihubs.networking.models.PaymentInfo
import ghayatech.ihubs.ui.theme.AppColors
import ghayatech.ihubs.ui.theme.AppStringsProvider
import ghayatech.ihubs.utils.handleTime
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource


@Composable
fun BookingDialog(
    paymentInfo: PaymentInfo,
    onDismiss: () -> Unit,
    onBookClick: (Request) -> Unit,
    onCopyClick: (String) -> Unit,
    hasTime: Boolean = false,

    ) {
    val strings = AppStringsProvider.current()
//    val clipboardManager = remember { ClipboardManager() }

    var worksHours by rememberSaveable { mutableStateOf("") }
    var request by remember { mutableStateOf<Request?>(null) }

    var selectedTime by remember { mutableStateOf("") }
//    var selectedTime by remember { mutableStateOf(LocalTime.now().toString()) }
//    var selectedDate by remember { mutableStateOf(LocalDate.now().toString()) }
    var selectedDate by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Black50)
            .padding(horizontal = 21.dp)
            .clip(RoundedCornerShape(25.dp)).clickable {
                onDismiss()
            }

//                .background(backgroundColor)
//            .shadow(10.dp, shape = RoundedCornerShape(25.dp), clip = false),
        ,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* لا تفعل شيئًا عند النقر على هذه المنطقة */ }
                .shadow(25.dp, RoundedCornerShape(25.dp)) // ← تضيف الظل
                .background(AppColors.White, RoundedCornerShape(25.dp))
                .padding(21.dp)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                selectedDate =
                    selectedDate.ifEmpty { strings.books_date }

                BookingItem(
                    text = selectedDate,
                    icon = painterResource(Res.drawable.date),
                    onItemClick = {
                        showDatePicker = true
                    })

                Spacer(modifier = Modifier.size(12.dp))


                if (hasTime) {
                    selectedTime = selectedTime.ifEmpty { strings.books_time }

                    BookingItem(
                        text = selectedTime,
                        icon = painterResource(Res.drawable.time),
                        onItemClick = {
                            showTimePicker = true
                        }
                    )




                    Spacer(modifier = Modifier.size(12.dp))

                    CTextField(
                        value = worksHours,
                        onValueChange = { worksHours = it },
                        placeholder = strings.works_hours,
                        inputType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                        modifier = Modifier.fillMaxWidth()
                    )

                }



                Spacer(modifier = Modifier.height(20.dp))

                CText(
                    text = strings.transfer_details,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                TransferRow(
                    label = strings.account_number,
                    value = paymentInfo.bankAccountNumber,
                    copyable = true,
                    isRed = true,
                    onClick = {
                        onCopyClick(paymentInfo.bankAccountNumber)
                    }
                )
                TransferRow(
                    label = strings.mobile_number,
                    value = paymentInfo.mobilePaymentNumber,
                    copyable = true,
                    isRed = true,
                    onClick = {
                        onCopyClick(paymentInfo.mobilePaymentNumber)
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                CButton(
                    text = strings.book_now,
                    onClick = {
                        onBookClick(
                            Request(
                                date = selectedDate,
                                time = selectedTime,
                                numberOfHours = worksHours.toIntOrNull()
                            )
                        )
                    })

            }
        }

        WheelDatePickerView(
            height = 250.dp,
            showDatePicker = showDatePicker,
            dateTimePickerView = DateTimePickerView.DIALOG_VIEW,
            rowCount = 3,
            doneLabel = strings.done,
            title = strings.books_date,
            minDate = LocalDate.now(),
            doneLabelStyle = TextStyle(
                color = AppColors.Primary,
                fontFamily = FontFamily(Font(Res.font.bold))
            ),
            titleStyle = TextStyle(
                color = AppColors.Primary,
                fontFamily = FontFamily(Font(Res.font.bold))
            ),
            yearsRange = LocalDate.now().year..LocalDate.now().year + 1,
            onDoneClick = {
                println("TAGTAG $it")
                selectedDate = it.toString()
                showDatePicker = false
            },
            onDismiss = {
                showDatePicker = false
            },
        )

        WheelTimePickerView(
            height = 250.dp,
            showTimePicker = showTimePicker,
            dateTimePickerView = DateTimePickerView.DIALOG_VIEW,
            rowCount = 3,
            minTime = LocalTime.now(),
            doneLabel = strings.done,
            title = strings.books_time,
            timeFormat = TimeFormat.AM_PM,
            doneLabelStyle = TextStyle(
                color = AppColors.Primary,
                fontFamily = FontFamily(Font(Res.font.bold))
            ),
            titleStyle = TextStyle(
                color = AppColors.Primary,
                fontFamily = FontFamily(Font(Res.font.bold))
            ),
            onDoneClick = {
                selectedTime = handleTime(it.toString())
                showTimePicker = false
            },
            onDismiss = {
                showTimePicker = false
            },
        )
    }
//    }
}


@Composable
fun TransferRow(
    label: String,
    value: String,
    copyable: Boolean,
    isRed: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, AppColors.Primary, RoundedCornerShape(15.dp))
            .padding(horizontal = 15.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CText(text = label, color = AppColors.Secondary)
        Spacer(modifier = Modifier.width(6.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = onClick)
        ) {
            CText(
                text = value,
                color = if (isRed) AppColors.Error else AppColors.Secondary,
                fontWeight = FontWeight.Bold,
                style = TextDecoration.Underline
            )
            if (copyable) {
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    painterResource(Res.drawable.copy),
                    contentDescription = null,
                    tint = AppColors.Secondary
                )
            }
        }
    }
}


data class Request(
//    val workspaceId: Int,
//    val packageId: Int,
    val date: String,
    val time: String? = null,
    val numberOfHours: Int? = null
)