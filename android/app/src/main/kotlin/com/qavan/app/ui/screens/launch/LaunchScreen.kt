package com.qavan.app.ui.screens.launch

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qavan.app.compose.AppTheme
import com.qavan.app.compose.buttons.AppButton
import com.qavan.app.compose.text.AppTextH2


@Composable
fun LaunchScreen(
    state: LaunchContract.LaunchState,
    onLoginAsDepartmentSpecialistClicked: () -> Unit = {},
    onLoginAsBloggerClicked: () -> Unit = {},
) {
    when(state) {
        LaunchContract.LaunchState.Idle -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                ) {
                    AppTextH2(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Войти как",
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    AppButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Специалист департамента",
                        onClick = {
                            onLoginAsDepartmentSpecialistClicked()
                        },
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    AppButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Блогер",
                        onClick = {
                            onLoginAsBloggerClicked()
                        },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun FirstLaunchScreenPreview() {
    AppTheme(true) {
        LaunchScreen(
            state = LaunchContract.LaunchState.Idle,
        )
    }
}