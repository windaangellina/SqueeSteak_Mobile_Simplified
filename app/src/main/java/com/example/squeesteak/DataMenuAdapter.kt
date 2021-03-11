package com.example.squeesteak

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DataMenuAdapter(val dataMenu : MutableList<MenuItem>) :
    RecyclerView.Adapter<DataMenuAdapter.ItemViewHolder>() {

    //untuk nampung data
    val listDataMenu : MutableList<MenuItem> = dataMenu
    //untuk context
    lateinit var appcontext : Context

    //untuk on click
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataMenuAdapter.ItemViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.menuitem_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listDataMenu.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        //get context
        appcontext = holder.itemView.context

        //get current data
        var currentMenu = listDataMenu[holder.adapterPosition]
        //pasang data
        holder.bind(currentMenu)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(currentMenu)
        }
    }

    inner class ItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var imgMenu : ImageView = itemView.findViewById(R.id.imgMenu)
        var tvNamaMenu : TextView = itemView.findViewById(R.id.tvNamaMenu)
        var tvHargaMenu : TextView = itemView.findViewById(R.id.tvHargaMenu)
        var tvDeskripsi : TextView = itemView.findViewById(R.id.tvKeteranganMenu)
        var tvStar : TextView = itemView.findViewById(R.id.tvStarReview)

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(menu : MenuItem){
            //isi value
            tvNamaMenu.text = menu.getNama()
            tvHargaMenu.text = menu.getHargaFormat()
            tvDeskripsi.text = menu.getDeskripsi()
            tvStar.text = "${menu.getBintangReview()} / 5"

            //atur foto menu
            // pake glide buat jaga2 spy kl dirun di mobile yg ramnya kecil, app ga crash
            var fotomenu = appcontext.getDrawable(menu.getIdFoto())
            Glide.with(appcontext)
                .load(fotomenu)
                .apply(RequestOptions().override(70, 70))
                .circleCrop()
                .into(imgMenu)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(datamenu: MenuItem)
    }
}


