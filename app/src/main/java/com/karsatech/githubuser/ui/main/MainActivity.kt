package com.karsatech.githubuser.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.karsatech.githubuser.adapter.GithubUserAdapter
import com.karsatech.githubuser.data.remote.ApiConfig
import com.karsatech.githubuser.data.remote.ApiService
import com.karsatech.githubuser.data.response.DetailUserResponse
import com.karsatech.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var service: ApiService

    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        service = ApiConfig.getApiClient()!!.create(ApiService::class.java)

        settingViewModel()
        setRecyclerView()
        setSearchBar()
        subscribe()
    }

    private fun setSearchBar() {
        binding.apply {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchBar.text = searchView.text
                searchView.hide()

                if (searchView.text.toString().isNullOrBlank()) {
                    mainViewModel.getAllUsers()
                } else {
                    mainViewModel.searchUsers(searchView.text.toString())
                }

                false
            }
        }
    }
    private fun subscribe() {
        binding.apply {
            mainViewModel.listUser.observe(this@MainActivity) { listUser ->
                setGithubData(listUser)
            }

            mainViewModel.isLoading.observe(this@MainActivity) { loading ->
                showLoading(loading)
            }
        }
    }

    private fun showLoading(loading: Boolean) {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        binding.rvGithubUsers.visibility = if (loading) View.GONE else View.VISIBLE
    }
    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvGithubUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvGithubUsers.addItemDecoration(itemDecoration)
    }

    private fun setGithubData(user: List<DetailUserResponse>) {
        val adapter = GithubUserAdapter()
        adapter.submitList(user)
        binding.rvGithubUsers.adapter = adapter

        adapter.setOnItemClickCallback(object : GithubUserAdapter.ActionAdapter {
            override fun onItemClick(data: DetailUserResponse) {
            }
        })
    }

    private fun settingViewModel() {
        mainViewModel.apply {
            setService(service)
        }
    }
}