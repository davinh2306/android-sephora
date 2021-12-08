package fr.davinhdot.sephora.data.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkItemList(
    val items: List<NetworkItem>
)
