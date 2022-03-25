package com.ribsky.ridna.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.domain.usecase.delete.DeleteAllUseCase
import com.ribsky.ridna.utils.Event
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val deleteAllUseCase: DeleteAllUseCase
) : ViewModel() {

    private val _statusDelete: Event<Boolean> = Event()
    val statusDelete: LiveData<Boolean> get() = _statusDelete

    fun deleteAll() {
        viewModelScope.launch {
            _statusDelete.value = deleteAllUseCase.invoke()
        }
    }
}
