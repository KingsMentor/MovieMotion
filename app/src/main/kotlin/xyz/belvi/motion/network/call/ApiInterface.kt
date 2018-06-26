package xyz.belvi.motion.network.call

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import xyz.belvi.motion.models.retroResponse.MovieResponse
import xyz.belvi.motion.models.retroResponse.TrailerResponse

/**
 * Created by Nosa Belvi on 6/23/18.
 * interce for retrofit request
 */
interface ApiInterface {

    // for fetching movies.
    @GET("movie/{path}")
    fun fetchMovies(@Path("path") sortType: String, @Query("api_key") apiKey: String, @Query("page") page: Int): io.reactivex.Observable<MovieResponse>

    // for fetching trailers
    @GET("movie/{id}/videos")
    fun fetchTrailers(@Path("id") movieId: Int, @Query("api_key") apiKey: String): io.reactivex.Observable<TrailerResponse>

    // for searching movie. @page is always 1 because I always want to request search resullt on page 1
    @GET("search/movie")
    fun search(@Query("query") query: String, @Query("api_key") apiKey: String, @Query("page") page: Int = 1): io.reactivex.Observable<MovieResponse>

}