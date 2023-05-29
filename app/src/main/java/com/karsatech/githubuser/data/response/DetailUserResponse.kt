package com.karsatech.githubuser.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
 class DetailUserResponse(

	@field:SerializedName("login")
	val username: String? = "",

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = "",

	@field:SerializedName("id")
	val id: Int,

	val name : String? = "",

	val followers : Int? = 0,

	val following : Int? = 0,

	@field:SerializedName("public_repos")
	val publicRepository: String? = "",

	val location: String? = "",

	val company: String? = ""

) : Parcelable
