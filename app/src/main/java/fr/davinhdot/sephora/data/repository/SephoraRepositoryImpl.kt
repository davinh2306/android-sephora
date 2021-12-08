package fr.davinhdot.sephora.data.repository

import fr.davinhdot.sephora.data.network.api.SephoraApi
import fr.davinhdot.sephora.data.network.model.NetworkItem
import fr.davinhdot.sephora.domain.entity.Item
import fr.davinhdot.sephora.domain.mapper.Mapper
import fr.davinhdot.sephora.domain.repository.SephoraRepository
import fr.davinhdot.sephora.utils.mapNetworkErrors
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class SephoraRepositoryImpl @Inject constructor(
    private val sephoraApi: SephoraApi,
    private val networkToItemMapper: Mapper<NetworkItem, Item>
) : SephoraRepository {

    override fun getAllItemsFromApi(): Single<List<Item>> {
        Timber.d("getAllItemsFromApi")

        return sephoraApi.getAllItems()
            .map {
                mapNetworkToItems(it.items)
            }
            .mapNetworkErrors()
    }


    /****************** map *******************/

    private fun mapNetworkToItems(networkItemList: List<NetworkItem>): List<Item> {
        Timber.d("mapNetworkToItems")

        return networkItemList.map { networkItem ->
            networkToItemMapper.map(networkItem)
        }
    }
}