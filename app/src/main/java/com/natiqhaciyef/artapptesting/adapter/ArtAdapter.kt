package com.natiqhaciyef.artapptesting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.natiqhaciyef.artapptesting.databinding.RecyclerArtItemRowBinding
import com.natiqhaciyef.artapptesting.roomdb.ArtModel
import javax.inject.Inject

class ArtAdapter @Inject constructor(
    val glide: RequestManager
) : RecyclerView.Adapter<ArtAdapter.ArtHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<ArtModel>() {
        override fun areItemsTheSame(oldItem: ArtModel, newItem: ArtModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ArtModel, newItem: ArtModel): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncDiffUtil = AsyncListDiffer(this, diffUtil)
    var artList: List<ArtModel>
        get() = asyncDiffUtil.currentList
        set(value) = asyncDiffUtil.submitList(value)

    class ArtHolder(val binding: RecyclerArtItemRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtHolder {
        val binding =
            RecyclerArtItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtHolder, position: Int) {
        val element = holder.binding
        val item = artList[position]

        element.apply {
            artNameTextView.text = "Name : ${item.artName}"
            artistNameTextView.text = "Artist name : ${item.artistName}"
            artYearTextView.text = "Year : ${item.artYear}"
            glide.load(item.imageUrl).into(artItemImageView)
        }
    }

    override fun getItemCount(): Int {
        return artList.size
    }
}