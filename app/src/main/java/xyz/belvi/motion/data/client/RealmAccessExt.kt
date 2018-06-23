package xyz.belvi.motion.data.client

import android.arch.lifecycle.ViewModel
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults
import xyz.belvi.motion.R
import xyz.belvi.motion.app.MotionApp
import xyz.belvi.motion.movieMain.viewModel.MoviesVM
import xyz.belvi.motion.models.enums.MovieFilter
import xyz.belvi.motion.preferences.AppCache

/**
 * Created by zone2 on 6/23/18.
 */

inline fun <reified T : RealmModel> MoviesVM.updateMovies(movies: MutableList<T>) {
    val realm = Realm.getDefaultInstance()
    realm.beginTransaction()
    realm.insertOrUpdate(movies)
    realm.commitTransaction()
    realm.close()
}

inline fun <reified T : RealmModel> MoviesVM.fetchMovies(): RealmResults<T>? {
    return Realm.getDefaultInstance().where(T::class.java)?.findAll()
}

inline fun <reified T : RealmModel> MoviesVM.clearMovies() {
    val realm = Realm.getDefaultInstance()
    realm.beginTransaction()
    realm.where(T::class.java)?.findAll()?.deleteAllFromRealm()
    realm.commitTransaction()
    realm.close()

}

inline fun <reified T : RealmModel> MoviesVM.isMovieListEmpty(): Boolean {
    return Realm.getDefaultInstance().where(T::class.java)?.findAll()?.let { it.size == 0 } ?: kotlin.run { true }
}

fun ViewModel.apiKey(): String? {
    return MotionApp.instance?.getString(R.string.movie_db_api_key)
}

fun ViewModel.resetPageCounter() {
    AppCache().resetLastRequestedPage()
}

fun MovieFilter.updatePageCounter() {
    AppCache().updateLastRequested(this)
}

fun MovieFilter.currentPage(): Int {
    return AppCache().currentPage(this)
}