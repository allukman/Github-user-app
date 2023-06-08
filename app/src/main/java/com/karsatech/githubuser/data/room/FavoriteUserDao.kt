package com.karsatech.githubuser.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.karsatech.githubuser.data.response.DetailUserResponse

@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavoriteUser(favUser: DetailUserResponse)

    @Query("DELETE from detailuserresponse WHERE id_user= :id")
    fun removeFavoriteUser(id: Int)

    @Query("SELECT * from detailuserresponse ORDER BY id_favorite_user ASC")
    fun getListFavoriteUser() :LiveData<List<DetailUserResponse>>

    @Query("SELECT * from detailuserresponse WHERE username= :username")
    fun getFavUserByUsername(username : String) : LiveData<List<DetailUserResponse>>
}