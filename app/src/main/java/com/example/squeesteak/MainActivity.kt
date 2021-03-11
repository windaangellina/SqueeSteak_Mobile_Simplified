package com.example.squeesteak

import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //resources
    lateinit var dataNamaSteaks: Array<String>
    lateinit var dataNamaSideDish: Array<String>
    lateinit var dataNamaDrinks: Array<String>

    lateinit var dataHargaSteaks : Array<String>
    lateinit var dataHargaSideDish : Array<String>
    lateinit var dataHargaDrinks : Array<String>

    lateinit var dataBintangSteaks : Array<String>
    lateinit var dataBintangSideDish : Array<String>
    lateinit var dataBintangDrinks : Array<String>

    lateinit var dataDeskripsiSteaks : Array<String>
    lateinit var dataDeskripsiSideDish : Array<String>
    lateinit var dataDeskripsiDrinks : Array<String>

    lateinit var dataFotoSteaks: TypedArray
    lateinit var dataFotoSideDish: TypedArray
    lateinit var dataFotoDrinks: TypedArray

    //recycler view
    lateinit var dataMenuAdapter: DataMenuAdapter
    lateinit var rvDataMenu: RecyclerView

    //components
    lateinit var imgCategorySteak : ImageView
    lateinit var imgCategorySideDish : ImageView
    lateinit var imgCategoryDrink : ImageView
    lateinit var imgCategoryFavorites : ImageView
    lateinit var imgProfile : ImageView
    lateinit var tvJudulKategoriMenu : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //menghilangkan ActionBar
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        if (ctrLoadData == 0){
            getMenuData()
        }

        setUpComponent()
        setUpView(listMenuSteaks)

        setUpEventListener()

        // makeToast("ctr load data : $ctrLoadData")
    }

    fun makeToast(message: String){
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    //kotlin tidak ada konsep static. bisa diakali dengan cara pake companion object
    companion object{
        var ctrLoadData : Int = 0

        //tampung data
        var listMenuSteaks = mutableListOf<MenuItem>()
        var listMenuSideDishes = mutableListOf<MenuItem>()
        var listMenuDrinks = mutableListOf<MenuItem>()
    }

    fun prepareResources(){
        dataNamaSteaks = resources.getStringArray(R.array.data_menu_nama_steaks)
        dataNamaSideDish = resources.getStringArray(R.array.data_menu_nama_side_dishes)
        dataNamaDrinks = resources.getStringArray(R.array.data_menu_nama_drinks)

        dataHargaSteaks = resources.getStringArray(R.array.data_menu_harga_steaks)
        dataHargaSideDish = resources.getStringArray(R.array.data_menu_harga_side_dishes)
        dataHargaDrinks = resources.getStringArray(R.array.data_menu_harga_drinks)

        dataBintangSteaks = resources.getStringArray(R.array.data_menu_bintang_steaks)
        dataBintangSideDish = resources.getStringArray(R.array.data_menu_bintang_side_dishes)
        dataBintangDrinks = resources.getStringArray(R.array.data_menu_bintang_drinks)

        dataDeskripsiSteaks = resources.getStringArray(R.array.data_menu_detail_steaks)
        dataDeskripsiSideDish = resources.getStringArray(R.array.data_menu_detail_side_dishes)
        dataDeskripsiDrinks = resources.getStringArray(R.array.data_menu_detail_drinks)

        dataFotoSteaks = resources.obtainTypedArray(R.array.data_menu_foto_steaks)
        dataFotoSideDish = resources.obtainTypedArray(R.array.data_menu_foto_side_dishes)
        dataFotoDrinks = resources.obtainTypedArray(R.array.data_menu_foto_drinks)
    }

    fun getMenuData(){
        prepareResources()

        //steaks
        listMenuSteaks.clear()
        for (i in dataNamaSteaks.indices){
            var steak = MenuItem(
                dataNamaSteaks[i],
                dataHargaSteaks[i].toInt(),
                dataFotoSteaks.getResourceId(i, -1),
                dataBintangSteaks[i].toInt(),
                dataDeskripsiSteaks[i]
            )
            listMenuSteaks.add(steak)
        }

        //side dishes
        listMenuSideDishes.clear()
        for (i in dataNamaSideDish.indices){
            var sidedish = MenuItem(
                dataNamaSideDish[i],
                dataHargaSideDish[i].toInt(),
                dataFotoSideDish.getResourceId(i, -1),
                dataBintangSideDish[i].toInt(),
                dataDeskripsiSideDish[i]
            )

            listMenuSideDishes.add(sidedish)
        }

        //drinks
        listMenuDrinks.clear()
        for (i in dataNamaDrinks.indices){
            var drink = MenuItem(
                dataNamaDrinks[i],
                dataHargaDrinks[i].toInt(),
                dataFotoDrinks.getResourceId(i, -1),
                dataBintangDrinks[i].toInt(),
                dataDeskripsiDrinks[i]
            )

            listMenuDrinks.add(drink)
        }

        ctrLoadData++
    }

    fun setUpComponent(){
        //recycler view
        rvDataMenu = findViewById(R.id.rvListMenuItem)
        rvDataMenu.setHasFixedSize(true)

        //image view
        imgCategoryDrink = findViewById(R.id.imgDrinks)
        imgCategorySideDish = findViewById(R.id.imgSideDishes)
        imgCategorySteak = findViewById(R.id.imgSteaks)
        imgCategoryFavorites = findViewById(R.id.imgCategoryFavorites)
        imgProfile = findViewById(R.id.imgProfile)

        //textview
        tvJudulKategoriMenu = findViewById(R.id.tvJudulKategoriMenu)
    }

    fun setUpView(listMenu: MutableList<MenuItem>){
        rvDataMenu.layoutManager = LinearLayoutManager(applicationContext)

        //set adapter
        var dataMenuAdapter = DataMenuAdapter(listMenu);
        rvDataMenu.adapter = dataMenuAdapter

        // short click munculkan detail
        dataMenuAdapter.setOnItemClickCallback(object : DataMenuAdapter.OnItemClickCallback{
            override fun onItemClicked(datamenu: MenuItem) {
                val detailMenuIntent = Intent(this@MainActivity, DetailMenuActivity::class.java)
                detailMenuIntent.putExtra(DetailMenuActivity.EXTRA_MENUITEM, datamenu)
                startActivity(detailMenuIntent)

//                makeToast(datamenu.toString())
            }
        })

        dataMenuAdapter.notifyDataSetChanged()
    }

    fun setUpEventListener(){
        imgProfile.setOnClickListener {
            val profileIntent : Intent = Intent(this@MainActivity, ProfileActivity::class.java)
            startActivity(profileIntent)
        }

        //navigasi kategori
        imgCategorySteak.setOnClickListener{
            tvJudulKategoriMenu.text = "Steaks"
            setUpView(listMenuSteaks)
        }
        imgCategorySideDish.setOnClickListener {
            tvJudulKategoriMenu.text = "Side Dishes"
            setUpView(listMenuSideDishes)
        }
        imgCategoryDrink.setOnClickListener {
            tvJudulKategoriMenu.text = "Drinks"
            setUpView(listMenuDrinks)
        }
        imgCategoryFavorites.setOnClickListener {
            tvJudulKategoriMenu.text = "Favorites"

            var tmpListMenuFavorites = mutableListOf<MenuItem>()

            //get data favorites
            for (menuItem in listMenuSteaks){
                if (menuItem.isLiked()){
                    tmpListMenuFavorites.add(menuItem)
                }
            }
            for (menuItem in listMenuSideDishes){
                if (menuItem.isLiked()){
                    tmpListMenuFavorites.add(menuItem)
                }
            }
            for (menuItem in listMenuDrinks){
                if (menuItem.isLiked()){
                    tmpListMenuFavorites.add(menuItem)
                }
            }

            //show data favorites
            setUpView(tmpListMenuFavorites)

        }

    }
}

