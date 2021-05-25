package uz.arview.test.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.arview.test.data.local.model.GameDbModel

@Database(entities = [GameDbModel::class], version = 1)
abstract class GameDatabase : RoomDatabase() {
    abstract fun topGameDao() : TopGameDao
}