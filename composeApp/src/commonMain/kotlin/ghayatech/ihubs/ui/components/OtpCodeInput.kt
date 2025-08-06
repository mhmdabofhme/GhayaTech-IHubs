package ghayatech.ihubs.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ghayatech.ihubs.ui.theme.AppColors

@Composable
fun OtpCodeInput(
    codeLength: Int,
    onCodeComplete: (String) -> Unit
) {

    val otpValues = remember { mutableStateListOf(*Array(codeLength) { "" }) }
    val focusRequesters = remember { List(codeLength) { FocusRequester() } }

    LaunchedEffect(Unit) {
        focusRequesters.firstOrNull()?.requestFocus()
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until codeLength) {
            BasicTextField(
                value = otpValues[i],
                onValueChange = { value ->
                    if (value.length <= 1 && value.all { it.isDigit() }) {
                        otpValues[i] = value
                        if (value.isNotEmpty()) {
                            if (i < codeLength - 1) {
                                focusRequesters[i + 1].requestFocus()
                            } else {
                                // الكود مكتمل
                                val fullCode = otpValues.joinToString("")
                                if (fullCode.length == codeLength) {
                                    onCodeComplete(fullCode)
                                }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .size(56.dp)
                    .focusRequester(focusRequesters[i])
                    .border(1.dp, AppColors.TextSecondary, RoundedCornerShape(8.dp))
                    .background(AppColors.White)
                    .padding(4.dp),
                textStyle = TextStyle.Default.copy(
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),

            )
        }
    }
}
