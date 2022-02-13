package com.example.searchgithubusers.view.SearchUsers

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.searchgithubusers.R
import com.example.searchgithubusers.databinding.FragmentSearchUsersBinding
import com.example.searchgithubusers.model.network.Resource
import com.example.searchgithubusers.model.network.bean.GithubUser
import com.example.searchgithubusers.view.base.BaseFragment
import com.example.searchgithubusers.view.base.handleApiError

/**
 * A simple [Fragment] subclass.
 */
class SearchUsersFragment : BaseFragment<SearchUsersViewModel, FragmentSearchUsersBinding>() {

    private val listAdapter by lazy { SearchUserItemAdapter() }
    private val linearLayoutManager by lazy { LinearLayoutManager(activity) }

    private val listGridAdapter by lazy { SearchUserGridItemAdapter() }
    private val gridLayoutManager by lazy { GridLayoutManager(activity, 2) }

    private val listUsers by lazy { ArrayList<GithubUser>() }

    private val listScrollListener = object: RecyclerView.OnScrollListener() {
        private var firstVisibleItemPosition = 0
        private var lastVisibleItemPosition = 0

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            val sizeThreshold = if(isLinearListView()) {
                listAdapter.getUserList().size
            } else {
                listGridAdapter.getUserList().size
            }

            if(!viewModel.isLoading && newState == RecyclerView.SCROLL_STATE_IDLE &&
                lastVisibleItemPosition >= sizeThreshold - 10) {

                viewModel.isLoading = true
                viewModel.searchUsers(binding.query.text.toString())
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            firstVisibleItemPosition = if(isLinearListView()) {
                linearLayoutManager.findFirstVisibleItemPosition()
            } else {
                gridLayoutManager.findFirstVisibleItemPosition()
            }

            lastVisibleItemPosition = if(isLinearListView()) {
                linearLayoutManager.findFirstVisibleItemPosition()
            } else {
                gridLayoutManager.findLastVisibleItemPosition()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setView()
        setListener()
        setObserver()
    }

    private fun setView() {
        // 預設是LinearList
        setLinearListView()
    }

    private fun setListener() {
        binding.listView.addOnScrollListener(listScrollListener)

        binding.searchUsers.setOnClickListener {
            viewModel.nextPage = 1
            viewModel.isLoading = true
            viewModel.searchUsers(binding.query.text.toString())
            hideSoftwareKeyboard()
        }

        binding.listModeChange.setOnClickListener {
            if(isLinearListView()) {
                Glide.with(this).load(R.drawable.ic_list_view).into(binding.listModeChange)
                setGridListView()
            } else {
                Glide.with(this).load(R.drawable.ic_grid_view).into(binding.listModeChange)
                setLinearListView()
            }
        }
    }

    private fun setLinearListView() {
        val firstPos = gridLayoutManager.findFirstVisibleItemPosition()
        binding.listView.adapter = listAdapter
        listAdapter.updateList(listUsers)
        binding.listView.layoutManager = linearLayoutManager
        linearLayoutManager.scrollToPosition(firstPos)
        binding.listView.tag = "LinearLayoutManager"
    }

    private fun setGridListView() {
        val firstPos = linearLayoutManager.findFirstVisibleItemPosition()
        binding.listView.adapter = listGridAdapter
        listGridAdapter.updateList(listUsers)
        binding.listView.layoutManager = gridLayoutManager
        gridLayoutManager.scrollToPosition(firstPos)
        binding.listView.tag = "GridLayoutManager"
    }

    private fun hideSoftwareKeyboard() {
        activity.currentFocus?.let { view ->
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun setObserver() {
        viewModel.searchUsersRepository.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    viewModel.isLoading = false

                    if(viewModel.nextPage == 1) {
                        listUsers.clear()
                    }

                    viewModel.nextPage += 1

                    listUsers.addAll(it.value.listItems)

                    if(isLinearListView()) {
                        listAdapter.updateList(listUsers)
                    } else {
                        listGridAdapter.updateList(listUsers)
                    }

                    activity.dismissProgressBar()
                    binding.listModeChange.visibility = View.VISIBLE
                }
                is Resource.Failure -> handleApiError(it)
                is Resource.Loading -> activity.showProgressBar(true)
            }
        }
    }

    private fun isLinearListView() = binding.listView.tag == "LinearLayoutManager"

    override fun getViewModel() = SearchUsersViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchUsersBinding  = FragmentSearchUsersBinding.inflate(inflater, container, false)
}