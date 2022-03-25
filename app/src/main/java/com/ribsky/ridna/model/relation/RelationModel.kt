package com.ribsky.ridna.model.relation

import android.net.Uri
import android.os.Parcelable
import com.ribsky.domain.model.relation.BaseRelationModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class RelationModel(
    override var firstUser: AccountModel,
    override var secondUser: AccountModel,
    override var label: String,
    override var date: Long,
    override var banner: String,
    override var isDark: Boolean,
    var tempUriOfBanner: Uri? = null
) : BaseRelationModel, Parcelable {

    @Parcelize
    data class AccountModel(
        override var name: String,
        override var image: String,
        var tempUriOfImage: Uri? = null
    ) : BaseRelationModel.AccountModel, Parcelable

    companion object {

        val empty
            get() = RelationModel(
                firstUser = AccountModel(name = "", image = "", tempUriOfImage = null),
                secondUser = AccountModel(name = "", image = "", tempUriOfImage = null),
                label = "",
                date = 0L,
                banner = "",
                tempUriOfBanner = null,
                isDark = false
            )
    }
}
