package fr.davinhdot.sephora.data.network.api

import fr.davinhdot.sephora.data.network.model.NetworkItemList
import io.reactivex.Single
import retrofit2.http.GET

interface SephoraApi {
    @GET("items.json")
    fun getAllItems(): Single<NetworkItemList>
}