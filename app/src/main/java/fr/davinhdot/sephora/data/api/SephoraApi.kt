package fr.davinhdot.sephora.data.api

import fr.davinhdot.sephora.data.model.NetworkItem
import io.reactivex.Single
import retrofit2.http.GET

interface SephoraApi {
    @GET("items.json")
    fun getAllItems(): Single<List<NetworkItem>>
}