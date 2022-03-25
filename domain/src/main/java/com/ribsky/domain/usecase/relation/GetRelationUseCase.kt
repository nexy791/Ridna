package com.ribsky.domain.usecase.relation

import com.ribsky.domain.model.relation.BaseRelationModel
import com.ribsky.domain.repository.RelationRepository

interface GetRelationUseCase {

    suspend fun invoke(): BaseRelationModel?
}

class GetRelationUseCaseImpl(
    private val relationRepository: RelationRepository
) : GetRelationUseCase {

    override suspend fun invoke(): BaseRelationModel? = relationRepository.getRelation()
}
