package com.houston.filmographer.presentation.root

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.houston.filmographer.R
import com.houston.filmographer.databinding.ActivityRootBinding
import com.houston.filmographer.navigation.NavigatorHolder
import com.houston.filmographer.navigation.NavigatorImpl
import com.houston.filmographer.presentation.search.SearchFragment
import org.koin.android.ext.android.inject

class RootActivity : AppCompatActivity() {

    private var _binding: ActivityRootBinding? = null
    private val binding get() = _binding!!

    private val navigatorHolder by inject<NavigatorHolder>()
    private val navigator = NavigatorImpl(
        fragmentContainerViewId = R.id.fragment_container_view_root,
        fragmentManager = supportFragmentManager
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            navigator.openFragment(SearchFragment())
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.attachNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.detachNavigator()
    }
}