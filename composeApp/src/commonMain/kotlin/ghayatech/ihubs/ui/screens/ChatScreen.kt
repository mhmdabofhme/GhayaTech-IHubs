package ghayatech.ihubs.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import ghayatech.ihubs.networking.models.WorkspaceDetails
import ghayatech.ihubs.networking.models.Message
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.chat
import ghayatech.ihubs.ui.components.ChatBubble
import ghayatech.ihubs.ui.components.ChatInput
import ghayatech.ihubs.ui.components.CustomTopBar
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.ui.theme.AppColors
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.getKoin
import org.koin.compose.rememberKoinInject


class ChatScreen(/*val secretaryId: Int*/) : Screen {
    @Composable
    override fun Content() {
//        val navigator = LocalNavigator.currentOrThrow
//        val settings: Settings = rememberKoinInject()
//        val viewModel: MainViewModel = rememberKoinInject()
//
////        val chatViewModel: ChatViewModel = getKoin().get()
////        val messages by chatViewModel.messages.collectAsState()
//
//
//
//        var messages by remember { mutableStateOf(listOf<Message>()) }
//        val messageState by viewModel.sendMessageState.collectAsState()
//
//        LaunchedEffect(Unit) {
////            viewModel.connectToConversation(conversationId)
//        }
//
//        DisposableEffect(Unit) {
//            onDispose {
//                viewModel.disconnect()
//            }
//        }
//
//
//        val user = loadUser(settings)
//        val userId = user?.id ?: -1
//
//        Box(Modifier.fillMaxSize()) {
//            Column {
//
//                CustomTopBar(
//                    modifier = Modifier.background(AppColors.itemBackground)
//                        .fillMaxWidth()
//                        .padding(top = 60.dp, bottom = 26.dp, start = 22.dp, end = 22.dp),
//                    title = stringResource(Res.string.chat),
//
//                    onBackClick = { navigator.pop() }
//                )
//
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .imePadding() // this is all you need to handle keyboard
//                        .background(AppColors.White),
//
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                ) {
//
//                    LazyColumn(
//                        modifier = Modifier
//                            .weight(1f)
//                            .padding(horizontal = 8.dp)
//                            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
//                        reverseLayout = true,
//                    ) {
//                        items(messages.reversed()) { message ->
//                            println("TAG TAGTAG" + "Message: ${message.body}, senderId: ${message.senderId}")
//                            ChatBubble(message,userId)
//                            Spacer(modifier = Modifier.height(4.dp))
//                        }
//                    }
//
//                    ChatInput(onSend = {
//                        println("TAG TAGTAG" + "Message:secretaryId ${secretaryId}, body: ${it}")
//
//                        viewModel.sendMessage(secretaryId, it,null)
////                        messages = messages + Message(it, isUser = true) + Message(
////                            "تم استلام الرسالة",
////                            isUser = false
////                        )
//                    })
//                }
//
//            }
//        }
    }


}

//data class Message(val text: String, val isUser: Boolean)

