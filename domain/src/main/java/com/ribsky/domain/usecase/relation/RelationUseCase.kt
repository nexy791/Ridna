package com.ribsky.domain.usecase.relation

import com.ribsky.domain.model.relation.BaseRelationModel

interface RelationUseCase {
    suspend fun edit(relationModel: BaseRelationModel): Result<Unit>
    suspend fun get(): BaseRelationModel?
}

class RelationUseCaseImpl(
    private val editRelationUseCase: EditRelationUseCase,
    private val getRelationUseCase: GetRelationUseCase
) : RelationUseCase {

    override suspend fun edit(relationModel: BaseRelationModel) =
        editRelationUseCase.invoke(relationModel)

    override suspend fun get() = getRelationUseCase.invoke()
}
