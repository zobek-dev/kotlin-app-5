package com.example.alejandra_alvarez_20241212_herpm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.graphics.BitmapFactory


class ImageAdapter(
    private val imagePaths: List<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.widgetImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.widget_item_layout, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imagePath = imagePaths[position]
        val bitmap = BitmapFactory.decodeFile(imagePath)
        holder.imageView.setImageBitmap(bitmap)

        holder.imageView.setOnClickListener {
            onClick(imagePath)
        }
    }

    override fun getItemCount() = imagePaths.size
}


