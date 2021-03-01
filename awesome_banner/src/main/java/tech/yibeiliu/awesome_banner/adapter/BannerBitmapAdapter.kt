package tech.yibeiliu.awesome_banner.adapter

import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class BannerBitmapAdapter(private val dataList: List<Bitmap>) :
    BannerAdapter<Bitmap, BannerBitmapAdapter.BitmapViewHolder>(dataList) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BitmapViewHolder {
        val iv = ImageView(parent.context)
        iv.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        iv.scaleType = ImageView.ScaleType.CENTER_CROP
        return BitmapViewHolder(iv)
    }

    override fun onBindHolder(holder: BitmapViewHolder, data: Bitmap, position: Int) {
        holder.bind(data)
    }

    inner class BitmapViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iv: ImageView = itemView as ImageView
        fun bind(data: Bitmap) {
            iv.setImageBitmap(data)
        }
    }
}