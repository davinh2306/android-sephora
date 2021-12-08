package fr.davinhdot.sephora.utils

import fr.davinhdot.sephora.domain.entity.HttpCallFailureException
import fr.davinhdot.sephora.domain.entity.NoNetworkException
import fr.davinhdot.sephora.domain.entity.ServerUnreachableException
import io.reactivex.Single
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <T> Single<T>.mapNetworkErrors(): Single<T> =
    this.onErrorResumeNext { error ->
        when (error) {
            is SocketTimeoutException -> Single.error(NoNetworkException(error))
            is UnknownHostException -> Single.error(ServerUnreachableException(error))
            is HttpException -> Single.error(HttpCallFailureException(error, error.code()))
            else -> Single.error(error)
        }
    }