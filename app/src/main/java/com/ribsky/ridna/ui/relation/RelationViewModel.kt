package com.ribsky.ridna.ui.relation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.domain.model.relation.BaseRelationModel
import com.ribsky.domain.usecase.relation.GetRelationUseCase
import kotlinx.coroutines.launch

class RelationViewModel(
    private val getRelationUseCase: GetRelationUseCase
) : ViewModel() {

    private val _statusRelation: MutableLiveData<BaseRelationModel?> =
        MutableLiveData()
    val statusRelation: LiveData<BaseRelationModel?> get() = _statusRelation

    fun getRelation() {
        viewModelScope.launch {
            _statusRelation.value = getRelationUseCase.invoke()
        }
    }
}
