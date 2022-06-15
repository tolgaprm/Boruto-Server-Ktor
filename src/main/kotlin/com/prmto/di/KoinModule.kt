package com.prmto.di

import com.prmto.repository.HeroRepository
import com.prmto.repository.HeroRepositoryImpl
import org.koin.dsl.module

val koinModule = module {
    single<HeroRepository> {
        HeroRepositoryImpl()
    }
}