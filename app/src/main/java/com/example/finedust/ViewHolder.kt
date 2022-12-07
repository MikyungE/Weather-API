package com.example.finedust

import android.content.Intent
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.time.Instant


class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tv_movie_title: TextView
    lateinit var image : LinearLayout
    init {
        tv_movie_title = itemView.findViewById(R.id.item_name)
        image = itemView.findViewById(R.id.gridImage)
    }
    fun onBind(title:String,backgroundImage :Int) {
        tv_movie_title.setText(title)
        image.setBackgroundResource(backgroundImage)
        itemView.setOnClickListener({
            val next = Intent(itemView.context, Device::class.java)
            itemView.context.startActivity(next)
            // 다음 페이지 지정 후, itemView 이벤트 발생시, 다음 activity 띄워줌.
            // Toast.makeText(itemView.context,title,Toast.LENGTH_SHORT).show()
        })
    }
}