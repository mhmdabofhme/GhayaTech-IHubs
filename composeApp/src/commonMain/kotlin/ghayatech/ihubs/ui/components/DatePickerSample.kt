package ghayatech.ihubs.ui.components

//import com.halilibo.compose.material.dialog.*
//import com.halilibo.compose.material.dialog.datetime.datepicker.datepicker
//import androidx.compose.runtime.*
//import androidx.compose.material3.*
//import kotlinx.datetime.LocalDate
//
//@Composable
//fun DatePickerSample() {
//    var pickedDate by remember { mutableStateOf<LocalDate?>(null) }
//    val dateDialog = rememberMaterialDialogState()
//
//    MaterialDialog(
//        dialogState = dateDialog,
//        buttons = {
//            positiveButton("OK")
//            negativeButton("Cancel")
//        }
//    ) {
//        datepicker(
//            initialDate = LocalDate(2025, 5, 12),
//            title = "اختر التاريخ"
//        ) { date ->
//            pickedDate = date
//        }
//    }
//
//    Column {
//        Button(onClick = { dateDialog.show() }) {
//            Text("اختر التاريخ")
//        }
//
//        pickedDate?.let {
//            Text("التاريخ المختار: $it")
//        }
//    }
//}
