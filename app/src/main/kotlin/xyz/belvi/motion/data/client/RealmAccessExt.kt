package xyz.belvi.motion.data.client

import android.arch.lifecycle.ViewModel
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults
import xyz.belvi.motion.R
import xyz.belvi.motion.app.MotionApp
import xyz.belvi.motion.data.realmObject.FavMovie
import xyz.belvi.motion.data.realmObject.Trailer
import xyz.belvi.motion.movieMain.viewModel.MoviesVM
import xyz.belvi.motion.models.enums.MovieFilter
import xyz.belvi.motion.movieDetails.viewModel.MovieDetailsVM
import xyz.belvi.motion.movieDetails.viewModel.TrailersVM
import xyz.belvi.motion.preferences.AppCache

/**
 * Created by Nosa Belvi on 6/23/18.
 *
 * extensible functions to perform CRUD operations on
 * realm data objects.
 * These are extensions on viewModels to as to restrict access
 * to only viewmodels
 *
 *
 */

// insert or update list of realm objects which can be FAvMovie or PopularMovie or TopRatedMovie
inline fun <reified T : RealmModel> ViewModel.update(realmItems: MutableList<T>) {
    val realm = Realm.getDefaultInstance()
    realm.beginTransaction()
    realm.insertOrUpdate(realmItems)
    realm.commitTransaction()
    realm.close()
}



// fetch from realm database
inline fun <reified T : RealmModel> ViewModel.fetch(): RealmResults<T>? {
    return Realm.getDefaultInstance().where(T::class.java)?.findAll()
}

// clear realm model
inline fun <reified T : RealmModel> ViewModel.clear() {
    val realm = Realm.getDefaultInstance()
    realm.beginTransaction()
    realm.where(T::class.java)?.findAll()?.deleteAllFromRealm()
    realm.commitTransaction()
    realm.close()

}

// check a realm model if it is empty
inline fun <reified T : RealmModel> ViewModel.isRealmListEmpty(): Boolean {
    return Realm.getDefaultInstance().where(T::class.java)?.findAll()?.let { it.size == 0 } ?: kotlin.run { true }
}

// check if movie has been added to favorite list by id
fun MovieDetailsVM.isFavMovie(movieID: Int): Boolean {
    return Realm.getDefaultInstance().where(FavMovie::class.java)?.equalTo("id", movieID)?.findFirst()?.let { true } ?: kotlin.run { false }
}

// add or remove a move to or from favorite list
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

// fetch trailer by movie id.
fun TrailersVM.fetchById(movieId: Int): RealmResults<Trailer>? {
    return Realm.getDefaultInstance().where(Trailer::class.java)?.equalTo("movieId", movieId)?.findAll()
}
