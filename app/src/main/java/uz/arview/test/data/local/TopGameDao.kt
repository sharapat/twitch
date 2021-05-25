package uz.arview.test.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import uz.arview.test.data.local.model.GameDbModel

@Dao
interface TopGameDao {
    @Query("SELECT * FROM games")
    fun getTopGames() : Observable<List<GameDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGames(games: List<GameDbModel>) : Completable
}