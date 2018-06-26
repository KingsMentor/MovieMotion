package xyz.belvi.motion.network.client

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import xyz.belvi.motion.BuildConfig

/**
 * Created by Nosa Belvi on 6/23/18.
 * @ApiClient is a singleton class implementation for performing retrofit request
 * @apiClient for making request from any class
 * @provideHttpClient is base httpclient for request
 */


class ApiClient {

    companion object {
        private var retrofit: Retrofit? = null
        private val API_BASE_URL: String = "http://api.themoviedb.org/3/"

        val apiClient: Retrofit
            get() {
                return retrofit?.let { it } ?: kotlin.run {
                    Retrofit.Builder()
                            .baseUrl(API_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create()) // for serialization. Great resource for json parsing
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // for rx. Enable the use of Observable instead of {@link Call}
                            .client(provideHttpClient())
                            .build()
                }

            }

        private fun provideHttpClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
            // logging details should only be shown on debug mode
            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(interceptor)
            }
            return builder.build()
        }
    }


}
