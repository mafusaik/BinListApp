package by.homework.hlazarseni.binlistapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BinEntity::class], version = 1, exportSchema = false)
abstract class BinDatabase : RoomDatabase() {
    abstract val binDao: BinDao
}