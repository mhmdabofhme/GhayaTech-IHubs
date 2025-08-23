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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import ghayatech.ihubs.networking.models.About
import ghayatech.ihubs.networking.models.MapData
import ghayatech.ihubs.networking.models.WorkspaceDetails
import ghayatech.ihubs.networking.viewmodel.HandleUiState
import ghayatech.ihubs.networking.viewmodel.MainViewModel
import ghayatech.ihubs.ui.components.CText
import ghayatech.ihubs.ui.components.CustomSnackbar
import ghayatech.ihubs.ui.components.CustomTopBar
import ghayatech.ihubs.ui.components.SocialMediaLogo
import ghayatech.ihubs.ui.theme.AppColors
import ghayatech.ihubs.ui.theme.AppStrings
import ghayatech.ihubs.ui.theme.AppStringsProvider
import ghayatech.ihubs.utils.Logger
import ghayatech.ihubs.utils.SocialOpener
import ihubs.composeapp.generated.resources.Res
import ihubs.composeapp.generated.resources.bold
import ihubs.composeapp.generated.resources.instagram
import ihubs.composeapp.generated.resources.linkedin
import ihubs.composeapp.generated.resources.normal
import ihubs.composeapp.generated.resources.star
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.rememberKoinInject

class AboutUsScreen : Screen {

    @Composable
    override fun Content() {
        // Dependencies injection using Koin
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: MainViewModel = rememberKoinInject()
        val socialOpener: SocialOpener = koinInject()
        val logger: Logger = rememberKoinInject()
        val strings = AppStringsProvider.current()

        // UI state management
        var snackbarMessage by rememberSaveable { mutableStateOf<String?>(null) }
        val aboutState by viewModel.aboutState.collectAsState()
        var aboutData by remember { mutableStateOf<About?>(null) }

        // Fetch data when the screen is first composed
        LaunchedEffect(Unit) {
            viewModel.getAbout()
        }

        Box(Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppColors.White)
                    .padding(top = 60.dp, start = 16.dp, end = 16.dp)
            ) {
                // Top app bar
                CustomTopBar(
                    title = strings.about_us,
                    onBackClick = { navigator.pop() }
                )

                // Main content
                AboutScreenContent(
                    aboutData = aboutData,
                    socialOpener = socialOpener,
                    logger = logger,
                    strings
                )
            }

            // Snackbar for displaying messages
            CustomSnackbar(
                message = snackbarMessage,
                onDismiss = { snackbarMessage = null },
                modifier = Modifier.align(Alignment.TopCenter)
            )

            // Handle different UI states from the ViewModel
            HandleUiState(
                state = aboutState,
                onMessage = { snackbarMessage = it },
                onSuccess = { data -> aboutData = data }
            )
        }
    }
}
@Composable
private fun AboutScreenContent(
    aboutData: About?,
    socialOpener: SocialOpener,
    logger: Logger,
    strings: AppStrings
) {
    if (aboutData == null) {
        // You might want to show a loading indicator here
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Spacer for top padding
        item { Spacer(modifier = Modifier.height(28.dp)) }

        // Divider line
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(AppColors.Primary)
            )
        }

        item { Spacer(modifier = Modifier.height(15.dp)) }

        // Section for informational items
        items(aboutData.info) { item ->
            AboutInfoItem(item)
        }

        // Another divider line
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(AppColors.Primary)
            )
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        // Section for contact links
        items(aboutData.contacts ?: emptyList()) { item ->
            AboutContactItem(item)
        }

        // Section for social media links
        item {
            aboutData.links?.let { links ->
                if (links.isNotEmpty()) {
                    AboutSocialMediaLinks(
                        links = links,
                        socialOpener = socialOpener,
                        logger = logger,
                        strings
                    )
                }
            }
        }
    }
}

/**
 * Composable function to display a single informational item with a star icon.
 */
@Composable
private fun AboutInfoItem(info: MapData) {
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
                text = info.key,
                color = AppColors.Black,
                fontFamily = Res.font.bold,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        CText(
            text = info.description,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            color = AppColors.Black,
            fontFamily = Res.font.normal,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

/**
 * Composable function to display a single contact item.
 */
@Composable
private fun AboutContactItem(contact: MapData) {
    Row(
        modifier = Modifier.wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CText(
            text = contact.key,
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 20.dp),
            color = AppColors.Primary,
            fontFamily = Res.font.bold,
            fontSize = 14.sp
        )
        CText(
            text = contact.description,
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 20.dp),
            color = AppColors.TextSecondary,
            fontFamily = Res.font.bold,
            fontSize = 14.sp
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
}

/**
 * Composable function to display a row of social media logos.
 */
@Composable
private fun AboutSocialMediaLinks(
    links: List<MapData>,
    socialOpener: SocialOpener,
    logger: Logger,
    strings: AppStrings
) {

    Row(
        modifier = Modifier
            .wrapContentSize()
            .padding(start = 20.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CText(
            text = strings.social_media,
            modifier = Modifier.wrapContentSize(),
            color = AppColors.Primary,
            fontFamily = Res.font.bold,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.width(16.dp))

        links.forEach { social ->
            SocialMediaLogo(social.key) {
                when (getPlatformName(social.key)) {
                    "instagram" -> {
                        logger.debug("TAG AboutUsScreen:", "Opening Instagram: ${social.description}")
                        socialOpener.openInstagram(social.description)
                    }
                    "linkedin" -> {
                        logger.debug("TAG AboutUsScreen:", "Opening LinkedIn: ${social.description}")
                        socialOpener.openLinkedIn(social.description)
                    }
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

/**
 * Helper function to normalize social media platform names.
 * This is now outside the class for better reusability.
 */
private fun getPlatformName(platformName: String): String {
    return when (platformName.trim().lowercase()) {
        "انستجرام", "instagram" -> "instagram"
        "لينكدين", "لينكد ان", "linkedin" -> "linkedin"
        else -> ""
    }
}