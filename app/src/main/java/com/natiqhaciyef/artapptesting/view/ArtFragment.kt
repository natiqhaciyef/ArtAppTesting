package com.natiqhaciyef.artapptesting.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.natiqhaciyef.artapptesting.R
import com.natiqhaciyef.artapptesting.adapter.ArtAdapter
import com.natiqhaciyef.artapptesting.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.fragment_art.*
import javax.inject.Inject

class ArtFragment @Inject constructor(
    private val adapter: ArtAdapter
): Fragment() {

    lateinit var viewModel : BaseViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_art, container, false)
    }

    private val swipeCallBack = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val chosenArt = adapter.artList[position]
            viewModel.deleteArt(chosenArt)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(BaseViewModel::class.java)
        observer()

        recyclerViewArt.adapter = adapter
        recyclerViewArt.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(recyclerViewArt)

        floatActionBar.setOnClickListener {
            val action = ArtFragmentDirections.actionArtFragmentToDetailsFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun observer(){
        viewModel.artList.observe(viewLifecycleOwner, Observer{
            adapter.artList = it
        })
    }
}