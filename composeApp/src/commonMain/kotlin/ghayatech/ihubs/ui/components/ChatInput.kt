package ghayatech.ihubs.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.add_image
import ihubs.composeapp.generated.resources.link
import ihubs.composeapp.generated.resources.send
import ihubs.composeapp.generated.resources.start_chatting
import ghayatech.ihubs.ui.theme.AppColors
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun ChatInput(modifier: Modifier=Modifier ,onSend: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth()
//            .height(90.dp)
            .background(
                AppColors.Background,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {


        Row(
            modifier = Modifier.wrapContentSize().padding(top = 8.dp,bottom = 8.dp, start = 8.dp)
                .background(AppColors.chatBackground, shape = RoundedCornerShape(48.dp))
                .height(48.dp)
        ) {

            IconButton(onClick = { /* Attach */ }) {
                Icon(
                    painterResource(Res.drawable.link),
                    contentDescription = "link",
                    tint = AppColors.Secondary
                )
            }

            IconButton(onClick = { /* add_image */ }) {
                Icon(
                    painterResource(Res.drawable.add_image),
                    contentDescription = "add_image",
                    tint = AppColors.Secondary
                )
            }

        }

        BasicTextField(
            value = text,
            onValueChange = { text = it },
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = AppColors.chatText // أو AppColors.TextPrimary حسب حالتك
            ),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp, vertical = 10.dp)
                .height(48.dp)
                .clip(RoundedCornerShape(48.dp))
                .background(AppColors.chatBackground),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()

                ) {
                    Spacer(modifier = Modifier.size(10.dp))

                    if (text.isEmpty()) {
                        CText(
                            text = stringResource(Res.string.start_chatting),
                            fontSize = 14.sp,
                            color = AppColors.chatText
                        )
                    }
                    innerTextField()
                    Spacer(modifier = Modifier.weight(1f))

                    CIcon(
                        painterResource(Res.drawable.send),
                        contentDescription = "send",
                        onClick = {
                            if (text.isNotBlank()) {
                                onSend(text)
                                text = ""
                            }
                        },
                        background = AppColors.Secondary,
                        tint = Color.White,
                        size = 38.dp, modifier = Modifier.rotate(180F)
                    )

                    Spacer(modifier = Modifier.size(10.dp))

                }
            }
        )


    }
}
