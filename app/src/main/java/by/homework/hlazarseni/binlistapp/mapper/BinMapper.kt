package by.homework.hlazarseni.binlistapp.mapper

import by.homework.hlazarseni.binlistapp.database.BinEntity


internal fun List<BinEntity>.toDomainModels(): List<String> = map {
    it.toDomain()
}

internal fun BinEntity.toDomain(): String  {
    return name
}

internal fun String.toDomain(): BinEntity {
    return BinEntity(
        name = toString()
    )
}