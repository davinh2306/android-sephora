package fr.davinhdot.sephora.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.davinhdot.sephora.data.repository.SephoraRepositoryImpl
import fr.davinhdot.sephora.domain.repository.SephoraRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindSephoraRepository(impl: SephoraRepositoryImpl): SephoraRepository
}