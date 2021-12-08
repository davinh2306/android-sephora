package fr.davinhdot.sephora.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.davinhdot.sephora.data.network.api.SephoraApi
import fr.davinhdot.sephora.data.network.constant.NetworkConstant
import fr.davinhdot.sephora.data.network.factory.RetrofitFactory
import fr.davinhdot.sephora.data.network.model.NetworkItem
import fr.davinhdot.sephora.domain.entity.Item
import fr.davinhdot.sephora.domain.mapper.Mapper
import fr.davinhdot.sephora.domain.mapper.item.NetworkToItemMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    // REMOTE

    @Singleton
    @Provides
    fun provideSuperItemApi(@ApplicationContext appContext: Context): SephoraApi {
        return RetrofitFactory.retrofit(NetworkConstant.BASE_URL, appContext)
            .create(SephoraApi::class.java)
    }

    // MAPPER

    @Singleton
    @Provides
    fun provideNetworkToItemMapper(): Mapper<NetworkItem, Item> {
        return NetworkToItemMapper()
    }
}