package com.houston.filmographer.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class NavigatorImpl(
    override val fragmentContainerViewId: Int,
    override val fragmentManager: FragmentManager
): Navigator {

    override fun openFragment(fragment: Fragment) {
        fragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(fragmentContainerViewId, fragment)
            .addToBackStack(null)
            .commit()
    }
}