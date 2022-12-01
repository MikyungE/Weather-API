package com.example.finedust.data.model.airquality

import androidx.annotation.ColorRes
import com.example.finedust.R
import com.google.gson.annotations.SerializedName

enum class Grade(val label :String ,
                 val emoji :String ,
                @ColorRes val colorResId : Int
                 ) {

    @SerializedName("1")
    GOOD("좋음","☀️",R.drawable.good),
    @SerializedName("2")
    NORMAL("보통","\uD83C\uDF25️",R.drawable.normal),
    @SerializedName("3")
    BAD("나쁨","☁️",R.drawable.bad),
    @SerializedName("4")
    AWFUL("매우나쁨","\uD83C\uDF2B",R.drawable.awful),

    UNKNOWN("미측정","🧐",R.color.gray);

    override fun toString(): String {
        return "${label} ${emoji}"
    }

}