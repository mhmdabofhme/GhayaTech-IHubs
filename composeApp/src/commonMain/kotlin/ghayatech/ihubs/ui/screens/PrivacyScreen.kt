package ghayatech.ihubs.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import ghayatech.ihubs.networking.models.Workspace
import ghayatech.ihubs.networking.viewmodel.HandleUiState
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.ui.components.CText
import ghayatech.ihubs.ui.components.CustomTopBar
import ghayatech.ihubs.ui.components.ExpandableText
import ghayatech.ihubs.ui.theme.AppColors
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.bold
import ihubs.composeapp.generated.resources.light
import ihubs.composeapp.generated.resources.normal
import ihubs.composeapp.generated.resources.privacy_policy
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.rememberKoinInject

class PrivacyScreen() : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val settings: Settings = rememberKoinInject()
        val viewModel: MainViewModel = rememberKoinInject()


        var snackbarMessage by rememberSaveable { mutableStateOf<String?>(null) }

        val privacyState by viewModel.bookingState.collectAsState()
        val privacyList = remember {
            mutableStateListOf<privacy>(

            )
        }


        Box(Modifier.fillMaxSize()) {

            val listSt = listOf<String>("rowrowr owrow rowro ", "hello hell o hello hello h d 213 12 s ad as 123 ", "Mohammed")

            val list = listOf<privacy>(
                privacy("title", listSt),
                privacy("title", listSt),
                privacy("title", listSt),
                privacy("title", listSt),

                )
            privacyList.addAll(list)
            Column(
                modifier = Modifier.fillMaxSize()
                    .background(AppColors.White)
                    .padding(top = 60.dp, start = 16.dp, end = 16.dp)

            ) {
                CustomTopBar(title = stringResource(Res.string.privacy_policy), onBackClick = {
                    navigator.pop()
                })

                Spacer(modifier = Modifier.size(24.dp))


                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(privacyList) { item ->

                        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 7.dp)) {
                            CText(
                                text = item.title,
                                modifier = Modifier.fillMaxWidth().padding(start = 20.dp),
                                color = AppColors.Black,
                                fontFamily = Res.font.bold,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.size(4.dp))
//                            for (text in item.body) {
//                                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
//                                    Box(
//                                        modifier = Modifier.size(4.dp)
//                                            .background(AppColors.Secondary, shape = CircleShape)
//                                    )
//                                    Spacer(modifier = Modifier.size(14.dp))
//                                    ExpandableText(
//                                        text = text,
//                                        modifier = Modifier.weight(1F),
////                                        color = AppColors.Secondary,
////                                        fontFamily = Res.font.normal,
////                                        fontSize = 14.sp
//                                    )
//
//                                }
//                            }

                            for (text in item.body) {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier.size(4.dp)
                                            .background(AppColors.Secondary, shape = CircleShape)
                                    )
                                    Spacer(modifier = Modifier.size(14.dp))

                                    CText(
                                        text = text,
                                        modifier = Modifier.fillMaxWidth(),
                                        color = AppColors.Secondary,
                                        fontFamily = Res.font.normal,
                                        fontSize = 14.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.size(14.dp))

                            Box(
                                modifier = Modifier.fillMaxWidth().height(2.dp)
                                    .padding(horizontal = 8.dp)
                                    .background(AppColors.Secondary)
                                    .padding(16.dp)
                            )
                            Spacer(modifier = Modifier.size(12.dp))
                        }

                    }
                }

            }

        }

        HandleUiState(
            state = privacyState,
            onMessage =
                {
                    snackbarMessage = it
                },
            onSuccess =
                { data ->
//                    privacyList.addAll(data)
                }
        )

    }
}

private data class privacy(
    val title: String,
    val body: List<String>
)