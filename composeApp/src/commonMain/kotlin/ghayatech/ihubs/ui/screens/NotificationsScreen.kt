package ghayatech.ihubs.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import ghayatech.ihubs.networking.models.NotificationResponse
import ghayatech.ihubs.networking.viewmodel.HandleUiState
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.ui.components.CText
import ghayatech.ihubs.ui.components.CustomSnackbar
import ghayatech.ihubs.ui.components.CustomTopBar
import ghayatech.ihubs.ui.components.ExpandableText
import ghayatech.ihubs.ui.theme.AppColors
import ghayatech.ihubs.ui.theme.AppStringsProvider
import ghayatech.ihubs.utils.convertToRelativeTime
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.bold
import org.koin.compose.rememberKoinInject

class NotificationsScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val viewModel: MainViewModel = rememberKoinInject()
        val strings = AppStringsProvider.current()

        var snackbarMessage by rememberSaveable { mutableStateOf<String?>(null) }

        val notificationsState by viewModel.notificationsState.collectAsState()
        val notificationsList = remember { mutableStateListOf<NotificationResponse>() }
        var isRefreshing by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            viewModel.getNotifications()
        }

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
                viewModel.getNotifications()
            },
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .background(AppColors.White)
                    .padding(top = 60.dp, start = 16.dp, end = 16.dp)

            ) {
                CustomTopBar(title = strings.privacy_policy, onBackClick = {
                    navigator.pop()
                })

                Spacer(modifier = Modifier.size(24.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize().background(AppColors.White)

                ) {
                    items(notificationsList) { item ->

                        Column(
                            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                                .padding(vertical = 6.dp)
                                .shadow(
                                    elevation = 3.dp,
                                    shape = RoundedCornerShape(10.dp),
                                    clip = false
                                )
                                .clip(RoundedCornerShape(10.dp))
                                .background(AppColors.coolBackground)
                                .padding(12.dp)
                        ) {
                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.Bottom
                            ) {

                                CText(
                                    text = item.data.title,
                                    modifier = Modifier.weight(1F),
                                    color = AppColors.Secondary,
                                    fontFamily = Res.font.bold,
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.size(4.dp))

                                // TODO CHANGE TIME FORMAT
                                CText(
                                    text = convertToRelativeTime(item.createdAt, strings),
                                    modifier = Modifier.wrapContentWidth(),
                                    color = AppColors.Secondary,
                                    fontSize = 12.sp
                                )

                            }
                            Spacer(modifier = Modifier.size(4.dp))

                            ExpandableText(
                                text = item.data.body,
                                modifier = Modifier.fillMaxWidth()
//                                modifier = Modifier.weight(1f) // هذا يجعل ExpandableText يأخذ كل المساحة الأفقية المتبقية
                            )

                        }


                    }
                }
            }



            CustomSnackbar(
                message = snackbarMessage,
                onDismiss = { snackbarMessage = null },
                modifier = Modifier.align(Alignment.TopCenter)
            )

            HandleUiState(
                state = notificationsState,
                onMessage =
                    {
                        snackbarMessage = it
                    },
                onSuccess =
                    { data ->
                        notificationsList.clear()
                        notificationsList.addAll(data)
                    }
            )

        }

    }
}
