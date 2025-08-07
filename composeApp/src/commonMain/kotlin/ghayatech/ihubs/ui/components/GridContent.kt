package ghayatech.ihubs.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ihubs.composeapp.generated.resources.Res
import ghayatech.ihubs.ui.theme.AppColors
import org.jetbrains.compose.resources.stringResource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ihubs.composeapp.generated.resources.bold
import ihubs.composeapp.generated.resources.logo
import ghayatech.ihubs.networking.models.Service
import ghayatech.ihubs.networking.models.Workspace
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun GridContent(
    list: List<Workspace>,
    onItemClicked: (Workspace) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.wrapContentHeight().padding(horizontal = 14.dp)
    ) {
        items(list) { item ->
            Column(
                modifier = Modifier
                    .padding(5.dp)
//                    .size(125.dp)
                    .background(AppColors.itemBackground, shape = RoundedCornerShape(15.dp))
                    .clickable {
                        onItemClicked(item)
                    }
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                itemContent(item)
                 NetworkImage(
                    item.logo,
                    modifier = Modifier
                        .size(58.dp)
                        .clip(CircleShape),
                )
//                Image(
//                    /*painter = image ?:*/ painterResource(Res.drawable.logo),
//                    contentDescription = "Hub image",
//                    modifier = Modifier.size(67.dp).clip(CircleShape)
////                        .border(width = 1.dp, shape = CircleShape, color = AppColors.coolBackground)
//                        .background(AppColors.White)
//                        .padding(10.dp)
//
//                )
                Spacer(modifier = Modifier.size(9.dp))
                CText(
                    item.name,
                    fontFamily = Res.font.bold,
                    fontSize = 16.sp,
                )
            }
        }
    }
}
