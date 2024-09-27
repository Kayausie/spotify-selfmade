package com.example.assigment2_spotifyclone

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class SecondActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.second_activity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.second)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var save=false
        var checkedout=false
        val numberincarts:Int=intent.getIntExtra("cartNumber",0)
        var total=0
        for(i in 0 until numberincarts){
            intent.getParcelableExtra("cart"+i.toString(),SongData::class.java)
            val tablerow=TableRow(this)
            val textview1=TextView(this).apply {
                setTextAppearance(R.style.ProdandListen)
                 layoutParams = TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,2f)
                gravity=Gravity.CENTER
                setPadding(8,8,8,8)
                text = intent.getParcelableExtra("cart" + i.toString(), SongData::class.java)?.name

            }
            tablerow.addView(textview1)
            val textview2=TextView(this).apply {
                setTextAppearance(R.style.ProdandListen)
                layoutParams = TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,1f)
                gravity=Gravity.CENTER
                setPadding(8,8,8,8)
                text = intent.getParcelableExtra("cart" + i.toString(), SongData::class.java)?.price.toString()

            }
            tablerow.addView(textview2)
            findViewById<TableLayout>(R.id.tableLayout).addView(tablerow)
            total+=intent.getParcelableExtra("cart" + i.toString(), SongData::class.java)?.price?:0
        }
        findViewById<TextView>(R.id.total).setText(total.toString()+"$")
        findViewById<TextView>(R.id.cartitems).setText(intent.getIntExtra("cartNumber",0).toString())
        findViewById<TextView>(R.id.money1).setText(intent.getIntExtra("money",0).toString()+"$")
        findViewById<Button>(R.id.goback).setOnClickListener{
            if(!save) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Confirmation")
                builder.setMessage("Going back without saving will delete your current cart, do you want to proceed?")
                builder.setPositiveButton("Yes") { dialog, which ->

                    val intent1 = Intent()
                    intent1.putExtra("save", save)
                    setResult(RESULT_OK, intent1)
                    finish()
                }
                builder.setNegativeButton("No") { dialog, which ->
                }
                val dialog = builder.create()
                dialog.show()
            }
            else{
                val intent1 = Intent()
                intent1.putExtra("save", save)
                setResult(RESULT_OK, intent1)
                finish()
            }
        }
        findViewById<Button>(R.id.save).setOnClickListener{
            save=true
            Toast.makeText(this, "Saved Successfullly", Toast.LENGTH_SHORT).show()
        }
        findViewById<Button>(R.id.checkout).setOnClickListener{
            if(total>=intent.getIntExtra("money",0)){
                Toast.makeText(this, "Error: total exceeded credits, please go back and change the booking", Toast.LENGTH_SHORT).show()
            }
            else{
                val intent1 = Intent(this, MainActivity::class.java)
                checkedout=true
                intent1.putExtra("checkedout",checkedout)
                intent1.putExtra("checkout",intent.getIntExtra("money",0)-total)
                setResult(RESULT_OK,intent1)
                finish()
            }
        }
        findViewById<ImageView>(R.id.logo).setOnClickListener{
            val intentemail=Intent(Intent.ACTION_VIEW, Uri.parse("https://open.spotify.com/"))
            startActivity(intentemail)
        }
    }
}