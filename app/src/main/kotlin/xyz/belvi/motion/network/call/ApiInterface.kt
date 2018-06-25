package xyz.belvi.motion.network.call

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import xyz.belvi.motion.models.retroResponse.MovieResponse
import xyz.belvi.motion.models.retroResponse.TrailerResponse

/**
 * Created by zone2 on 6/23/18.
 */
interface ApiInterface {

    @GET("movie/{path}")
    fun fetchMovies(@Path("path") sortType: String, @Query("api_key") apiKey: String, @Query("page") page: Int): io.reactivex.Observable<MovieResponse>

    @GET("movie/{id}/videos")
    fun fetchTrailers(@Path("id") movieId: Int, @Query("api_key") apiKey: String): io.reactivex.Observable<TrailerResponse>

    @GET("search/movie")
    fun search(@Query("query") query: String, @Query("api_key") apiKey: String, @Query("page") page: Int = 1): io.reactivex.Observable<MovieResponse>

}