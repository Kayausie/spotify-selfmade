package com.example.assigment2_spotifyclone

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.lang.Thread.State


class SongModel : ViewModel(){
    private val songs: MutableList<SongData> = mutableListOf(
        SongData(R.drawable.tyga,"Money","2013",13,50,3.5f),
        SongData(R.drawable.trais,"Drill","2015",19,25,4.6f),
        SongData(R.drawable.wxrdie,"Tri Tra","2016",25,30,4.7f),
        SongData(R.drawable.charlie,"Attention","2022",70,123,4.5f)
    )
    private val _indexState= MutableStateFlow(0)
    val indexState: StateFlow<Int> =_indexState.asStateFlow()
    fun reset(){
        _indexState.value=0
        for (i in songs){
            i.added=false
        }

    }
    fun next(){
        if(_indexState.value==3){
            reset()
        }
        else{
            _indexState.value+=1
        }
    }
    fun viewvalue():SongData {
        return songs[_indexState.value]
    }
    init {
        _indexState.value=0
    }
    fun deluxepack(): SongData {
        val deluxesong = viewvalue()
        // Create a new SongData object with the modified price
        return deluxesong.copy(price = deluxesong.price + 10)
    }

    fun premiumpack(): SongData {
        val premiumsong = viewvalue()
        // Create a new SongData object with the modified price
        return premiumsong.copy(price = premiumsong.price + 20)
    }
}
fun setprice(song:SongData,price:Int){
    song.price=price
}