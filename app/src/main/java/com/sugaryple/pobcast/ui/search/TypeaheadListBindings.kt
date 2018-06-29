package com.sugaryple.pobcast.ui.search

import android.databinding.BindingAdapter
import android.widget.ArrayAdapter
import android.widget.ListView
import java.util.Collections.addAll


object TypeaheadListBindings {

    @BindingAdapter("app:items")
    @JvmStatic fun setItems(listView: ListView, items: List<String>) {
        with(listView.adapter as ArrayAdapter<String>) {
            clear()
            addAll(items)
        }
    }
}