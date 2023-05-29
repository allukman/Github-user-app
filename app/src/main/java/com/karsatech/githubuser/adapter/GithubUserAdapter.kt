package com.karsatech.githubuser.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karsatech.githubuser.data.response.DetailUserResponse
import com.karsatech.githubuser.databinding.ItemUserBinding
import com.karsatech.githubuser.ui.detail.DetailUserActivity
import com.karsatech.githubuser.utils.ui.UiUtils.loadImage

class GithubUserAdapter : ListAdapter<DetailUserResponse, GithubUserAdapter.RecyclerViewHolder>(DIFF_CALLBACK) {

    private lateinit var actionAdapter: ActionAdapter

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DetailUserResponse>() {
            override fun areItemsTheSame(
                oldItem: DetailUserResponse,
                newItem: DetailUserResponse
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DetailUserResponse,
                newItem: DetailUserResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val bind = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return RecyclerViewHolder(bind)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setOnItemClickCallback(onItemClickCallback: ActionAdapter) {
        this.actionAdapter = onItemClickCallback
    }

    inner class RecyclerViewHolder(private val bind: ItemUserBinding) :
        RecyclerView.ViewHolder(bind.root) {
            fun bind(data: DetailUserResponse) {
                with(bind) {
                    tvItemGithubUserUsername.text = data.username
                    civImageGithubUser.loadImage(data.avatarUrl, itemView.context, progressBar)

                    itemView.setOnClickListener {
                        actionAdapter.onItemClick(data)

                        val intent = Intent(itemView.context, DetailUserActivity::class.java)
                        intent.putExtra("User", data)

                        val optionsCompat: ActivityOptionsCompat =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(
                                itemView.context as Activity,
                                Pair(civImageGithubUser, "avatar"),
                                Pair(tvItemGithubUserUsername, "name"),
                            )
                        itemView.context.startActivity(intent, optionsCompat.toBundle())
                    }
                }


            }
        }

    interface ActionAdapter {
        fun onItemClick(data: DetailUserResponse)
    }
}