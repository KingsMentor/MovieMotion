package xyz.belvi.motion.network.client

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import xyz.belvi.motion.BuildConfig

/**
 * Created by zone2 on 6/23/18.
 */


class ApiClient {

    companion object {
        private var retrofit: Retrofit? = null
        private val API_BASE_URL: String = "http://api.themoviedb.org/3/movie/"

        val apiClient: Retrofit
            get() {
                return retrofit?.let { it } ?: kotlin.run {
                    Retrofit.Builder()
                            .baseUrl(API_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(provideHttpClient())
                            .build()
                }

            }

        private fun provideHttpClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(interceptor)
            }
            return builder.build()
        }
    }


}
