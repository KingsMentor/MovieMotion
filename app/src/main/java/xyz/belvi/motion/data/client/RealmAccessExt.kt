package xyz.belvi.motion.data.client

import android.arch.lifecycle.ViewModel
import io.realm.Realm
import xyz.belvi.motion.data.realmObject.Movie

/**
 * Created by zone2 on 6/23/18.
 */

fun ViewModel.updateMovies(movies: MutableList<Movie>) {
    val realm = Realm.getDefaultInstance()
    realm.beginTransaction()
    realm.insertOrUpdate(movies)
    realm.commitTransaction()
    realm.close()
}