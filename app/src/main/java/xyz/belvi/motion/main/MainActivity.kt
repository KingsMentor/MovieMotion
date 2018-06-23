package xyz.belvi.motion.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_toolbar.*
import xyz.belvi.motion.R
import xyz.belvi.motion.data.realmObject.Movie
import xyz.belvi.motion.enchanceViews.EnhanceGridRecyclerView
import xyz.belvi.motion.enchanceViews.GridSpacingItemDecoration
import xyz.belvi.motion.main.interfaceAdapters.MoviesFetchPresenter

class MainActivity : AppCompatActivity(), EnhanceGridRecyclerView.listenToScroll, MoviesFetchPresenter {


    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    private fun initSideNav() {
        setSupportActionBar(toolbar)
        // set a custom shadow that overlays the main content when the drawer opens
        drawer_layout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START)

        //
        actionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.app_name, R.string.app_name) {

        }


        drawer_layout.addDrawerListener(actionBarDrawerToggle)
        nav_view.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            item.isChecked = true
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
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        drawer_layout.openDrawer(Gravity.END)
        return super.onOptionsItemSelected(item)
    }

    override fun reachedEndOfList() {

    }

    override fun onLoadCompleted() {

    }

    override fun onLoadFailure() {
    }

    override fun onLoadStarted(refresh: Boolean) {
    }

    override fun movieSelected(view: View, movie: Movie, postion: Int) {
    }


}
