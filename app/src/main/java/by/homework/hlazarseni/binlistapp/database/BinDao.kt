package by.homework.hlazarseni.binlistapp.database

import androidx.room.*

@Dao
interface BinDao {

    @Query("SELECT * from BinEntity")
    fun getNumbers(): List<BinEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: BinEntity)

}