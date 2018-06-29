package com.sugaryple.pobcast.ui.home

import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sugaryple.pobcast.R
import com.sugaryple.pobcast.data.vo.Channel
import com.sugaryple.pobcast.databinding.HomeFragBinding
import com.sugaryple.pobcast.ui.search.SearchActivity
import com.sugaryple.pobcast.util.DisplayUtil
import com.sugaryple.pobcast.util.Util
import com.sugaryple.pobcast.util.lg

class HomeFragment: Fragment() {

    private lateinit var viewDataBinding: HomeFragBinding
    private lateinit var listAdapter: HomeAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.home_frag, container, false)
        viewDataBinding = HomeFragBinding.bind(view).apply {
            viewmodel = (activity as HomeActivity).obtainViewModel()
            listener = object : HomeUserActionsListener {
                override fun onStartSearch(v: View) {
                    activity?.let { SearchActivity.startActivity(it, v) }
                }
            }
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListAdapter()
        setupMovingScrollView()
        viewDataBinding.viewmodel?.setUp()
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            listAdapter = HomeAdapter(viewModel)
            viewDataBinding.homeFragList.adapter = listAdapter

            viewModel.repoResult.observe(this, Observer<PagedList<Channel>> {
                listAdapter.submitList(it)
            })
        }
    }

    private fun setupMovingScrollView() {
        Util.setOnPreDrawListener(viewDataBinding.homeFragImgSearch) {
            view ->
                val topMarginSearchView = DisplayUtil.getDpToPx(view.context, 42)
                viewDataBinding.homeFragList.addOnScrollListener(
                    object : SearchViewMovingScrollListener(view.y, view.height, topMarginSearchView) {
                        override fun onTranslationY(y: Float) {
                            view.translationY = y
                        }
                    })
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}