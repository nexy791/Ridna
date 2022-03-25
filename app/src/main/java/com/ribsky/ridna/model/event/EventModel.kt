package com.ribsky.ridna.model.event

import android.os.Parcelable
import com.cesarferreira.tempo.Tempo
import com.ribsky.domain.model.event.BaseEventModel
import com.ribsky.ridna.utils.ext.DateExtension.Companion.date
import kotlinx.parcelize.Parcelize

@Parcelize
class EventModel(
    override var name: String,
    override var description: String,
    override var date: Long,
    var addNotifications: Boolean
) : Parcelable, BaseEventModel {

    companion object {

        val empty = EventModel(
            name = "",
            description = "",
            date = Tempo.now.time.date,
            addNotifications = true
        )
    }
}
