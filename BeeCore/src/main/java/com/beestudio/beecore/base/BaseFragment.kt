package com.beestudio.beecore.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<V : ViewBinding> : Fragment() {
    private var _binding: V? = null
    val binding: V
        get() = _binding
            ?: throw RuntimeException("Should only use binding after onCreateView and before onDestroyView")
    private var _view: View? = null
    
    open fun onStarted(savedInstanceState: Bundle?){}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if(_view == null) {
            _binding = getBinding(inflater, container)
            onStarted(savedInstanceState)
            _view = binding.root
        }
        return binding.root
    }
}