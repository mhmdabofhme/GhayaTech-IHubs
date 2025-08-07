package ghayatech.ihubs.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.list
import ihubs.composeapp.generated.resources.search
import ghayatech.ihubs.ui.theme.AppColors
import ghayatech.ihubs.ui.theme.AppStrings
import ghayatech.ihubs.ui.theme.AppStringsProvider
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = AppStringsProvider.current().search_on_hubs,
    leadingIcon: Painter = painterResource(Res.drawable.search),
    trailingIcon: Painter = painterResource(Res.drawable.list),
    onTrailingIconClick: (() -> Unit)? = null,
    hasTrailing: Boolean = true,
    backgroundColor: Color = AppColors.Background,
    cornerRadius: Dp = 12.dp,
    fontSize: Int = 16,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth(),
        placeholder = {
            CText(
                placeholder,
                fontWeight = FontWeight.Bold,
                color = AppColors.TextSecondary
            )
        },
        leadingIcon = {
            Icon(
                leadingIcon,
                contentDescription = null,
                tint = AppColors.TextSecondary
            )
        },

        trailingIcon = {

            when {
                hasTrailing -> {
                    Icon(
                        painter = trailingIcon, contentDescription = null, modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(AppColors.White)
                            .padding(8.dp)
                            .clickable {
                                onTrailingIconClick?.let { it() }
                            }, tint = AppColors.TextSecondary
                    )
                }
            }

        },
        textStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = fontSize.sp
        ),
        singleLine = true,
        shape = RoundedCornerShape(cornerRadius),
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

        )
    )

}