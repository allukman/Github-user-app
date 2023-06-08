package com.karsatech.githubuser.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.switchmaterial.SwitchMaterial
import com.karsatech.githubuser.R
import com.karsatech.githubuser.adapter.GithubUserAdapter
import com.karsatech.githubuser.data.remote.ApiConfig
import com.karsatech.githubuser.data.remote.ApiService
import com.karsatech.githubuser.data.response.DetailUserResponse
import com.karsatech.githubuser.databinding.ActivityMainBinding
import com.karsatech.githubuser.ui.factory.ThemeViewModelFactory
import com.karsatech.githubuser.ui.favorite.FavoriteActivity
import com.karsatech.githubuser.utils.datastore.SettingPreferences
import com.karsatech.githubuser.utils.datastore.dataStore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var service: ApiService
    private lateinit var switchTheme: SwitchMaterial

    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        switchTheme = binding.switchTheme

        service = ApiConfig.getApiClient()!!.create(ApiService::class.java)

        settingViewModel()
        setRecyclerView()
        setSearchBar()
        subscribeMainViewModel()
        subscribeThemeViewModel()

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.favorite -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun setSearchBar() {
        binding.apply {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchBar.text = searchView.text
                searchView.hide()

                if (searchView.text.toString().isBlank()) {
                    mainViewModel.getAllUsers()
                } else {
                    mainViewModel.searchUsers(searchView.text.toString())
                }

                false
            }
        }
    }

    private fun subscribeMainViewModel() {
        binding.apply {

            mainViewModel.listUser.observe(this@MainActivity) { listUser ->
                if (listUser.isNotEmpty()) {
                    setGithubData(listUser)
                    binding.tvError.visibility = View.GONE
                    binding.rvGithubUsers.visibility = View.VISIBLE
                } else {
                    binding.tvError.visibility = View.VISIBLE
                    binding.rvGithubUsers.visibility = View.GONE
                }
            }

            mainViewModel.isLoading.observe(this@MainActivity) { loading ->
                showLoading(loading)
            }
        }
    }

    private fun subscribeThemeViewModel() {
        val pref = SettingPreferences.getInstance(application.dataStore)
        val themeViewModel = ViewModelProvider(this, ThemeViewModelFactory(pref))[ThemeViewModel::class.java]

        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            themeViewModel.saveThemeSetting(isChecked)
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