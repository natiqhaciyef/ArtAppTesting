package com.natiqhaciyef.artapptesting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.natiqhaciyef.artapptesting.databinding.RecyclerArtItemRowBinding
import com.natiqhaciyef.artapptesting.databinding.RecyclerImageSelectRowBinding
import javax.inject.Inject

class ImageApiAdapter @Inject constructor(
    val glide: RequestManager
) : RecyclerView.Adapter<ImageApiAdapter.ImageViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncDiffUtil = AsyncListDiffer(this, diffUtil)
    var imageList: List<String>
        get() = asyncDiffUtil.currentList
        set(value) = asyncDiffUtil.submitList(value)


    class ImageViewHolder(val binding: RecyclerImageSelectRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var onItemClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = RecyclerImageSelectRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageViewHolder(binding)
    }

    fun onClick(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val element = holder.binding
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(imageList[position])
            }
        }
        element.apply {
            glide.load(imageList[position]).into(recyclerViewImage)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}