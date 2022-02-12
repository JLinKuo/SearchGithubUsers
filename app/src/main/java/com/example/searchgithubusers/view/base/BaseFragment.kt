package com.example.searchgithubusers.view.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.searchgithubusers.view.main.MainActivity

abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding>: Fragment() {

    protected var TAG = javaClass.simpleName
    protected lateinit var viewModel: VM
    protected lateinit var binding: VB

    protected lateinit var activity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is MainActivity) {
            activity = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getFragmentBinding(inflater, container)

        val viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[getViewModel()]

        return binding.root
    }

    abstract fun getViewModel(): Class<VM>
    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): VB
}