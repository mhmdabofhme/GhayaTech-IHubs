package ghayatech.ihubs.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.imgeye
import ghayatech.ihubs.ui.theme.AppColors
import org.jetbrains.compose.resources.painterResource

@Composable
fun CTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    maxLines :Int= 1,
//    label: String = "",
    placeholder: String = "",
//    leadingIcon: ImageVector? = null,
    trailingIcon: Painter? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    isPassword: Boolean = false,
    backgroundColor: Color = AppColors.Background,
//    textColor: Color = AppColors.Primary,
    cornerRadius: Dp = 12.dp,
    fontSize: Int = 16,
    singleLine: Boolean = true,
    inputType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    minLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = inputType, imeAction = imeAction
    ),
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val visualTransformation = when {
        isPassword && !passwordVisible -> PasswordVisualTransformation()
        else -> VisualTransformation.None
    }
//    val eyeIcon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff

//    val imgEye = remember { vectorResource(Res.drawable.imgeye) }
    val imgEye = painterResource(Res.drawable.imgeye)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = isEnabled,
        modifier = modifier
            .fillMaxWidth()
//            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(cornerRadius)),
        ,
//        label = { Text(label) }  ,
        maxLines = maxLines,
        placeholder = {
            CText(
                placeholder,
                fontWeight = FontWeight.Bold,
                color = AppColors.TextSecondary
            )
        },
//        leadingIcon = { leadingIcon?.let { Icon(it, contentDescription = null) } },
        trailingIcon = {
            when {
                isPassword -> {
                    Icon(
                        painter = imgEye,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { passwordVisible = !passwordVisible },
                        tint = AppColors.TextSecondary
                    )
                }

                trailingIcon != null && onTrailingIconClick != null -> {
                    Icon(
                        painter = trailingIcon,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            onTrailingIconClick()
                        },
                        tint = AppColors.TextSecondary
                    )
                }

                trailingIcon != null -> {
                    Icon(
                        painter = trailingIcon,
                        contentDescription = null,
                        tint = AppColors.TextSecondary
                    )
                }
            }
        },
        visualTransformation = visualTransformation,
        textStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = fontSize.sp
        ),
        singleLine = singleLine,
        shape = RoundedCornerShape(cornerRadius),
        keyboardOptions = keyboardOptions,
        minLines = minLines,
//        colors = TextFieldDefaults.outlinedTextFieldColors(
//            backgroundColor = backgroundColor,
//            cursorColor = AppColors.TextSecondary,
//            textColor = AppColors.TextSecondary,
//            focusedBorderColor = backgroundColor,
//            unfocusedBorderColor = backgroundColor,
//            placeholderColor = AppColors.TextSecondary,
//        ),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = backgroundColor,
            focusedContainerColor = backgroundColor,
            cursorColor = AppColors.TextSecondary,
            focusedBorderColor = backgroundColor,
            unfocusedBorderColor = backgroundColor,
            focusedTextColor = AppColors.TextSecondary,
            unfocusedTextColor = AppColors.TextSecondary,
            focusedPlaceholderColor = AppColors.TextSecondary,
            unfocusedPlaceholderColor = AppColors.TextSecondary,
//            cursorColor = borderColor
        )
    )

}