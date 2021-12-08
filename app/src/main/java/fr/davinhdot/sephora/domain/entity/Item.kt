package fr.davinhdot.sephora.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val id: String,

    val image: String,

    val description: String,

    val location: String
) : Parcelable