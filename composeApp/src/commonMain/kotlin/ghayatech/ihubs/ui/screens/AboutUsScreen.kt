package ghayatech.ihubs.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import ghayatech.ihubs.networking.models.About
import ghayatech.ihubs.networking.models.WorkspaceDetails
import ghayatech.ihubs.networking.viewmodel.HandleUiState
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.ui.components.CText
import ghayatech.ihubs.ui.components.CustomTopBar
import ghayatech.ihubs.ui.components.SocialMediaLogo
import ghayatech.ihubs.ui.theme.AppColors
import ghayatech.ihubs.ui.theme.AppStringsProvider
import ghayatech.ihubs.utils.Logger
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.bold
import ihubs.composeapp.generated.resources.normal
import ihubs.composeapp.generated.resources.star
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.rememberKoinInject

class AboutUsScreen() : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: MainViewModel = rememberKoinInject()
        val logger: Logger = rememberKoinInject()
        val tag = "TAG AboutUsScreen:"
        val strings = AppStringsProvider.current()


        var snackbarMessage by rememberSaveable { mutableStateOf<String?>(null) }

        val aboutState by viewModel.aboutState.collectAsState()
        val about = remember { mutableStateOf<About?>(null) }


//        val privacyList = remember {
//            mutableStateListOf<privacy>(
//
//            )
//        }

        LaunchedEffect(Unit) {
            viewModel.getAbout()
        }


        Box(Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier.fillMaxSize()
                    .background(AppColors.White)
                    .padding(top = 60.dp, start = 16.dp, end = 16.dp)

            ) {
                CustomTopBar(title = strings.privacy_policy, onBackClick = {
                    navigator.pop()
                })

                Spacer(modifier = Modifier.size(28.dp))

                Box(
                    modifier = Modifier.fillMaxWidth().height(2.dp)
                        .padding(horizontal = 8.dp)
                        .background(AppColors.Primary)
                        .padding(16.dp)
                )

                Spacer(modifier = Modifier.size(15.dp))



                if (about.value != null) {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        // الجزء الأول: معلومات عن التطبيق (info)
                        items(about.value?.info ?: emptyList()) { item ->
                            Column(modifier = Modifier.fillMaxWidth()) {

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Image(
                                        painter = painterResource(Res.drawable.star),
                                        contentDescription = "star",
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    CText(
                                        text = item.key,
                                        color = AppColors.Black,
                                        fontFamily = Res.font.bold,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }
                                Spacer(modifier = Modifier.size(14.dp))
                                CText(
                                    text = item.description,
                                    modifier = Modifier.fillMaxWidth().padding(start = 20.dp),
                                    color = AppColors.Black,
                                    fontFamily = Res.font.normal,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.size(12.dp))
                            }
                        }

                        // الجزء الثاني: الخط الفاصل
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp)
                                    .padding(horizontal = 8.dp)
                                    .background(AppColors.Primary)
                            )
                            Spacer(modifier = Modifier.size(16.dp))
                        }

                        // الجزء الثالث: روابط التواصل (links)

                        items(about.value?.contacts ?: emptyList()) { item ->
                            Row(modifier = Modifier.wrapContentSize()) {

                                
                                CText(
                                    text = item.key,
                                    modifier = Modifier.wrapContentSize().padding(start = 20.dp),
                                    color = AppColors.Primary,
                                    fontFamily = Res.font.bold,
                                    fontSize = 14.sp
                                )

                                CText(
                                    text = item.description,
                                    modifier = Modifier.wrapContentSize().padding(start = 20.dp),
                                    color = AppColors.TextSecondary,
                                    fontFamily = Res.font.bold,
                                    fontSize = 14.sp
                                )

                            }
                        }

                        if (about.value!!.links != null) {

                            item {
                                Row(
                                    modifier = Modifier.wrapContentSize(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CText(
                                        text = strings.social_media,
                                        modifier = Modifier.wrapContentSize()
                                            .padding(start = 20.dp),
                                        color = AppColors.Primary,
                                        fontFamily = Res.font.bold,
                                        fontSize = 14.sp
                                    )

                                    for (social in about.value!!.links!!) {
                                        SocialMediaLogo(social.key) {}
                                        Spacer(modifier = Modifier.size(8.dp))
                                    }

                                }
                            }

                        }


                    }
                }
            }



            HandleUiState(
                state = aboutState,
                onMessage =
                    {
                        snackbarMessage = it

                    },
                onSuccess =
                    { data ->
                        about.value = data
                    }
            )

        }
    }
}


