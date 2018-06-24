package xyz.belvi.motion.data.client

import android.arch.lifecycle.ViewModel
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults
import xyz.belvi.motion.R
import xyz.belvi.motion.app.MotionApp
import xyz.belvi.motion.data.realmObject.FavMovie
import xyz.belvi.motion.movieMain.viewModel.MoviesVM
import xyz.belvi.motion.models.enums.MovieFilter
import xyz.belvi.motion.movieDetails.viewModel.MovieDetailsVM
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

inline fun <reified T : RealmModel> MovieDetailsVM.findMovieById(movieID: Int): T? {
    return Realm.getDefaultInstance().where(T::class.java)?.equalTo("id", movieID)?.findFirst()
}

fun MovieDetailsVM.isFavMovie(movieID: Int): Boolean {
    return Realm.getDefaultInstance().where(FavMovie::class.java)?.equalTo("id", movieID)?.findFirst()?.let { true } ?: kotlin.run { false }
}

fun MovieDetailsVM.addOrRemoveFavMovie(favMovie: FavMovie, state: Boolean) {
    val realm = Realm.getDefaultInstance()
    realm.beginTransaction()
    if (state)
        realm.insertOrUpdate(favMovie)
    else
        realm.where(FavMovie::class.java)?.equalTo("id", favMovie.id)?.findAll()?.deleteAllFromRealm()
    realm.commitTransaction()
    realm.close()
}