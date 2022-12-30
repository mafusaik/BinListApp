package by.homework.hlazarseni.binlistapp.repository

import by.homework.hlazarseni.binlistapp.api.BinApi
import by.homework.hlazarseni.binlistapp.database.BinDao
import by.homework.hlazarseni.binlistapp.mapper.toDomain
import by.homework.hlazarseni.binlistapp.mapper.toDomainModels
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class Repository(
    private val binApi: BinApi,
    private val binDao: BinDao
) {



    suspend fun getNumbersDB() = withContext(Dispatchers.IO) {
        binDao.getNumbersObserve().toDomainModels()
    }


    suspend fun insertNumberDB(numberCard: String) = withContext(Dispatchers.IO) {
        binDao.insert(numberCard.toDomain())
    }

}