package com.beestudio.beecore.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.beestudio.beecore.R

abstract class BaseFragmentBinding<V : ViewBinding> : Fragment() {
    private var _view: View? = null
    private var _binding: V? = null
    val binding: V
        get() = _binding
            ?: throw RuntimeException("Should only use binding after onCreateView and before onDestroyView")


    abstract fun onStarted(savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_view == null) {
            _binding = getBinding(inflater, container)
            onStarted(savedInstanceState)
            setupBinding(binding)
            _view = binding.root
        }
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
      //  _binding = null
    }

    open fun setupBinding(binding: V){}
}