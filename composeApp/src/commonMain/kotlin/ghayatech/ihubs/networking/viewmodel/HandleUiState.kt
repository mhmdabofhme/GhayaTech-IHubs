package ghayatech.ihubs.networking.viewmodel


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import ghayatech.ihubs.networking.models.BaseResponse
import ghayatech.ihubs.networking.models.MapData
import ghayatech.ihubs.networking.util.getMessage
import ghayatech.ihubs.ui.components.ErrorPage
import ghayatech.ihubs.ui.components.NoResult
import ghayatech.ihubs.ui.components.ProgressBar
import kotlinx.coroutines.flow.StateFlow


@Composable
fun <T> HandleUiState(
    state: UiState<BaseResponse<T>>?,
    onMessage: (String) -> Unit = {},
    onSuccess: (T) -> Unit = {},
    hasProgressBar:Boolean=true

) {
    when (state) {
        is UiState.Loading -> {
            if (hasProgressBar) {
                ProgressBar(state)
            }
        }

        is UiState.Success -> {
            val response = state.data
            LaunchedEffect(state) {
                if (response.status == 200) {
                    response.data?.let { onSuccess(it) }
                } else {
                    response.message?.let { onMessage(it) }
                }
            }
            if (response.data == null && response.status == 200) {
                NoResult()
            }
        }

        is UiState.Error -> {
            val errorMessage = state.error.getMessage()
            ErrorPage(errorMessage)
            LaunchedEffect(state) {
                onMessage(errorMessage)
            }
        }

        null -> Unit
    }
}
