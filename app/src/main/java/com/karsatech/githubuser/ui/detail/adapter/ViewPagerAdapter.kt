package com.karsatech.githubuser.ui.detail.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.karsatech.githubuser.ui.detail.followers.FollowersFragment
import com.karsatech.githubuser.ui.detail.following.FollowingFragment

class ViewPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = ITEM_COUNT

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        return fragment as Fragment
    }

    companion object {
        private const val ITEM_COUNT = 2
    }
}