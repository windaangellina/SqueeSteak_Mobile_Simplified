package com.example.squeesteak

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailMenuActivity : AppCompatActivity() {
    //nampung data yang akan ditampilkan
    lateinit var dataMenuItem : MenuItem

    //component
    lateinit var imgDetailFoto : ImageView
    lateinit var tvNamaMenu : TextView
    lateinit var tvHarga : TextView
    lateinit var tvKeteranganBintang : TextView
    lateinit var tvKeteranganMenu : TextView
    lateinit var btnLike : Button
    lateinit var btnShare : Button
    lateinit var btnBack : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_menu)

        initComponent()

        if (intent.hasExtra(EXTRA_MENUITEM)){
            dataMenuItem = intent.getParcelableExtra(EXTRA_MENUITEM)!!
            setComponentValue()
        }
    }

    companion object {
        const val EXTRA_MENUITEM = "data_menu"
    }

    fun makeToast(message: String){
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    fun initComponent(){
        imgDetailFoto = findViewById(R.id.imgDetailFotoMenu)
        tvNamaMenu = findViewById(R.id.tvDetailNamaMenu)
        tvHarga = findViewById(R.id.tvDetailHargaMenu)
        tvKeteranganBintang = findViewById(R.id.tvDetailBintang)
        tvKeteranganMenu = findViewById(R.id.tvDetailKeteranganMenu)

        btnLike = findViewById(R.id.btnLike)
        btnShare = findViewById(R.id.btnShare)
        btnBack = findViewById(R.id.btnBack)

        setupEventListener()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setComponentValue(){
        //isi data
        tvNamaMenu.text = dataMenuItem.getNama()
        tvHarga.text = dataMenuItem.getHargaFormat()
        var review = "${dataMenuItem.getBintangReview()} / 5"
        tvKeteranganBintang.text = review
        tvKeteranganMenu.text = dataMenuItem.getDeskripsi()

        //gambar
        var fotomenu = applicationContext.getDrawable(dataMenuItem.getIdFoto())
        Glide.with(applicationContext)
            .load(fotomenu)
            .apply(RequestOptions().override(300, 300))
            .into(imgDetailFoto)

        //cek status like
        if (dataMenuItem.isLiked()){
            btnLike.text = "Unlike"
        }else{
            btnLike.text = "Like"
        }
    }

    fun setupEventListener(){
        btnBack.setOnClickListener{
            val backIntent = Intent(applicationContext, MainActivity::class.java)
            startActivity(backIntent)
            finish()
        }

        btnShare.setOnClickListener{
            shareDetailMenu()
        }

        btnLike.setOnClickListener{
            likeMenu()
        }
    }

    private fun shareDetailMenu() {
        /*Create an ACTION_SEND Intent*/
        val intent = Intent(Intent.ACTION_SEND)

        /*This will be the actual content you wish you share.*/
        val shareBody = "Let's dine at Squee Steak! \n " +
                "${dataMenuItem.getNama()} only Rp. ${dataMenuItem.getHargaFormat()} \n " +
                "Rating : ${dataMenuItem.getBintangReview()} out of 5 stars \n " +
                "${dataMenuItem.getDeskripsi()} \n \n " +
                "-- Squee Steak. Serving life's best finesse for you since 2021 -- "

        /*The type of the content is text, obviously.*/
        intent.type = "text/plain"

        /*Applying information Subject and Body.*/
        val subject = "Squee Steak - " + dataMenuItem.getNama()
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, shareBody)

        /*Fire!*/startActivity(Intent.createChooser(intent, "Share Squee Steak delish using"))
    }

    private fun likeMenu(){
        //change status variable detail menu yg dilempar dr MainActivity
        dataMenuItem.setIsLiked(! dataMenuItem.isLiked())

        //update di list
        var idx : Int = -1
        var foundIn : String = "steak"
        idx = getIndexMenu(dataMenuItem, MainActivity.listMenuSteaks)
        if (idx < 0){
            foundIn = "side-dish"
            idx = getIndexMenu(dataMenuItem, MainActivity.listMenuSideDishes)
        }
        if (idx < 0){
            foundIn = "drinks"
            idx = getIndexMenu(dataMenuItem, MainActivity.listMenuDrinks)
        }

        //cek data ada atau gak
        if (idx < 0){
            makeToast("data not found")
        }
        else{
            changeData(foundIn, idx)

            //feed back
            if (dataMenuItem.isLiked()){
                //makeToast("${dataMenuItem.getNama()} has been added to your favorite dish")
                btnLike.text = "Unlike"
            }
            else{
                //makeToast("${dataMenuItem.getNama()} has been removed from your favorite dish")
                btnLike.text = "Like"
            }
        }
    }

    private fun getIndexMenu(data : MenuItem, listMenu : MutableList<MenuItem>) : Int{
        var idx : Int = -1
        for (i in listMenu.indices){
            var item : MenuItem = listMenu[i]

            if (item.getNama().toUpperCase().equals(data.getNama().toUpperCase())){
                idx = i
            }
        }

        return idx
    }

    private fun changeData(foundIn : String, index : Int){
        if (foundIn.equals("steak")){
            MainActivity.listMenuSteaks[index].setIsLiked(dataMenuItem.isLiked())
        }
        else if (foundIn.equals("side-dish")){
            MainActivity.listMenuSideDishes[index].setIsLiked(dataMenuItem.isLiked())
        }
        else if (foundIn.equals("drinks")){
            MainActivity.listMenuDrinks[index].setIsLiked(dataMenuItem.isLiked())
        }
    }
}