package com.ribsky.data.model.relation

import com.ribsky.domain.model.relation.BaseRelationModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RelationApiModel(
    override var firstUser: AccountApiModel,
    override var secondUser: AccountApiModel,
    override var label: String,
    override var date: Long,
    override var banner: String,
    override var isDark: Boolean
) : BaseRelationModel {

    @JsonClass(generateAdapter = true)
    class AccountApiModel(
        override var name: String,
        override var image: String
    ) : BaseRelationModel.AccountModel {

        constructor(model: BaseRelationModel.AccountModel) : this(
            model.name, model.image
        )
    }

    constructor(model: BaseRelationModel) : this(
        AccountApiModel(model.firstUser),
        AccountApiModel(model.secondUser),
        model.label,
        model.date,
        model.banner,
        model.isDark
    )
}
