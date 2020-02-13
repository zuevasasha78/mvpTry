package com.example.mvptry.common

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvptry.R
import kotlinx.android.synthetic.main.user_item.view.*

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserHolder>() {

    var dataUser: MutableList<User> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserHolder(view)
    }

    override fun getItemCount(): Int {
        return dataUser.size
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(dataUser[position])
    }

    fun setData(users: List<User>) {
        dataUser.clear()
        dataUser.addAll(users)
        notifyDataSetChanged()
        Log.d("qweee", "size  = $itemCount")
    }

    class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: User) {
            itemView.text1.text =
                String.format(
                    "id: %s, name: %s, email: %s",
                    user.id,
                    user.name,
                    user.email
                )
        }
    }
}