package ghayatech.ihubs.networking.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import ghayatech.ihubs.networking.models.ListBaseResponse
import ghayatech.ihubs.networking.util.getMessage
import ghayatech.ihubs.ui.components.ErrorPage
import ghayatech.ihubs.ui.components.NoResult
import ghayatech.ihubs.ui.components.ProgressBar


@Composable
fun <T> HandleUiState(
    state: UiState<ListBaseResponse<T>>?,
    onMessage: (String) -> Unit = {},
    onSuccess: (List<T>) -> Unit = {},
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
                    onSuccess(response.data)

                } else {
                    response.message?.let { onMessage(it) }
                }
            }

            if (response.data.isEmpty()) {
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
