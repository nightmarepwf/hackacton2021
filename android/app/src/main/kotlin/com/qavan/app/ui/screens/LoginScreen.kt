package com.qavan.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.qavan.app.base.mvi.MVI
import com.qavan.app.base.mvi.UiEffect
import com.qavan.app.base.mvi.UiEvent
import com.qavan.app.base.mvi.UiState
import com.qavan.app.compose.AppTheme
import com.qavan.app.compose.LoadingBox
import com.qavan.app.compose.buttons.AppButton
import com.qavan.app.compose.input.AppOutlinedTextField
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@Composable
fun LoginScreen(
    state: LoginContract.LoginState,
    onLogin: () -> Unit = {},
) {
    when(state) {
        LoginContract.LoginState.Idle -> {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                AppOutlinedTextField(
                    value = "",
                    onValueChange =  {

                    })
                AppOutlinedTextField(
                    value = "",
                    onValueChange =  {

                    })
                AppButton(
                    text = "Войти",
                    onClick = onLogin,
                )
            }
        }
        LoginContract.LoginState.Loading -> {
            LoadingBox()
        }
    }
}

@HiltViewModel
class LoginMVI @Inject constructor(

): MVI<LoginContract.Event, LoginContract.State, LoginContract.Effect>() {

    override fun createInitialState(): LoginContract.State {
        return LoginContract.State(
            LoginContract.LoginState.Idle
        )
    }

    override fun handleEvent(event: LoginContract.Event) {
        when(event) {

        }
    }

}

class LoginContract {

    sealed class Event: UiEvent {
        object Login: Event()
    }

    data class State(
        val state: LoginState,
    ): UiState

    sealed class LoginState {
        object Idle: LoginState()
        object Loading : LoginState()
    }

    sealed class Effect: UiEffect
}

@Preview
@Composable
private fun LoginScreenPreview() {
    AppTheme(true) {
        LoginScreen(
            state = LoginContract.LoginState.Idle,
        )
    }
}