package fr.davinhdot.sephora.ui.catalog.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.davinhdot.sephora.domain.entity.Item
import fr.davinhdot.sephora.domain.interactor.SephoraInteractor
import fr.davinhdot.sephora.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var sephoraInteractor: SephoraInteractor

    @Inject
    lateinit var mSchedulerProvider: SchedulerProvider

    private var mCompositeDisposable = CompositeDisposable()

    val mLiveDataItems = MutableLiveData<List<Item>>()
    val mLiveDataNetworkError = MutableLiveData<Throwable>()

    override fun onCleared() {
        Timber.d("onCleared")

        mCompositeDisposable.dispose()
        mCompositeDisposable.clear()
    }

    fun getAllItemsFromApi() {
        Timber.d("getAllItemsFromApi")

        val disposable = sephoraInteractor
            .getAllItemsFromApi()
            .compose(mSchedulerProvider.ioToMainSingleScheduler())
            .subscribe({ results ->
                Timber.d("getAllItemsFromApi - doOnSuccess $results")

                mLiveDataItems.value = results
            }, { throwable ->
                Timber.d("getAllItemsFromApi - doOnError $throwable")

                mLiveDataNetworkError.value = throwable
            })

        mCompositeDisposable.add(disposable)
    }
}