package com.example.assigment2_spotifyclone

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SongData(
    val imgurl:Int,
    val name:String,
    val prodYear:String,
    val listens:Int,
    var price:Int,
    val rate:Float,
    var added:Boolean=false
):Parcelable{

}
