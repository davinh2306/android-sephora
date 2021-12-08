package fr.davinhdot.sephora.domain.repository

import fr.davinhdot.sephora.domain.entity.Item
import io.reactivex.Single

interface SephoraRepository {
    fun getAllItemsFromApi(): Single<List<Item>>
}