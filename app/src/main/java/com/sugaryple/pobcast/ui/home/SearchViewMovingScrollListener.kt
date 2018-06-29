package com.sugaryple.pobcast.ui.home

import android.support.v7.widget.RecyclerView

/**
 * RecyclerViewとSearchViewの動き
 */
abstract class SearchViewMovingScrollListener(
        private val positionSearchView : Float,
        heightSearchView: Int,
        private val marginTopSearchView: Int
): RecyclerView.OnScrollListener() {

    private val maxTopPosition = -(positionSearchView + (heightSearchView))
    private val maxBottomPosition = (maxTopPosition + (heightSearchView)) + marginTopSearchView

    private var translationY = 0.0f
    private var scrollY = 0.0f

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if(dy == 0) return
        scrollY += dy
        translationY -= dy
        if(dy > 0) {
            //--
            if(translationY <= maxTopPosition) {
                translationY = maxTopPosition
            }
        } else {
            //++
            if(scrollY + marginTopSearchView < positionSearchView) {
                translationY = -scrollY
            } else {
                if (maxBottomPosition < translationY) {
                    translationY = maxBottomPosition
                }
            }
        }
        onTranslationY(translationY)
    }

    /**
     * SearchViewにtranslationYを設定関数
     */
    abstract fun onTranslationY(y : Float)
}