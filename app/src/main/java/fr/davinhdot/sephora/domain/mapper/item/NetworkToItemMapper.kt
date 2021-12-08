package fr.davinhdot.sephora.domain.mapper.item

import fr.davinhdot.sephora.data.network.model.NetworkItem
import fr.davinhdot.sephora.domain.entity.Item
import fr.davinhdot.sephora.domain.mapper.Mapper

class NetworkToItemMapper : Mapper<NetworkItem, Item> {

    override fun map(input: NetworkItem, vararg params: String): Item {
        return Item(
            input.id,
            input.image ?: "",
            input.description ?: "",
            input.location ?: "",
        )
    }
}