package com.qavan.app.ui.screens.create

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qavan.app.compose.AppTheme
import com.qavan.app.compose.buttons.AppButtonAction
import com.qavan.app.compose.buttons.AppButtonOutlinedAction
import com.qavan.app.compose.input.AppOutlinedTextField
import com.qavan.app.compose.text.AppTextBody
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun CreateScreen(
    state: CreateContract.CreateState,
    title: String = "Title",
    description: String = "Description",
    time: Long = 1629496957406L,
    onTitleChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
    onDateClicked: () -> Unit = {},
    onBackClicked: () -> Unit = {},
) {
    when(state) {
        CreateContract.CreateState.Idle -> {
            val dateTime = if (time == -1L)
                "Не выбрана"
            else
                SimpleDateFormat("dd.MM.yyyy").format(Date(time))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                AppOutlinedTextField(
                    label = "Название",
                    placeholder = "Название",
                    value = title,
                    onValueChange = onTitleChange,
                )
                Spacer(modifier = Modifier.height(8.dp))
                AppOutlinedTextField(
                    label = "Описание",
                    placeholder = "Описание",
                    value = description,
                    onValueChange = onDescriptionChange,
                )
                Spacer(modifier = Modifier.height(8.dp))
                AppOutlinedTextField(
                    label = "Дата",
                    placeholder = "Дата",
                    readOnly = true,
                    value = dateTime,
                    trailingIcon = {
                       AppTextBody(
                           modifier = Modifier
                               .wrapContentWidth()
                               .padding(end = 12.dp)
                               .clip(RoundedCornerShape(16.dp))
                               .clickable {
                                   onDateClicked()
                               }
                               .padding(8.dp),
                           text = "Выбрать",
                       )
                    },
                    onValueChange = {},
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    AppButtonOutlinedAction(
                        modifier = Modifier
                            .wrapContentWidth()
                            .weight(1f),
                        text = "Назад к списку",
                        onClick = onBackClicked,
                    )
                    AppButtonAction(
                        modifier = Modifier
                            .wrapContentWidth()
                            .weight(1f),
                        text = "Далее",
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CreateScreenPreview() {
    AppTheme(true) {
        var title by remember { mutableStateOf("Title") }
        var description by remember { mutableStateOf("Description") }
        CreateScreen(
            state = CreateContract.CreateState.Idle,
            title = title,
            description = description,
            onTitleChange = { title = it },
            onDescriptionChange = { description = it },
        )
    }
}