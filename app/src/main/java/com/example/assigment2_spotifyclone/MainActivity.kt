package com.example.assigment2_spotifyclone

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class MainActivity : AppCompatActivity() {
    private val viewModel:SongModel by viewModels()
    private val cart= mutableListOf<SongData>()
    private var money =100
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode==Activity.RESULT_OK && it.data != null){
            val save=it.data?.getBooleanExtra("save",true)
            val checkedout=it.data?.getBooleanExtra("checkedout",false)
            if(save==false) {
                val intent = intent
                finish() // Close the current activity
                startActivity(intent)
            }
            if(checkedout==true){
                cart.clear()
                viewModel.reset()
                val moneychecked=it.data?.getIntExtra("checkout",0)?:0
                money=moneychecked
                setproduct(viewModel.viewvalue())
            }
        }
    }
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
        val nextButton=findViewById<Button>(R.id.next)
        nextButton.setOnClickListener{
            viewModel.next()
            when{
                findViewById<Chip>(R.id.deluxe).isChecked->setproduct(viewModel.deluxepack())
                findViewById<Chip>(R.id.premium).isChecked->setproduct(viewModel.premiumpack())
                else->setproduct(viewModel.viewvalue())
            }

        }
        findViewById<Button>(R.id.addtocart).setOnClickListener{
            if(!viewModel.viewvalue().added) {
                when{
                    findViewById<Chip>(R.id.deluxe).isChecked->cart.add(viewModel.deluxepack())
                    findViewById<Chip>(R.id.premium).isChecked->cart.add(viewModel.premiumpack())
                    else->cart.add(viewModel.viewvalue())
                }
                viewModel.viewvalue().added=true
                findViewById<Button>(R.id.addtocart).setText(R.string.unaddtocart)
                findViewById<TextView>(R.id.cartitems).setText(cart.size.toString())
                Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show()
            }
            else{
                cart.removeIf{it.name==viewModel.viewvalue().name}
                viewModel.viewvalue().added=false
                findViewById<Button>(R.id.addtocart).setText(R.string.addtocart)
                findViewById<TextView>(R.id.cartitems).setText(cart.size.toString())
                Toast.makeText(this, "Unadded from cart", Toast.LENGTH_SHORT).show()
            }

        }
        findViewById<ChipGroup>(R.id.types).setOnCheckedStateChangeListener { _, checkedIds ->

            if (checkedIds.isNotEmpty()) {
                when (checkedIds[0]) {
                    R.id.basic -> {
                        findViewById<TextView>(R.id.price).text = viewModel.viewvalue().price.toString() + "$/m"
                    }
                    R.id.premium -> {
                        findViewById<TextView>(R.id.price).text = viewModel.premiumpack().price.toString() + "$/m"
                    }
                    R.id.deluxe -> {
                        findViewById<TextView>(R.id.price).text = viewModel.deluxepack().price.toString() + "$/m"
                    }
                }
            }
        }
        findViewById<Button>(R.id.borrow).setOnClickListener{
            val intent =Intent(this,SecondActivity::class.java)
            for(item:SongData in cart){
                intent.putExtra("cart"+cart.indexOf(item).toString(),item)
            }
            intent.putExtra("cartNumber",cart.size)
            intent.putExtra("money",money)
            launcher.launch(intent)
        }
    }
    fun setproduct(song:SongData){
       findViewById<ImageView>(R.id.productView).setImageDrawable(AppCompatResources.getDrawable(this,song.imgurl))
        findViewById<TextView>(R.id.trackname).setText(song.name)
       findViewById<TextView>(R.id.prodyear).setText("Produced "+song.prodYear)
        findViewById<TextView>(R.id.listens).setText(song.listens.toString()+"K listens")
        val rate = findViewById<androidx.appcompat.widget.AppCompatRatingBar>(R.id.rate)
        rate.rating=song.rate
         findViewById<TextView>(R.id.price).setText(song.price.toString()+"$/m")
        if(viewModel.viewvalue().added){
            findViewById<Button>(R.id.addtocart).setText(R.string.unaddtocart)
        }
        else{
            findViewById<Button>(R.id.addtocart).setText(R.string.addtocart)
        }
        findViewById<TextView>(R.id.money).setText(money.toString()+"$")
        findViewById<TextView>(R.id.cartitems).setText(cart.size.toString())
    }

}