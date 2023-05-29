package com.karsatech.githubuser.data.response

import com.google.gson.annotations.SerializedName

data class SearchUserResponse (
    @SerializedName("total_count") val totalCount: Int? = 0,
    val items: List<DetailUserResponse>
)