package com.natiqhaciyef.artapptesting.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.natiqhaciyef.artapptesting.R
import com.natiqhaciyef.artapptesting.util.Status
import com.natiqhaciyef.artapptesting.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.fragment_details.*
import javax.inject.Inject

class DetailsFragment @Inject constructor(
    val glide: RequestManager
) : Fragment() {

    lateinit var viewModel: BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(BaseViewModel::class.java)
        observer()

        saveButtonDetails.setOnClickListener {
            viewModel.createArt(
                artNameDetails.text.toString(),
                artistNameDetails.text.toString(),
                artYearDetails.text.toString().toInt()
            )
        }

        artImageViewDetails.setOnClickListener {
            val action = DetailsFragmentDirections.actionDetailsFragmentToImageSelectFragment()
            findNavController().navigate(action)
        }

        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }

    private fun observer() {
        viewModel.chosenImageURL.observe(viewLifecycleOwner, Observer {
            glide.load(it).into(artImageViewDetails)
        })

        viewModel.insertArtMessageString.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message?: "Error", Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMessage()
                }
                Status.LOADING -> {

                }
            }
        })
    }
}