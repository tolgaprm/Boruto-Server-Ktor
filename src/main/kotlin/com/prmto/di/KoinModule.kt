package com.prmto.di

import com.prmto.repository.HeroRepository
import com.prmto.repository.HeroRepositoryAlternative
import com.prmto.repository.HeroRepositoryImpl
import com.prmto.repository.HeroRepositoryImplAlternative
import org.koin.dsl.module

val koinModule = module {
    single<HeroRepository> {
        HeroRepositoryImpl()
    }
    single<HeroRepositoryAlternative> {
        HeroRepositoryImplAlternative()
    }
}