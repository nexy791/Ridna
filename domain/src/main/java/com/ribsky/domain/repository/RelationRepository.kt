package com.ribsky.domain.repository

import com.ribsky.domain.model.relation.BaseRelationModel

interface RelationRepository {

    suspend fun editRelation(relationModel: BaseRelationModel): Result<Unit>

    suspend fun getRelation(): BaseRelationModel?

    suspend fun deleteRelation(): Boolean
}
