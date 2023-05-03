package com.marcelodonato.desafiomblabs.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewbinding.ViewBinding
import com.marcelodonato.desafiomblabs.R
import org.koin.android.viewmodel.ext.android.getViewModel
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

abstract class BaseFragment<V : BaseViewModel> : Fragment() {

    abstract val binding: ViewBinding

    val viewModel: V by lazy { getViewModel(clazz = viewModelClass()) }

    private val toolbarTitle = MutableLiveData<String>()

    abstract fun initView()

    open fun onActivityBackPressed(): Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    fun onTitleChanged(): LiveData<String> = toolbarTitle

    fun setTitle(title: String) = toolbarTitle.postValue(title)

    @Suppress("UNCHECKED_CAST")
    private fun viewModelClass(): KClass<V> {
        return ((javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<V>).kotlin
    }

}