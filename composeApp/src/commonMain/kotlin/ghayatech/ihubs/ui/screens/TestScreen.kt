package ghayatech.ihubs.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.ghayatech
import ihubs.composeapp.generated.resources.logo
import ihubs.composeapp.generated.resources.resource_default
import ihubs.composeapp.generated.resources.welcome
import org.jetbrains.compose.resources.painterResource

@Composable
fun Test() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(Res.drawable.welcome),
            contentDescription = "any",
            modifier = Modifier.fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(20.dp))
                .padding(bottom = 20.dp)
        )
        Image(
            painter = painterResource(Res.drawable.resource_default),
            contentDescription = "any",
            modifier = Modifier.size(80.dp)
                .clip(RoundedCornerShape(20.dp))
                .padding(bottom = 20.dp)
        )
    }
}