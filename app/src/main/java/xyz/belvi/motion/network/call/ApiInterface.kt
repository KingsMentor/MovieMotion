package xyz.belvi.motion.network.call

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import xyz.belvi.motion.models.retroResponse.MovieResponse

/**
 * Created by zone2 on 6/23/18.
 */
interface ApiInterface {

    @GET("{path}")
    abstract fun fetchMovies(@Path("path") sortType: String, @Query("api_key") apiKey: String, @Query("page") page: Int): io.reactivex.Observable<List<MovieResponse>>

}