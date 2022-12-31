package by.homework.hlazarseni.binlistapp.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BinDao {

    @Query("SELECT * from BinEntity")
    fun getNumbersObserve(): List<BinEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: BinEntity)

}