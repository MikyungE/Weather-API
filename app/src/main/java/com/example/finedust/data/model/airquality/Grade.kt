package com.example.finedust.data.model.airquality

import androidx.annotation.ColorRes
import com.example.finedust.R
import com.google.gson.annotations.SerializedName

enum class Grade(val label :String ,
                 val emoji :String ,
                @ColorRes val colorResId : Int
                 ) {

    @SerializedName("1")
    AWFUL("매우나쁨","\uD83C\uDF2B",R.drawable.awful),
    @SerializedName("2")
    BAD("나쁨","☁️",R.drawable.bad),
    @SerializedName("3")
    NORMAL("보통","\uD83C\uDF25️",R.drawable.normal),
    @SerializedName("4")
    GOOD("좋음","☀️",R.drawable.good),

    UNKNOWN("미측정","🧐",R.color.gray);

    override fun toString(): String {
        return "${label} ${emoji}"
    }
}