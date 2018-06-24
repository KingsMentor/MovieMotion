package xyz.belvi.motion.movieDetails.trailers

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatTextView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.trailers_layout.*
import xyz.belvi.motion.R
import xyz.belvi.motion.constants.YOUTUBE_WATCH
import xyz.belvi.motion.data.realmObject.Movie
import xyz.belvi.motion.data.realmObject.Trailer
import xyz.belvi.motion.movieDetails.MovieDetailedActivity.Companion.MOVIE_KEY
import xyz.belvi.motion.movieDetails.presenter.TrailerPresenter
import xyz.belvi.motion.movieDetails.viewModel.TrailersVM
import xyz.belvi.motion.movieMain.viewModel.MoviesVM

/**
 * Created by zone2 on 6/24/18.
 */
class TrailersFragment : Fragment(), TrailerPresenter {


    private lateinit var trailersVM: TrailersVM


    fun newInstance(movieId: Int): TrailersFragment {
        val bundle = Bundle()
        bundle.putInt(MOVIE_KEY, movieId)
        arguments = bundle
        return this
    }

    fun getMovieId(): Int? {
        return arguments?.getInt(MOVIE_KEY)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.trailers_layout, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trailersVM = ViewModelProviders.of(this).get(TrailersVM::class.java)
        getMovieId()?.let { id ->
            trailersVM.bind(this, id).observeForever { result ->
                trailers?.let {
                    trailers.removeAllViews()
                    result?.forEach {
                        trailers.addView(getTrailerView(it))
                    }
                }

            }
            RxView.clicks(retry_view).subscribe {
                trailersVM.retry(id)
            }
        }
    }

    private fun getTrailerView(trailer: Trailer): View {
        val view = LayoutInflater.from(context).inflate(R.layout.trailer_item, null, false)
        val trailerAppCompatTextView = view.findViewById(R.id.trailer_txt_view) as AppCompatTextView
        trailerAppCompatTextView.text = trailer.name
        trailerAppCompatTextView.contentDescription = String.format(getString(R.string.trailer_content_description), trailer.name)

        trailerAppCompatTextView.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_WATCH + trailer.key)))
        }

        return view
    }

    override fun onTrailerRetrieveFailed(emptyList: Boolean) {
        trailer_loading_indicator.visibility = View.GONE
        if (emptyList)
            retry_view.visibility = View.VISIBLE
    }

    override fun startLoading(emptyList: Boolean) {
        if (emptyList)
            trailer_loading_indicator.visibility = View.VISIBLE
        retry_view.visibility = View.GONE

    }

    override fun onLoadCompleted(emptyList: Boolean) {
        trailer_loading_indicator.visibility = View.GONE
        if (emptyList)
            retry_view.visibility = View.VISIBLE
    }

}