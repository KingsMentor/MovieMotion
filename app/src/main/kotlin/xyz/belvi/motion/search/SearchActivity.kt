package xyz.belvi.motion.search

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.recyclerview.R.attr.layoutManager
import android.support.v7.widget.LinearLayoutManager
import android.system.Os.listen
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_search.*
import xyz.belvi.motion.R
import xyz.belvi.motion.enchanceViews.EnhanceRecyclerView
import xyz.belvi.motion.helpers.CustomWatcher
import xyz.belvi.motion.models.SearchResult
import xyz.belvi.motion.search.presenter.SearchResultPresenter
import xyz.belvi.motion.search.viewModel.SearchVM

class SearchActivity : AppCompatActivity(), SearchResultPresenter, EnhanceRecyclerView.listenToScroll {


    private lateinit var searchVM: SearchVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)



        search_field.addTextChangedListener(object : CustomWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                super.onTextChanged(s, start, before, count)
                searchVM.search(s.toString())
            }
        })
        searchVM = ViewModelProviders.of(this).get(SearchVM::class.java)
        searchVM.bind(this)



    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun searchCompleted(isUpdate: Boolean, searchResult: MutableList<SearchResult>) {
        progress_indicator.visibility = View.VISIBLE
        if (searchResult.isEmpty()) {
            empty_view.visibility = View.VISIBLE
            search_recycler.visibility = View.GONE

        } else {
            empty_view.visibility = View.VISIBLE
            search_recycler.visibility = View.VISIBLE
        }

    }

    override fun reachedEndOfList() {
        searchVM.requestNextPage(search_field.text.toString())
    }

    override fun searching() {
        progress_indicator.visibility = View.VISIBLE
    }

    override fun searchFailed() {
        progress_indicator.visibility = View.VISIBLE

    }
}
