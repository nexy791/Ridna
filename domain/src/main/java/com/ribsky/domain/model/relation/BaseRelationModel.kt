package com.ribsky.domain.model.relation

interface BaseRelationModel {

    val firstUser: AccountModel
    val secondUser: AccountModel
    var label: String
    var date: Long
    var banner: String
    var isDark: Boolean

    interface AccountModel {
        var name: String
        var image: String
    }
}
