package com.natiqhaciyef.artapptesting.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.natiqhaciyef.artapptesting.R
import com.natiqhaciyef.artapptesting.adapter.ImageApiAdapter
import com.natiqhaciyef.artapptesting.util.Status
import com.natiqhaciyef.artapptesting.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.fragment_image_select.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageSelectFragment @Inject constructor(
    private val adapter: ImageApiAdapter
): Fragment() {

    lateinit var viewModel: BaseViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_select, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(BaseViewModel::class.java)
        observer()

        var job: Job? = null
        searchTextBar.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                if (it.toString().isNotEmpty())
                    viewModel.searchImage(it.toString())
            }
        }
        imageSelectRecyclerView.adapter = adapter
        imageSelectRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        adapter.onClick {
            findNavController().popBackStack()
            viewModel.setChosenImage(it)
        }
    }

    private fun observer(){
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    val urlList = it.data?.let { it.hits }?.map { result ->
                        result.previewURL
                    }
                    adapter.imageList = urlList ?: listOf()
                    circularProgressBar.visibility = View.GONE
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_SHORT).show()
                    circularProgressBar.visibility = View.GONE
                }
                Status.LOADING -> {
                    circularProgressBar.visibility = View.VISIBLE
                }
            }
        })
    }
}