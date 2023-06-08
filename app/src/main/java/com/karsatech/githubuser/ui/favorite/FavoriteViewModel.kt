package com.karsatech.githubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.karsatech.githubuser.data.repository.FavoriteUserRepository
import com.karsatech.githubuser.data.response.DetailUserResponse

class FavoriteViewModel(application: Application) : ViewModel() {

    private val mFavoriteUserRepository : FavoriteUserRepository = FavoriteUserRepository(application)

    fun insertFavoriteUser(favUser: DetailUserResponse) {
        mFavoriteUserRepository.insertFavoriteUser(favUser)
    }

    fun removeFavoriteUser(id: Int) {
        mFavoriteUserRepository.removeFavoriteUser(id)
    }

    fun getAllFavoriteUser() : LiveData<List<DetailUserResponse>> = mFavoriteUserRepository.getListFavoriteUser()

    fun getFavoriteUserByUsername(username : String) : LiveData<List<DetailUserResponse>> = mFavoriteUserRepository.getFavoriteUserByUsername(username)
}