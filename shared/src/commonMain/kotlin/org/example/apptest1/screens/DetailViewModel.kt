package org.example.apptest1.screens

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import org.example.apptest1.data.MuseumObject
import org.example.apptest1.data.MuseumRepository

class DetailViewModel(private val museumRepository: MuseumRepository) : ViewModel() {
    private val objectId = MutableStateFlow<Int?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    @NativeCoroutinesState
    val museumObject: StateFlow<MuseumObject?> = objectId
        .flatMapLatest {
            val id = it ?: return@flatMapLatest flowOf(null)
            museumRepository.getObjectById(id)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun setId(objectId: Int) {
        this.objectId.value = objectId
    }
}
