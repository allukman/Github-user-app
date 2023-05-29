package com.karsatech.githubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karsatech.githubuser.data.remote.ApiService
import com.karsatech.githubuser.data.response.DetailUserResponse
import com.karsatech.githubuser.data.response.SearchUserResponse
import com.karsatech.githubuser.ui.main.MainViewModel
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

class DetailViewModel: ViewModel(), CoroutineScope {

    private lateinit var service: ApiService

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _followers = MutableLiveData<List<DetailUserResponse>>()
    val followers: LiveData<List<DetailUserResponse>> = _followers

    private val _following = MutableLiveData<List<DetailUserResponse>>()
    val following: LiveData<List<DetailUserResponse>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _followingIsLoading = MutableLiveData<Boolean>()
    val followingIsLoading: LiveData<Boolean> = _followingIsLoading

    private val _followersIsLoading = MutableLiveData<Boolean>()
    val followersIsLoading: LiveData<Boolean> = _followersIsLoading

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: ApiService) {
        this.service = service
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getDetailUsers(name: String) {
        _isLoading.value = true
        launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    service.getDetailUser(name)
                }

                result.enqueue(object : Callback<DetailUserResponse> {
                    override fun onResponse(
                        call: Call<DetailUserResponse>,
                        response: Response<DetailUserResponse>
                    ) {
                        _isLoading.value = false
                        if (response.isSuccessful) {
                            if (response.code() == HttpURLConnection.HTTP_OK) {
                                _detailUser.value = response.body()
                            } else {
                                Log.e(TAG, "onFailure: ${response.message()}")
                            }
                        } else {
                            Log.e(TAG, "onFailure: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
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
    fun getFollowers(name: String, type: String) {
        _followersIsLoading.value = true
        launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    service.getListUserByType(name, type)
                }

                result.enqueue(object : Callback<List<DetailUserResponse>> {
                    override fun onResponse(
                        call: Call<List<DetailUserResponse>>,
                        response: Response<List<DetailUserResponse>>
                    ) {
                        _followersIsLoading.value = false
                        if (response.isSuccessful) {
                            if (response.code() == HttpURLConnection.HTTP_OK) {
                                _followers.value = response.body()
                                Log.d("LUKMAN", "followers viewmodel ${response.body()}")
                            } else {
                                Log.e(TAG, "onFailure: ${response.message()}")
                            }
                        } else {
                            Log.e(TAG, "onFailure: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<List<DetailUserResponse>>, t: Throwable) {
                        _followersIsLoading.value = false
                        Log.e(TAG, "onFailure: ${t.message.toString()}")
                    }

                })

            } catch (e: Throwable) {
                Log.e(TAG, "onFailure: ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
    }

    fun getFollowing(name: String, type: String) {
        _followingIsLoading.value = true
        launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    service.getListUserByType(name, type)
                }

                result.enqueue(object : Callback<List<DetailUserResponse>> {
                    override fun onResponse(
                        call: Call<List<DetailUserResponse>>,
                        response: Response<List<DetailUserResponse>>
                    ) {
                        _followingIsLoading.value = false
                        if (response.isSuccessful) {
                            if (response.code() == HttpURLConnection.HTTP_OK) {
                                _following.value = response.body()
                                Log.d("LUKMAN", "following viewmodel ${response.body()}")
                            } else {
                                Log.e(TAG, "onFailure: ${response.message()}")
                            }
                        } else {
                            Log.e(TAG, "onFailure: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<List<DetailUserResponse>>, t: Throwable) {
                        _followingIsLoading.value = false
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