package com.gmail.orlandroyd.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.orlandroyd.myapplication.model.Character
import com.gmail.orlandroyd.myapplication.networking.DataState
import com.gmail.orlandroyd.myapplication.repository.CharacterRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject
constructor(
    private val repository: CharacterRepo
) : ViewModel() {

    private val _characters = Channel<DataState<List<Character>>>(Channel.BUFFERED)
    val characters = _characters.receiveAsFlow()

    init {
        viewModelScope.launch {
            repository.fetchCharacters()
                .catch { e ->
                    _characters.send(DataState.error(e.toString()))
                }
                .collect {
                    _characters.send(it)
                }
        }
    }

}