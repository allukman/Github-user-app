package com.karsatech.githubuser.ui.detail.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.karsatech.githubuser.adapter.GithubUserAdapter
import com.karsatech.githubuser.data.remote.ApiConfig
import com.karsatech.githubuser.data.remote.ApiService
import com.karsatech.githubuser.data.response.DetailUserResponse
import com.karsatech.githubuser.databinding.FragmentFollowingBinding
import com.karsatech.githubuser.ui.detail.DetailViewModel

class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var service: ApiService

    private val detailViewModel by viewModels<DetailViewModel>()

    companion object {
        var USERNAME = ""
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        settingViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeFollowing()
        setRecyclerView()
    }

    private fun subscribeFollowing() {
        detailViewModel.following.observe(viewLifecycleOwner) {data ->
            setFollowingData(data)
        }

        detailViewModel.followingIsLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
    }

    private fun showLoading(loading: Boolean) {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        binding.rvGithubUsers.visibility = if (loading) View.GONE else View.VISIBLE
    }

    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        binding.rvGithubUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvGithubUsers.addItemDecoration(itemDecoration)
    }

    private fun setFollowingData(user: List<DetailUserResponse>) {
        val adapter = GithubUserAdapter()
        adapter.submitList(user)
        binding.rvGithubUsers.adapter = adapter

        adapter.setOnItemClickCallback(object : GithubUserAdapter.ActionAdapter {
            override fun onItemClick(data: DetailUserResponse) {
            }
        })
    }

    private fun settingViewModel() {
        service = ApiConfig.getApiClient()!!.create(ApiService::class.java)

        detailViewModel.apply {
            setService(service)
            getFollowing(USERNAME, "following")
        }
    }
}