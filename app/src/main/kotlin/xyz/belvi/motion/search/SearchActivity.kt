package xyz.belvi.motion.search

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.failed_to_load.*
import kotlinx.android.synthetic.main.search_btn.*
import kotlinx.android.synthetic.main.search_recycler_view.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import xyz.belvi.motion.R
import xyz.belvi.motion.data.realmObject.MotionMovie
import xyz.belvi.motion.movieMain.adapter.MovieListAdapter
import xyz.belvi.motion.search.presenter.SearchResultPresenter
import xyz.belvi.motion.search.viewModel.SearchVM
import xyz.belvi.motion.sharedScreens.MovieActivity
import xyz.belvi.motion.utils.calculateNoOfColumns
/**
 * Created by Nosa Belvi on 6/23/18.
 *
 * @SearchActivity houses search implementation of the app.
 * ViewModel - @SearchVM
 * Presenter - SearchResultPresenter - an extension of @MovieDetailsPresenter
 *
 */

class SearchActivity : MovieActivity(), SearchResultPresenter {

    private lateinit var searchVM: SearchVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        // initialise viewModel and bind with this activity
        searchVM = ViewModelProviders.of(this).get(SearchVM::class.java)
        searchVM.bind(this)

        // hous keeping for recyclerview
        search_recycler.apply {
            layoutManager = GridLayoutManager(this@SearchActivity, this.context.calculateNoOfColumns())
            setHasFixedSize(false)
            adapter = MovieListAdapter(arrayListOf(), this@SearchActivity)
        }


        // merge searh and retry button action lick. They both perform same operation
        Observable.merge(RxView.clicks(search), RxView.clicks(retry))
                .subscribe { searchVM.search(search_field.text.toString()) }


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onLoadStarted(freshLoad: Boolean) {
        super.onLoadStarted(freshLoad)
        progress_indicator.visibility = View.VISIBLE

        failed_view.visibility = View.GONE
        empty_view.visibility = View.GONE
    }


    override fun onLoadFailure(emptyDataSet: Boolean) {
        super.onLoadFailure(emptyDataSet)
        failed_view.visibility = View.VISIBLE

        progress_indicator.visibility = View.GONE
        search_recycler.visibility = View.GONE
        empty_view.visibility = View.GONE
    }

    // updates recyclerview with search result
    override fun onSearchResult(result: MutableList<MotionMovie>) {
        super.onSearchResult(result)
        progress_indicator.visibility = View.GONE
        failed_view.visibility = View.GONE
        search_recycler.visibility = View.VISIBLE
        (search_recycler.adapter as MovieListAdapter).updateItems(result)

    }

    // show movie details page when a movie is selected
    override fun movieSelected(view: View, movie: MotionMovie) {
        super.movieSelected(view, movie)
        showMovieDetails(view, movie)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

}
