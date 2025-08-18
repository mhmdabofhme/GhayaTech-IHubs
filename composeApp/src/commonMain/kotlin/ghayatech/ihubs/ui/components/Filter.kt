package ghayatech.ihubs.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.dropdown
import ghayatech.ihubs.ui.theme.AppColors
import org.jetbrains.compose.resources.painterResource

@Composable
fun Filter(
    dataList: List<String>,
    selectedItem: String? = null,
    placeholder: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .height(38.dp)
            .fillMaxWidth()
            .clickable { expanded = true }
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = AppColors.coolBackground, shape = RoundedCornerShape(5.dp))
                .padding(horizontal = 12.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            CText(
                text = selectedItem ?: placeholder,
                fontSize = 12.sp,
                color = AppColors.TextSecondary,
                modifier = Modifier.weight(1f)
            )
//            Spacer(modifier = Modifier.size(30.dp))
            Image(
                painterResource(Res.drawable.dropdown),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(IntrinsicSize.Min).background(color = AppColors.coolBackground)
        ) {
            dataList.forEach { option ->
                DropdownMenuItem(
                    text = { CText(option) },
                    onClick = {
                        onItemSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}