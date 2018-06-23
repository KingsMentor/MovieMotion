package xyz.belvi.motion.main

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.GridLayoutManager
import android.view.Gravity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_toolbar.*
import kotlinx.android.synthetic.main.loading_view.*
import xyz.belvi.motion.R
import xyz.belvi.motion.data.realmObject.Movie
import xyz.belvi.motion.enchanceViews.EnhanceGridRecyclerView
import xyz.belvi.motion.enchanceViews.GridSpacingItemDecoration
import xyz.belvi.motion.main.adapter.MovieListAdapter
import xyz.belvi.motion.main.interfaceAdapters.MoviesFetchPresenter
import xyz.belvi.motion.main.viewModel.MoviesVM
import xyz.belvi.motion.models.enums.findByResID
import xyz.belvi.motion.preferences.getFilterType
import xyz.belvi.motion.preferences.setFilterType

class MainActivity : AppCompatActivity(), EnhanceGridRecyclerView.listenToScroll, MoviesFetchPresenter {


    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var moviesVM: MoviesVM

    private fun initSideNav() {
        setSupportActionBar(toolbar)
        // set a custom shadow that overlays the main content when the drawer opens
        drawer_layout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START)

        //
        actionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.app_name, R.string.app_name) {

        }


        drawer_layout.addDrawerListener(actionBarDrawerToggle)
        nav_view.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            val movieFilter = findByResID(item.itemId)
            if (movieFilter != getFilterType()) {
                item.isChecked = true
                setFilterType(movieFilter)
                moviesVM.switchFilter(movieFilter)
            }
            drawer_layout.closeDrawer(Gravity.END)
            true
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initSideNav()

        movies?.apply {
            listen(this@MainActivity)
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            addItemDecoration(GridSpacingItemDecoration(2, 0, false))
            setHasFixedSize(false)
            adapter = MovieListAdapter(arrayListOf(), this@MainActivity)
        }
        moviesVM = ViewModelProviders.of(this).get(MoviesVM::class.java)

        moviesVM.bindWithModel(this@MainActivity, getFilterType()).observeForever {
            it?.let {
                (movies.adapter as MovieListAdapter).updateItems(it)
            }
        }


    }


    override fun reachedEndOfList() {
        moviesVM.requestNextPage(getFilterType())
    }

    override fun onLoadCompleted() {
        loading_view_indicator.visibility = View.GONE
        loading_items.visibility = View.GONE

    }

    override fun onLoadFailure(emptyDataSet: Boolean) {
        if (emptyDataSet) {
            failed_view.visibility = View.VISIBLE
            loading_items.visibility = View.GONE
        }
    }

    override fun onLoadStarted(freshLoad: Boolean) {
        failed_view.visibility = View.GONE
        loading_view_indicator.visibility = if (freshLoad) View.GONE else View.VISIBLE
        loading_items.visibility = if (freshLoad) View.VISIBLE else View.GONE
    }

    override fun movieSelected(view: View, movie: Movie, postion: Int) {
    }


}
