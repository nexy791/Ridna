package com.ribsky.ridna.ui.dialog.relation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.domain.model.relation.BaseRelationModel
import com.ribsky.domain.usecase.file.CreateFileUseCase
import com.ribsky.domain.usecase.relation.RelationUseCase
import com.ribsky.ridna.model.relation.RelationModel
import com.ribsky.ridna.utils.Event
import kotlinx.coroutines.launch

class EditRelationViewModel(
    private val relationUseCase: RelationUseCase,
    private val createFileUseCase: CreateFileUseCase
) : ViewModel() {

    private val _statusRelation: MutableLiveData<BaseRelationModel?> = MutableLiveData()
    val statusRelation: LiveData<BaseRelationModel?> get() = _statusRelation

    private val _statusEditRelation: Event<Result<Unit?>> = Event()
    val statusEditRelation: LiveData<Result<Unit?>> get() = _statusEditRelation

    fun getRelation() {
        viewModelScope.launch {
            _statusRelation.value = relationUseCase.get()
        }
    }

    fun editRelation(relationModel: RelationModel) {

        viewModelScope.launch {
            runCatching {

                with(relationModel) {

                    firstUser.tempUriOfImage?.let { uri ->
                        firstUser.image = createFileUseCase.invoke(uri.toString()).getOrThrow()
                    }

                    secondUser.tempUriOfImage?.let { uri ->
                        secondUser.image =
                            createFileUseCase.invoke(uri.toString()).getOrThrow()
                    }

                    tempUriOfBanner?.let { uri ->
                        banner = createFileUseCase.invoke(uri.toString()).getOrThrow()
                    }
                    _statusEditRelation.value = relationUseCase.edit(relationModel)
                }
                getRelation()
            }
        }
    }
}
