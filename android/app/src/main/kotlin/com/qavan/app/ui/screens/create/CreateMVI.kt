package com.qavan.app.ui.screens.create

import com.qavan.app.base.mvi.MVI
import com.qavan.app.data.model.TagX
import com.qavan.app.extensions.EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateMVI @Inject constructor(

): MVI<CreateContract.Event, CreateContract.State, CreateContract.Effect>() {

    private val _title by lazy { MutableStateFlow(String.EMPTY) }
    val title by lazy { _title.asStateFlow() }

    private val _description by lazy { MutableStateFlow(String.EMPTY) }
    val description by lazy { _description.asStateFlow() }

    private val _time by lazy { MutableStateFlow(-1L) }
    val time by lazy { _time.asStateFlow() }

    private val _tags by lazy { MutableStateFlow(emptyList<TagX>()) }
    val tags by lazy { _tags.asStateFlow() }

    override fun createInitialState(): CreateContract.State {
        return CreateContract.State(
            CreateContract.CreateState.Idle
        )
    }

    override fun handleEvent(event: CreateContract.Event) {
        when(event) {
            is CreateContract.Event.SetTitle -> {
                _title.value = event.title
            }
            is CreateContract.Event.SetDescription -> {
                _description.value = event.description
            }
            is CreateContract.Event.SetDate -> {
                _time.value = event.time
            }
            is CreateContract.Event.AddTag -> {
                _tags.value += event.tag
            }
            is CreateContract.Event.RemoveTag -> {
                _tags.value -= event.tag
            }
        }
    }

}