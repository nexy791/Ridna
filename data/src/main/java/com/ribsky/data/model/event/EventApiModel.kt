package com.ribsky.data.model.event

import androidx.annotation.ColorRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ribsky.domain.model.event.BaseEventModel

@Entity
data class EventApiModel(
    override var name: String,
    override var description: String,
    override var date: Long,
    var id: String,
    @ColorRes var color: Int = 0
) : BaseEventModel {

    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0

    constructor(eventModel: BaseEventModel, id: String, color: Int) : this(
        eventModel.name,
        eventModel.description,
        eventModel.date,
        id,
        color
    )
}
