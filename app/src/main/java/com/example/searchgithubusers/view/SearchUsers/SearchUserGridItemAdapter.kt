package com.example.searchgithubusers.view.SearchUsers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.searchgithubusers.databinding.ViewListUsersGridItemBinding
import com.example.searchgithubusers.model.network.bean.GithubUser

class SearchUserGridItemAdapter: RecyclerView.Adapter<SearchUserGridItemAdapter.ViewHolder>()  {

    private lateinit var context: Context
    private val listUsers by lazy { ArrayList<GithubUser>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            ViewListUsersGridItemBinding.inflate(
            LayoutInflater.from(context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchUserGridItemAdapter.ViewHolder, position: Int) {
        Glide.with(context).load(listUsers[position].avatarUrl).into(holder.binding.gridListAvatar)
        holder.binding.gridListUsername.text = listUsers[position].login
    }

    override fun getItemCount() = listUsers.size

    fun updateList(list: List<GithubUser>) {
        this.listUsers.clear()
        this.listUsers.addAll(list)
        this.notifyDataSetChanged()
    }

    fun getUserList() = listUsers

    inner class ViewHolder(val binding: ViewListUsersGridItemBinding): RecyclerView.ViewHolder(binding.root)
}