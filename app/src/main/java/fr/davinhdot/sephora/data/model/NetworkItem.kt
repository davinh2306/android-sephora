package fr.davinhdot.sephora.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkItem(
    var id: String = "",

    var image: String?,

    var description: String?,

    var location: String?
)
