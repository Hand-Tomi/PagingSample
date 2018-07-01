package com.sugaryple.pobcast.ui.result

import android.app.Activity
import android.app.ActivityOptions
import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.sugaryple.pobcast.R
import com.sugaryple.pobcast.data.vo.Podcast
import com.sugaryple.pobcast.databinding.ResultActBinding
import com.sugaryple.pobcast.ui.home.SearchViewMovingScrollListener
import com.sugaryple.pobcast.util.DisplayUtil
import com.sugaryple.pobcast.util.Util
import com.sugaryple.pobcast.util.lg
import com.sugaryple.pobcast.util.obtainViewModel

class ResultActivity: AppCompatActivity() {
    private lateinit var viewDataBinding: ResultActBinding
    private lateinit var listAdapter: ResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_act)
        viewDataBinding = DataBindingUtil
                .setContentView<ResultActBinding>(this, R.layout.result_act).apply {
            viewmodel = obtainViewModel()
            listener = object : ResultUserActionsListener {
                override fun onClickSearchView(v: View) {
                    if(v is TextView) {
                        v.text = ""
                    }
                    finishAfterTransition()
                }
            }
        }
        viewDataBinding.resultActTvSearch.transitionName = TRANSITION_NAME_SEARCH_INPUT
        setupListAdapter()
        setupMovingScrollView()
        viewDataBinding.viewmodel?.showSearchedPodcasts(intent.getStringExtra(SEARCH_QUERY))
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            listAdapter = ResultAdapter(viewModel)
            viewDataBinding.resultActList.adapter = listAdapter

            viewModel.repoResult.observe(this, Observer<PagedList<Podcast>> {
                listAdapter.submitList(it)
            })
        } else {
            lg.v("ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun setupMovingScrollView() {
        Util.setOnPreDrawListener(viewDataBinding.resultActTvSearch) {
            view ->
            val topMarginSearchView = DisplayUtil.getDpToPx(view.context, TOP_MARGIN_SEARCH_VIEW)
            viewDataBinding.resultActList.addOnScrollListener(
                    object : SearchViewMovingScrollListener(view.y, view.height, topMarginSearchView) {
                        override fun onTranslationY(y: Float) {
                            view.translationY = y
                        }
                    })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewDataBinding.resultActList.clearOnScrollListeners()
    }

    private fun obtainViewModel() = obtainViewModel(ResultViewModel::class.java)

    companion object {
        const val TOP_MARGIN_SEARCH_VIEW = 32
        const val TRANSITION_NAME_SEARCH_INPUT = "act_result_input"
        const val SEARCH_QUERY = "SEARCH_QUERY"
        fun startActivity(act: Activity, view: View, query: String) {
            val intent = Intent(act, ResultActivity::class.java)
            intent.putExtra(ResultActivity.SEARCH_QUERY, query)
            val options = ActivityOptions
                    .makeSceneTransitionAnimation(act,
                            view,
                            TRANSITION_NAME_SEARCH_INPUT)
            act.startActivity(intent, options.toBundle())
        }
    }
}