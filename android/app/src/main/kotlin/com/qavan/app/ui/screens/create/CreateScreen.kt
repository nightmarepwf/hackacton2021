package com.qavan.app.ui.screens.create

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowColumn
import com.google.accompanist.flowlayout.FlowRow
import com.qavan.app.R
import com.qavan.app.compose.AppTheme
import com.qavan.app.compose.buttons.AppButtonAction
import com.qavan.app.compose.buttons.AppButtonOutlinedAction
import com.qavan.app.compose.input.AppOutlinedTextField
import com.qavan.app.compose.text.AppTextBody
import com.qavan.app.compose.text.AppTextCaption
import com.qavan.app.data.model.TagX
import com.qavan.app.extensions.EMPTY
import com.qavan.app.ui.items.ItemTag
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun CreateScreen(
    state: CreateContract.CreateState,
    title: String = "Title",
    description: String = "Description",
    time: Long = 1629496957406L,
    tags: List<TagX> = emptyList(),
    onTitleChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
    onAddTagClicked: (TagX) -> Unit = {},
    onRemoveTagClicked: (TagX) -> Unit = {},
    onDateClicked: () -> Unit = {},
    onBackClicked: () -> Unit = {},
) {
    val dateTime = if (time == -1L)
        "дд.мм.гггг"
    else
        SimpleDateFormat("dd.MM.yyyy").format(Date(time))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        AppTextBody(
            text = "Название",
            color = MaterialTheme.colors.primary,
        )
        AppOutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
        )
        Spacer(modifier = Modifier.height(8.dp))
        AppTextBody(
            text = "Описание",
            color = MaterialTheme.colors.primary,
        )
        AppOutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
        )
        Spacer(modifier = Modifier.height(8.dp))
        AppTextBody(
            text = "Дата",
            color = MaterialTheme.colors.primary,
        )
        AppOutlinedTextField(
            readOnly = true,
            value = dateTime,
            trailingIcon = {
               Icon(
                   modifier = Modifier
                       .wrapContentWidth()
                       .padding(end = 12.dp)
                       .clip(RoundedCornerShape(50))
                       .clickable {
                           onDateClicked()
                       }
                       .padding(8.dp),
                   imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_calendar_today_24),
                   contentDescription = "Выбрать",
                   tint = MaterialTheme.colors.primary,
               )
            },
            onValueChange = {},
        )
        Spacer(modifier = Modifier.height(8.dp))
        AppTextBody(
            text = "Теги",
            color = MaterialTheme.colors.primary,
        )
        var tagName by remember { mutableStateOf(String.EMPTY) }
        AppOutlinedTextField(
            value = tagName,
            placeholder = "Введите название тега",
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(end = 12.dp)
                        .clip(RoundedCornerShape(50))
                        .clickable {
                            if (tagName.isNotBlank()) {
                                onAddTagClicked(TagX(name = tagName))
                                tagName = String.EMPTY
                            }
                        }
                        .padding(8.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_add_24),
                    contentDescription = "Добавить",
                    tint = MaterialTheme.colors.primary,
                )
            },
            onValueChange = {
                tagName = it
            },
        )
        if (tags.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                mainAxisSpacing = 8.dp,
                crossAxisSpacing = 6.dp,
            ) {
                tags.forEach { tag ->
                    ItemTag(
                        modifier = Modifier.wrapContentWidth(),
                        tag = tag,
                        onRemoveIconClick = onRemoveTagClicked,
                    )
                }
            }
        }
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

@Preview
@Composable
private fun CreateScreenPreview() {
    AppTheme(false) {
        var title by remember { mutableStateOf("Title") }
        var description by remember { mutableStateOf("Description") }
        CreateScreen(
            state = CreateContract.CreateState.Idle,
            title = title,
            description = description,
            tags = (1..5).map { TagX(name = "Tag$it") },
            onTitleChange = { title = it },
            onDescriptionChange = { description = it },
        )
    }
}