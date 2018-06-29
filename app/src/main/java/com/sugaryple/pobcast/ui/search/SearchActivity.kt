package com.sugaryple.pobcast.ui.search

import android.app.Activity
import android.app.ActivityOptions
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.SearchView
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.sugaryple.pobcast.R
import com.sugaryple.pobcast.databinding.SearchActBinding
import com.sugaryple.pobcast.ui.result.ResultActivity
import com.sugaryple.pobcast.util.lg
import com.sugaryple.pobcast.util.obtainViewModel


class SearchActivity : AppCompatActivity() {

    private lateinit var viewDataBinding: SearchActBinding

    private lateinit var typeaheadListAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView<SearchActBinding>(this, R.layout.search_act).apply {
            viewmodel = obtainViewModel()
        }
        setupTypeaheadList()
        setupTransitionName()
        setSearchManager()
        viewDataBinding.actSearchLytBk.setOnClickListener {
            finishAfterTransition()
        }
    }

    private fun setupTypeaheadList() {
        typeaheadListAdapter = ArrayAdapter(this.applicationContext,
                android.R.layout.simple_list_item_1)
        viewDataBinding.actSearchList.run {
            adapter = typeaheadListAdapter
            onItemClickListener = AdapterView.OnItemClickListener {
                p, v, pos, id ->
                val query = adapter.getItem(pos)
                if(query is String) {
                    viewDataBinding.actSearchInput.setQuery(query, true)
                }
            }
        }
    }

    private fun startResult(query: String) {
        ResultActivity.startActivity(this, viewDataBinding.actSearchLyt, query)
    }

    private fun setupTransitionName() {
        viewDataBinding.actSearchLyt.transitionName = TRANSITION_NAME_SEARCH_INPUT
    }

    private fun setSearchManager() {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        viewDataBinding.actSearchInput.run {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false)
            setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    lg.v("onQueryTextSubmit", query)
                    if(query?.isNotEmpty() == true) {
                        startResult(query)
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    lg.v("onQueryTextChange", newText)
                    viewDataBinding.viewmodel?.setNameTypeahead(newText)
                    return false
                }

            })
        }

    }

    private fun obtainViewModel(): SearchViewModel = obtainViewModel(SearchViewModel::class.java)

    companion object {
        const val TRANSITION_NAME_SEARCH_INPUT = "act_search_input"
        fun startActivity(act: Activity, view: View) {
            val intent = Intent(act, SearchActivity::class.java)
            val options = ActivityOptions
                    .makeSceneTransitionAnimation(act,
                            view,
                            TRANSITION_NAME_SEARCH_INPUT)
            act.startActivity(intent, options.toBundle())
        }
    }
}
