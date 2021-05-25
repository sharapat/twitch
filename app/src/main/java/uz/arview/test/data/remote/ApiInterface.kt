package uz.arview.test.data.remote

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import uz.arview.test.data.remote.model.TopGameResponse

interface ApiInterface {
    @GET("games/top")
    fun getTopGames(
        @Header("Client-ID") clientId: String,
        @Header("Accept") accept: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Observable<TopGameResponse>
}