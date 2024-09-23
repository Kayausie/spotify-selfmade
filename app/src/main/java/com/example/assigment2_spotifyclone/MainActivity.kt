package com.example.assigment2_spotifyclone

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val viewModel:SongModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setproduct(viewModel.viewvalue())
        val nextbutton=findViewById<Button>(R.id.next)
        nextbutton.setOnClickListener{
            viewModel.next()
            setproduct(viewModel.viewvalue())
        }

    }
    fun setproduct(song:SongData):Unit{
        val productView=findViewById<ImageView>(R.id.productView).setImageDrawable(AppCompatResources.getDrawable(this,song.imgurl))
        val trackname= findViewById<TextView>(R.id.trackname).setText(song.name)
        val prodYear=findViewById<TextView>(R.id.prodyear).setText(song.prodYear)
        val listens=findViewById<TextView>(R.id.listens).setText(song.listens.toString())
        val rate = findViewById<androidx.appcompat.widget.AppCompatRatingBar>(R.id.rate)
        rate.rating=song.rate
        val price = findViewById<TextView>(R.id.price).setText(song.price.toString())
    }

}