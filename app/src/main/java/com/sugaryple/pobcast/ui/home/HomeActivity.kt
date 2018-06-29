package com.sugaryple.pobcast.ui.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sugaryple.pobcast.R
import com.sugaryple.pobcast.util.obtainViewModel
import com.sugaryple.pobcast.util.replaceFragmentInActivity

/**
 * 最初ページ
 * ベストPodcast表示
 */
class HomeActivity : AppCompatActivity() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_act)

        replaceFragmentInActivity(findOrCreateViewFragment(), R.id.act_main_lyt_frame)

        homeViewModel = obtainViewModel()
    }

    private fun findOrCreateViewFragment() =
            supportFragmentManager.findFragmentById(R.id.act_main_lyt_frame) ?:
            HomeFragment.newInstance()

    fun obtainViewModel(): HomeViewModel = obtainViewModel(HomeViewModel::class.java)

}
