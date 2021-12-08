package fr.davinhdot.sephora.data.factory

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import fr.davinhdot.sephora.data.constant.NetworkConstant.DEFAULT_CONNECT_TIMEOUT
import fr.davinhdot.sephora.data.constant.NetworkConstant.DEFAULT_READ_TIMEOUT
import fr.davinhdot.sephora.data.constant.NetworkConstant.DEFAULT_WRITE_TIMEOUT
import fr.davinhdot.sephora.data.constant.NetworkConstant.OKHTTP_CACHE
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit


object RetrofitFactory {

    private const val CACHE_SIZE = 10 * 1024 * 1024.toLong() // 10 MB

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    fun retrofit(baseUrl: String, context: Context?): Retrofit {
        Timber.d("retrofit")

        return Retrofit.Builder()
                .client(getClient(context))
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
    }

    private fun getClient(context: Context?) =
        OkHttpClient().newBuilder()
                .cache(Cache(File(OKHTTP_CACHE), CACHE_SIZE))
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(ChuckInterceptor(context))
                .addInterceptor(loggingInterceptor)
                .build()
}