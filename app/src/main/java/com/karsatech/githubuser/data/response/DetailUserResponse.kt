package com.karsatech.githubuser.data.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
 class DetailUserResponse(

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id_favorite_user")
	var idFavoriteUser: Int = 0,

	@ColumnInfo(name = "username")
	@field:SerializedName("login")
	val username: String? = "",

	@ColumnInfo(name = "avatar_url")
	@field:SerializedName("avatar_url")
	val avatarUrl: String? = "",

	@ColumnInfo(name = "id_user")
	@field:SerializedName("id")
	val id: Int,

	@ColumnInfo(name = "name")
	val name : String? = "",

	@ColumnInfo(name = "followers")
	val followers : Int? = 0,

	@ColumnInfo(name = "following")
	val following : Int? = 0,

	@ColumnInfo(name = "public_repos")
	@field:SerializedName("public_repos")
	val publicRepository: String? = "",

	@ColumnInfo(name = "location")
	val location: String? = "",

	@ColumnInfo(name = "company")
	val company: String? = ""

) : Parcelable
