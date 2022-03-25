package com.ribsky.ridna.model.library

import androidx.recyclerview.widget.DiffUtil

data class LibraryModel(
    val title: String,
    val description: String,
    val link: String
) {

    companion object {

        object DiffCallback : DiffUtil.ItemCallback<LibraryModel>() {
            override fun areItemsTheSame(
                oldItem: LibraryModel,
                newItem: LibraryModel
            ): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: LibraryModel,
                newItem: LibraryModel
            ): Boolean =
                oldItem == newItem
        }

        val list = listOf(
            LibraryModel(
                title = "ViewBindingPropertyDelegate",
                description = "Licensed under the Apache License",
                link = "https://github.com/androidbroadcast/ViewBindingPropertyDelegate",
            ),
            LibraryModel(
                title = "Insetter",
                description = "Licensed under the Apache License",
                link = "https://github.com/chrisbanes/insetter",
            ),
            LibraryModel(
                title = "Koin",
                description = "Licensed under the Apache License",
                link = "https://github.com/InsertKoinIO/koin",
            ),
            LibraryModel(
                title = "CircleIndicator",
                description = "Licensed under the Apache License",
                link = "https://github.com/ongakuer/CircleIndicator",
            ),
            LibraryModel(
                title = "Tempo",
                description = "Licensed under the Apache License",
                link = "https://github.com/cesarferreira/tempo",
            ),
            LibraryModel(
                title = "Glide",
                description = "Licensed under BSD, part MIT and Apache 2.0",
                link = "https://github.com/bumptech/glide",
            ),
            LibraryModel(
                title = "Moshi",
                description = "Licensed under the Apache License",
                link = "https://github.com/square/moshi",
            ),
            LibraryModel(
                title = "Localization",
                description = "Licensed under the Apache License",
                link = "https://github.com/akexorcist/Localization",
            )
        )
    }
}
