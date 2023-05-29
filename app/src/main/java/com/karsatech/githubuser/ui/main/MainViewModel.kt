package com.karsatech.githubuser.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karsatech.githubuser.data.remote.ApiService
import com.karsatech.githubuser.data.response.DetailUserResponse
import com.karsatech.githubuser.data.response.SearchUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection
import kotlin.coroutines.CoroutineContext

class MainViewModel : ViewModel(), CoroutineScope {

    private lateinit var service: ApiService

    private val _listUser = MutableLiveData<List<DetailUserResponse>>()
    val listUser: LiveData<List<DetailUserResponse>> = _listUser

    private val _searchUser = MutableLiveData<SearchUserResponse>()
    val searchUser: LiveData<SearchUserResponse> = _searchUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: ApiService) {
        this.service = service
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        getAllUsers()
    }

    fun getAllUsers() {
        _isLoading.value = true
        launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    service.getAllUsers()
                }

                result.enqueue(object : Callback<List<DetailUserResponse>> {
                    override fun onResponse(
                        call: Call<List<DetailUserResponse>>,
                        response: Response<List<DetailUserResponse>>
                    ) {
                        _isLoading.value = false
                        if (response.isSuccessful) {
                            if (response.code() == HttpURLConnection.HTTP_OK) {
                                _listUser.value = response.body()
                            } else {
                                Log.e(TAG, "onFailure: ${response.message()}")
                            }
                        } else {
                            Log.e(TAG, "onFailure: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<List<DetailUserResponse>>, t: Throwable) {
                        _isLoading.value = false
                        Log.e(TAG, "onFailure: ${t.message.toString()}")
                    }

                })

            } catch (e: Throwable) {
                Log.e(TAG, "onFailure: ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
    }

    fun searchUsers(q: String) {
        _isLoading.value = true
        launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    service.searchUser(q)
                }

                result.enqueue(object : Callback<SearchUserResponse> {
                    override fun onResponse(
                        call: Call<SearchUserResponse>,
                        response: Response<SearchUserResponse>
                    ) {
                        _isLoading.value = false
                        if (response.isSuccessful) {
                            if (response.code() == HttpURLConnection.HTTP_OK) {
                                _searchUser.value = response.body()
                                _listUser.value = response.body()?.items
                            } else {
                                Log.e(TAG, "onFailure: ${response.message()}")
                            }
                        } else {
                            Log.e(TAG, "onFailure: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                        _isLoading.value = false
                        Log.e(TAG, "onFailure: ${t.message.toString()}")
                    }

                })

            } catch (e: Throwable) {
                Log.e(TAG, "onFailure: ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
    }
}