package by.homework.hlazarseni.binlistapp.di

import by.homework.hlazarseni.binlistapp.repository.Repository

import org.koin.core.module.dsl.singleOf

import org.koin.dsl.module

internal val repositoryModule = module {
    singleOf(::Repository)
}