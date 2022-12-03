package com.example.finedust

import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class RecyclerVierAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // adapter에 들어갈 list 입니다.

    private val listData: ArrayList<String> = ArrayList<String>()
    private val imageList: ArrayList<Int> = ArrayList<Int>()
    lateinit var mItemClickListener: OnItemClickEventListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view =  inflater.inflate(com.example.finedust.R.layout.grid_item,parent,false)
        return ViewHolder(view)
    }

    interface OnItemClickEventListener {
        fun onItemClick(v: View)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).onBind(listData[position],imageList[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    fun addItem(title: String, image: Int) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(title)
        imageList.add(image)
    }

    fun setOnClickListener(onClickListener: OnItemClickEventListener) {
        mItemClickListener = onClickListener
    }
}


