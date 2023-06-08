package com.karsatech.githubuser.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.karsatech.githubuser.adapter.GithubUserAdapter
import com.karsatech.githubuser.data.response.DetailUserResponse
import com.karsatech.githubuser.databinding.ActivityFavoriteBinding
import com.karsatech.githubuser.ui.factory.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(
            application
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecyclerView()
        subscribeFavoriteViewModel()
        setOnClick()

    }

    private fun setOnClick() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun subscribeFavoriteViewModel() {
        favoriteViewModel.getAllFavoriteUser().observe(this) { listUser ->
            if (listUser.isNotEmpty()) {
                setGithubData(listUser)
                binding.rvGithubUsers.visibility = View.VISIBLE
                binding.tvError.visibility = View.GONE
            } else {
                binding.rvGithubUsers.visibility = View.GONE
                binding.tvError.visibility = View.VISIBLE
            }
        }
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
}