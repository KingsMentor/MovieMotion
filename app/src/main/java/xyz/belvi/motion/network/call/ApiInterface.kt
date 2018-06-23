package xyz.belvi.motion.network.call

import com.google.gson.JsonElement
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by zone2 on 6/23/18.
 */
interface ApiInterface {

    @GET("{path}")
    fun fetchMovies(@Path("path") sortType: String, @Query("api_key") apiKey: String, @Query("page") page: Int): io.reactivex.Observable<JsonElement>

}