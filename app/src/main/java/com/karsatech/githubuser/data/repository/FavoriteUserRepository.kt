package com.karsatech.githubuser.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.karsatech.githubuser.data.response.DetailUserResponse
import com.karsatech.githubuser.data.room.FavoriteUserDao
import com.karsatech.githubuser.data.room.FavoriteUserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {

    private val mFavoriteUserDao : FavoriteUserDao
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getListFavoriteUser() : LiveData<List<DetailUserResponse>> = mFavoriteUserDao.getListFavoriteUser()

    fun insertFavoriteUser(favUser : DetailUserResponse) {
        executorService.execute { mFavoriteUserDao.insertFavoriteUser(favUser) }
    }

    fun removeFavoriteUser(id : Int) {
        executorService.execute { mFavoriteUserDao.removeFavoriteUser(id) }
    }

    fun getFavoriteUserByUsername(username : String) : LiveData<List<DetailUserResponse>> = mFavoriteUserDao.getFavUserByUsername(username)

}