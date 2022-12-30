package by.homework.hlazarseni.binlistapp.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import by.homework.hlazarseni.binlistapp.ActivityViewModel

internal val viewModelModule= module {
    viewModelOf(::ActivityViewModel)
}