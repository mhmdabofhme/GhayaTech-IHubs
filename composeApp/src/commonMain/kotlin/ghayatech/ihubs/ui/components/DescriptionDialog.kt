package ghayatech.ihubs.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.send
import ghayatech.ihubs.ui.theme.AppColors
import ghayatech.ihubs.ui.theme.AppStringsProvider
import org.jetbrains.compose.resources.stringResource

@Composable
fun DescriptionDialog(
    onDismiss: () -> Unit,
    onItemClick: (String) -> Unit
) {
    val strings = AppStringsProvider.current()
    var description by remember { mutableStateOf("") }

    val initialColor = AppColors.White
    var backgroundColor by remember { mutableStateOf(initialColor) }
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(backgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppColors.White, shape = RoundedCornerShape(25.dp))
                    .padding(21.dp), horizontalAlignment = Alignment.CenterHorizontally
//                    .padding(horizontal = 24.dp)
            ) {

                CText(
                    text = strings.specify_details,
                    color = AppColors.Primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.size(24.dp))
                CTextField(
                    value = description,
                    onValueChange = { description = it },
                    placeholder = strings.describe_the_details_here,
                    singleLine = false,
                    minLines = 5,
                    maxLines = 15,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default)
                )
                Spacer(modifier = Modifier.size(18.dp))

                CButton(
                    text = strings.send,
                    onClick = { onItemClick(description) })

            }
        }
    }
}

