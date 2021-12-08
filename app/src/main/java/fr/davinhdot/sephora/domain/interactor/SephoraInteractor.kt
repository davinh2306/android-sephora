package fr.davinhdot.sephora.domain.interactor

import fr.davinhdot.sephora.domain.entity.Item
import fr.davinhdot.sephora.domain.repository.SephoraRepository
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SephoraInteractor @Inject constructor(private val sephoraRepository: SephoraRepository) {
    fun getAllItemsFromApi(): Single<List<Item>> {
        Timber.d("getAllItemsFromApi")

        return sephoraRepository.getAllItemsFromApi()
            .doOnSuccess { items ->
                Timber.d("getAllItemsFromApi - doOnSuccess - $items")
            }
    }
}