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


class DeviceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
//            ItemList.nameList.add(title);
//            ItemList.nameList.add(tv_movie_title.getText().toString());
//            ItemList.imageList.add(backgroundImage);
            val next = Intent(itemView.context,MainActivity::class.java)
            next.putExtra("title",tv_movie_title.getText().toString());
            next.putExtra("image",backgroundImage)
            // 넘어가는 페이지에 key value 형식으로 값을 넘겨줌.
            // 앞에 쓰이는 변수를 getStringExtra("title")로 받을 수 있음.
            itemView.context.startActivity(next)
        })
    }
}