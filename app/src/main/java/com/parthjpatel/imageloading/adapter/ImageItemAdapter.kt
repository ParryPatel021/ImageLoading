package com.parthjpatel.imageloading.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.parthjpatel.imageloading.databinding.ItemImageBinding
import com.parthjpatel.imageloading.image_task.ImageLoader
import com.parthjpatel.imageloading.model.ResponseModel

class ImageItemAdapter : RecyclerView.Adapter<ImageItemAdapter.ImageViewHolder>() {

    private var allImages: ArrayList<ResponseModel> = arrayListOf()

    fun setupData(allImages: ArrayList<ResponseModel>) {
        this.allImages.addAll(allImages)
        notifyDataSetChanged()
    }

    inner class ImageViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setupData(model: ResponseModel) {

            val imageUrl =
                model.thumbnail?.domain + "/" + model.thumbnail?.basePath + "/0/" + model.thumbnail?.key
            ImageLoader(binding.root.context).loadImageFromUrl(imageUrl) { imageBitmap ->
                binding.ivImage.setImageBitmap(imageBitmap)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
        viewType: Int): ImageItemAdapter.ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemImageBinding.inflate(inflater, parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageItemAdapter.ImageViewHolder, position: Int) {
        holder.setupData(allImages[position])
    }

    override fun getItemCount(): Int = allImages.size
}