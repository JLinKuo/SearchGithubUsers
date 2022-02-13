package com.example.searchgithubusers.view.SearchUsers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.searchgithubusers.databinding.ViewListUsersItemBinding
import com.example.searchgithubusers.model.network.bean.GithubUser

class SearchUserItemAdapter(
    private val onItemClicked: (GithubUser) -> Unit
): RecyclerView.Adapter<SearchUserItemAdapter.ViewHolder>() {

    private lateinit var context: Context
    private val listUsers by lazy { ArrayList<GithubUser>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(ViewListUsersItemBinding.inflate(
            LayoutInflater.from(context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(listUsers[position].avatarUrl).into(holder.binding.avatar)
        holder.binding.username.text = listUsers[position].login

        holder.itemView.setOnClickListener {
            onItemClicked(listUsers[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }

    fun updateList(list: List<GithubUser>) {
        this.listUsers.clear()
        this.listUsers.addAll(list)
        this.notifyDataSetChanged()
    }

    fun getUserList() = listUsers

    inner class ViewHolder(val binding: ViewListUsersItemBinding): RecyclerView.ViewHolder(binding.root)
}