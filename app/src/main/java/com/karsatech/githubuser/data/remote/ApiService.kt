package com.karsatech.githubuser.data.remote

import com.karsatech.githubuser.data.response.DetailUserResponse
import com.karsatech.githubuser.data.response.SearchUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/users")
    fun getAllUsers(): Call<List<DetailUserResponse>>

    @GET("/search/users")
    fun searchUser(@Query("q") query: String): Call<SearchUserResponse>

    @GET("/users/{username}")
    fun getDetailUser(@Path("username") username : String) : Call<DetailUserResponse>

    @GET("/users/{username}/{type}")
    fun getListUserByType(@Path("username") username : String,
                          @Path("type") type : String
    ) : Call<List<DetailUserResponse>>

}