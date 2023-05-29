package com.karsatech.githubuser.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.karsatech.githubuser.R
import com.karsatech.githubuser.data.remote.ApiConfig
import com.karsatech.githubuser.data.remote.ApiService
import com.karsatech.githubuser.data.response.DetailUserResponse
import com.karsatech.githubuser.databinding.ActivityDetailUserBinding
import com.karsatech.githubuser.ui.detail.adapter.ViewPagerAdapter
import com.karsatech.githubuser.ui.detail.followers.FollowersFragment
import com.karsatech.githubuser.ui.detail.following.FollowingFragment

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var service: ApiService

    private lateinit var data: DetailUserResponse
    private var username = ""

    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        service = ApiConfig.getApiClient()!!.create(ApiService::class.java)

        setupTransitionData()
        settingViewModel()
        subscribe()
        sendDataToFragment()
        settingViewPager()
    }

    private fun subscribe() {

        detailViewModel.detailUser.observe(this@DetailUserActivity) { data ->
            setData(data)
        }

        detailViewModel.isLoading.observe(this@DetailUserActivity) { loading ->
            showLoading(loading)
        }
    }

    private fun settingViewPager() {
        binding.apply {
            val detailUserPagerAdapter = ViewPagerAdapter(this@DetailUserActivity)

            viewPager.adapter = detailUserPagerAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()

            supportActionBar?.elevation = 0f
        }
    }

    private fun sendDataToFragment() {
        FollowersFragment.USERNAME = data.username ?: ""
        FollowingFragment.USERNAME = data.username ?: ""
    }

    private fun setData(data: DetailUserResponse) {
        binding.tvName.text = data.name
        binding.tvAmountFollowers.text = String.format("${data.followers} followers")
        binding.tvAmountFollowing.text = String.format("${data.following} following")
        binding.tvRepo.text = String.format("Repositories ${data.publicRepository}")
        binding.tvCompany.text = data.company ?: "-"
        binding.tvLocation.text = data.location ?: "-"
    }

    private fun showLoading(loading: Boolean) {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun settingViewModel() {
        detailViewModel.apply {
            setService(service)
        }
    }

    private fun setupTransitionData() {
        data = intent.getParcelableExtra<DetailUserResponse>("User") as DetailUserResponse
        username = data.username ?: ""

        Glide.with(this).load(data.avatarUrl).into(binding.civImageGithubUser)
        binding.tvUsername.text = data.username

        detailViewModel.getDetailUsers(username)
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers, R.string.following
        )
    }
}