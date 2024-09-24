package com.example.assigment2_spotifyclone

import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class MainActivity : AppCompatActivity() {
    private val viewModel:SongModel by viewModels()
    val cart= mutableListOf<SongData>()
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
        findViewById<Button>(R.id.addtocart).setOnClickListener{
            if(!viewModel.viewvalue().added) {
                when{
                    findViewById<Chip>(R.id.deluxe).isChecked->cart.add(viewModel.deluxepack())
                    findViewById<Chip>(R.id.premium).isChecked->cart.add(viewModel.premiumpack())
                    else->cart.add(viewModel.viewvalue())
                }
                viewModel.viewvalue().added=true
                findViewById<Button>(R.id.addtocart).setText("Unadd from cart")
                findViewById<TextView>(R.id.cartitems).setText(cart.size.toString())
                Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show()
            }
            else{
                when{
                    findViewById<Chip>(R.id.deluxe).isChecked->cart.remove(viewModel.deluxepack())
                    findViewById<Chip>(R.id.premium).isChecked->cart.remove(viewModel.premiumpack())
                    else->cart.remove(viewModel.viewvalue())
                }
                viewModel.viewvalue().added=false
                findViewById<Button>(R.id.addtocart).setText("Add to cart")
                findViewById<TextView>(R.id.cartitems).setText(cart.size.toString())
                Toast.makeText(this, "Unadded from cart", Toast.LENGTH_SHORT).show()
            }

        }
        findViewById<ChipGroup>(R.id.types).setOnCheckedStateChangeListener { group, checkedIds ->
            // Check if any chip is selected
            if (checkedIds.isNotEmpty()) {
                when (checkedIds[0]) {  // We use [0] because it's single selection
                    R.id.basic -> {
                        // Update the price to basic price
                        findViewById<TextView>(R.id.price).text = viewModel.viewvalue().price.toString() + "$/m"
                    }
                    R.id.premium -> {
                        // Update the price to premium pack price
                        findViewById<TextView>(R.id.price).text = viewModel.premiumpack().price.toString() + "$/m"
                    }
                    R.id.deluxe -> {
                        // Update the price to deluxe pack price
                        findViewById<TextView>(R.id.price).text = viewModel.deluxepack().price.toString() + "$/m"
                    }
                }
            }
        }
    }
    fun setproduct(song:SongData):Unit{
        val productView=findViewById<ImageView>(R.id.productView).setImageDrawable(AppCompatResources.getDrawable(this,song.imgurl))
        val trackname= findViewById<TextView>(R.id.trackname).setText(song.name)
        val prodYear=findViewById<TextView>(R.id.prodyear).setText("Produced "+song.prodYear)
        val listens=findViewById<TextView>(R.id.listens).setText(song.listens.toString()+"K listens")
        val rate = findViewById<androidx.appcompat.widget.AppCompatRatingBar>(R.id.rate)
        rate.rating=song.rate
        val price = findViewById<TextView>(R.id.price).setText(song.price.toString()+"$/m")
        if(viewModel.viewvalue().added){
            findViewById<Button>(R.id.addtocart).setText("Unadd from cart")
        }
        else{
            findViewById<Button>(R.id.addtocart).setText("Add to cart")
        }
        val cartitems=findViewById<TextView>(R.id.cartitems).setText(cart.size.toString())
    }

}