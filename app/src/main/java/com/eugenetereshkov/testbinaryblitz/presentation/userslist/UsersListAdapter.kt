package com.eugenetereshkov.testbinaryblitz.presentation.userslist

import android.graphics.Bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.eugenetereshkov.testbinaryblitz.R
import com.eugenetereshkov.testbinaryblitz.entity.User
import com.eugenetereshkov.testbinaryblitz.extentions.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_user.*

/**
 * Created by eugenetereshkov on 24.10.2017.
 */
class UsersListAdapter(
        private val clickListener: (user: User) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserViewHolder(parent.inflate(R.layout.item_user), clickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UserViewHolder).bind(items[position])
    }

    override fun getItemCount() = items.size

    class UserViewHolder(override val containerView: View, clickListener: (user: User) -> Unit) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {
        private lateinit var user: User

        init {
            containerView.setOnClickListener { clickListener.invoke(user) }
        }

        fun bind(user: User) {
            this.user = user
            nameTextView.text = "${user.firstName} ${user.lastName}"
            user.email?.let { emailTextView.text = it }

            Glide.with(itemView.context)
                    .load(user.avatarURL)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.drawable.circle)
                    .error(R.drawable.circle)
                    .into(object : BitmapImageViewTarget(imageView) {
                        override fun setResource(resource: Bitmap?) {
                            resource?.let {
                                RoundedBitmapDrawableFactory.create(view.resources, it).run {
                                    this.isCircular = true
                                    imageView.setImageDrawable(this)
                                }
                            }
                        }
                    })

        }
    }

    fun updateItems(users: List<User>) {
        items.clear()
        items.addAll(users)
        notifyDataSetChanged()
    }
}