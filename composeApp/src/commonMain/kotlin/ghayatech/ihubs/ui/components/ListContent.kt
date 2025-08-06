package ghayatech.ihubs.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.back
import ghayatech.ihubs.networking.models.Workspace
import ghayatech.ihubs.ui.theme.AppColors
import org.jetbrains.compose.resources.painterResource

@Composable
fun ListContent(
    list: List<Workspace>,
    onItemClicked: (Workspace) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxHeight().padding(bottom = 70.dp)) {
        items(list) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(AppColors.itemBackground, RoundedCornerShape(15.dp))
                    .clickable { onItemClicked(item) }
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                NetworkImage(
                    item.logo,
                    modifier = Modifier
                        .size(58.dp)
                        .clip(RoundedCornerShape(15.dp)),
                )
                Spacer(modifier = Modifier.size(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    CText(item.name, fontWeight = FontWeight.Bold)
                    CText(item.description ?: "", fontSize = 10.sp)
                }

                Icon(
                    painter = painterResource(Res.drawable.back),
                    modifier = Modifier.rotate(180F),
                    contentDescription = "go", tint = AppColors.TextSecondary
                )

            }
        }
    }
}
