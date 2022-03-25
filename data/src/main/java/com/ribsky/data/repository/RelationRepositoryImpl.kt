package com.ribsky.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.ribsky.data.model.relation.RelationApiModel
import com.ribsky.domain.model.relation.BaseRelationModel
import com.ribsky.domain.repository.RelationRepository
import com.squareup.moshi.Moshi

class RelationRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    moshi: Moshi
) : RelationRepository {

    private val adapter = moshi.adapter(RelationApiModel::class.java)

    override suspend fun editRelation(relationModel: BaseRelationModel): Result<Unit> {
        return runCatching {
            val model = RelationApiModel(relationModel)
            val json = adapter.toJson(model)
            sharedPreferences.edit {
                putString(KEY_RELATION, json)
            }
        }
    }

    override suspend fun getRelation(): BaseRelationModel? {
        return runCatching {
            val json = sharedPreferences.getString(KEY_RELATION, null)
            if (json != null) {
                adapter.fromJson(json)
            } else {
                null
            }
        }.getOrNull()
    }

    override suspend fun deleteRelation(): Boolean = runCatching {
        sharedPreferences.edit {
            putString(KEY_RELATION, "")
        }
    }.isSuccess

    companion object {
        private const val KEY_RELATION = "KEY_RELATION"
    }
}
