package com.example.finedust

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Device : AppCompatActivity() {
    var adapter = DeviceAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)
        init()
        getData()
    }
    fun init(){
        val recyclerView = findViewById<RecyclerView>(R.id.device_recycle)
        val gridLayoutManager = GridLayoutManager(this,2)
        recyclerView.setLayoutManager(gridLayoutManager)
        recyclerView.setAdapter(adapter)
        adapter.setOnClickListener(object : DeviceAdapter.OnItemClickEventListener {
            override fun onItemClick(v: View) {

            }
        })
    }
    fun getData(){  // 어댑터에 아이템 함수를 이용하여 하나씩 추가.
        adapter.addItem("창문입니다~",R.color.white)
        adapter.addItem("창문이에유~",R.color.white)
        adapter.addItem("창문입니다~",R.color.white)
        adapter.addItem("창문이에유~",R.color.white)
    }
}