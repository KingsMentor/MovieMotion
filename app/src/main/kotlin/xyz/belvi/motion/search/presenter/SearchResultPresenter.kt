package xyz.belvi.motion.search.presenter

import xyz.belvi.motion.models.SearchResult

/**
 * Created by zone2 on 6/25/18.
 */
interface SearchResultPresenter {
    fun searchCompleted(isUpdate: Boolean, searchResult: MutableList<SearchResult>)
    fun searching()
    fun searchFailed()
}