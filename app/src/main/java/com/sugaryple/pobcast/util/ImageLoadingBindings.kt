package com.sugaryple.pobcast.util

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.sugaryple.pobcast.core.GlideApp

object ImageLoadingBindings {
    @BindingAdapter("app:imageUrl")
    @JvmStatic fun setImageUrl(listView: ImageView, items: String) {
        GlideApp.with(listView.context)
                .load(items)
                .into(listView)
    }
}