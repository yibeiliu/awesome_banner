package tech.yibeiliu.awesome_banner.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BannerAdapter<DATA, VH : RecyclerView.ViewHolder>(private val dataList: List<DATA> = ArrayList()) :
    RecyclerView.Adapter<VH>() {

    private var increaseNum = 2
    val realCount = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return onCreateHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return dataList.size + increaseNum
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val data: DATA = dataList[getRealPosition(increaseNum, position, realCount)]
        onBindHolder(holder, data, position)
    }

    fun setIncreaseNum(increaseNum: Int) {
        this.increaseNum = increaseNum
    }

    abstract fun onCreateHolder(parent: ViewGroup, viewType: Int): VH
    abstract fun onBindHolder(holder: VH, data: DATA, position: Int)
}

fun getRealPosition(increaseNum: Int, position: Int, realCount: Int): Int {
    if (increaseNum == 0) {
        return position
    }
    return when (position) {
        0 -> realCount - 1
        realCount + 1 -> 0
        else -> position - 1
    }
}