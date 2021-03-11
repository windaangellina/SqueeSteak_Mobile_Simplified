package com.example.squeesteak

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.*

/*
class DataAdapterSong(private val context: Context, dataSong: ArrayList<MenuItem>) :

    RecyclerView.Adapter<ItemViewHolder>() {
    private val listDataSong: ArrayList<Song>

    // on click
    private var onItemClickCallback: onItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: onItemClickCallback?) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_header_lagu, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentDataSong: Song = listDataSong[position]

        //pasang data
        holder.bind(currentDataSong)

        //pasang on click di view nya
        holder.itemView.setOnClickListener { v ->
            onItemClickCallback!!.onItemClick(
                v,
                currentDataSong
            )
        }
    }

    override fun getItemCount(): Int {
        return listDataSong.size
    }

    inner class ItemViewHolder(itemView: View) : ViewHolder(itemView) {
        var tvJudul: TextView
        var tvPenyanyi: TextView
        var imgCoverAlbum: ImageView
        var imgFavorite: ImageView
        fun bind(song: Song) {
            tvJudul.setText(song.getJudul())
            tvPenyanyi.setText(song.getPenyanyi())
            // imgCoverAlbum.setImageResource(song.getIdAlbumCover());

            // pake glide buat jaga2 spy kl dirun di mobile yg ramnya kecil, app ga crash
            val albumcover = context.getDrawable(song.getIdAlbumCover())
            Glide.with(context)
                .load(albumcover)
                .apply(RequestOptions().override(70, 70))
                .into(imgCoverAlbum)

            //icon favorite mengikuti status
            if (song.getLiked()) {
                imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_full)
            } else {
                imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_border)
            }

            //pasang onclick untuk img favorite
            imgFavorite.setOnClickListener {
                var currStatusLiked: Boolean = song.getLiked()

                //ubah status jd kebalikannya
                currStatusLiked = !currStatusLiked
                song.setLiked(currStatusLiked)

                // update data
                // note to self : jangan lakukan ini kl gaada perubahan data. nti app crash
                notifyDataSetChanged()
            }
        }

        init {
            tvJudul = itemView.findViewById(R.id.tvJudul)
            tvPenyanyi = itemView.findViewById(R.id.tvPenyanyi)
            imgCoverAlbum = itemView.findViewById(R.id.imgAlbumCover)
            imgFavorite = itemView.findViewById(R.id.imgFavorite)
        }
    }

    interface onItemClickCallback {
        fun onItemClick(view: View?, song: Song?)
    }

    init {
        listDataSong = dataSong
    }

}


 */
