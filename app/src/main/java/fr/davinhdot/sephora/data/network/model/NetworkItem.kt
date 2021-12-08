package fr.davinhdot.sephora.data.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkItem(
    var id: String = "",

    val image: String?,

    val description: String?,

    val location: String?
)
