package com.karsatech.githubuser.utils.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.karsatech.githubuser.R
import de.hdodenhof.circleimageview.CircleImageView

object UiUtils {
    fun CircleImageView.loadImage(url : String?, context : Context, progressBar : ProgressBar) {
        Glide.with(context)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false                        }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false                        }

            })
            .placeholder(R.drawable.github_logo)
            .error(R.drawable.github_logo)
            .dontAnimate()
            .into(this)
    }
}