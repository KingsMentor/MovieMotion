package xyz.belvi.motion.data.client

import android.arch.lifecycle.ViewModel
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import xyz.belvi.motion.data.realmObject.Movie
import xyz.belvi.motion.main.viewModel.MoviesVM

/**
 * Created by zone2 on 6/23/18.
 */

fun MoviesVM.updateMovies(movies: MutableList<Movie>) {
    val realm = Realm.getDefaultInstance()
    realm.beginTransaction()
    realm.insertOrUpdate(movies)
    realm.commitTransaction()
    realm.close()
}

fun MoviesVM.fetchMovies(): RealmResults<Movie>? {
    return Realm.getDefaultInstance().where(Movie::class.java)?.findAll()
}

fun MoviesVM.clearMovies() {
    val realm = Realm.getDefaultInstance()
    realm.beginTransaction()
    realm.where(Movie::class.java)?.findAll()?.deleteAllFromRealm()
    realm.commitTransaction()
    realm.close()

}

fun MoviesVM.isMovieListEmpty(): Boolean {
    return Realm.getDefaultInstance().where(Movie::class.java)?.findAll()?.let { it.size == 0 } ?: kotlin.run { true }
}