package com.ribsky.domain.usecase.relation

import com.ribsky.domain.model.relation.BaseRelationModel
import com.ribsky.domain.repository.RelationRepository

interface EditRelationUseCase {

    suspend fun invoke(relationModel: BaseRelationModel): Result<Unit>
}

class EditRelationUseCaseImpl(
    private val relationRepository: RelationRepository
) : EditRelationUseCase {

    override suspend fun invoke(relationModel: BaseRelationModel): Result<Unit> =
        relationRepository.editRelation(relationModel)
}
