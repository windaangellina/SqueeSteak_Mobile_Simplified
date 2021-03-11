package com.example.squeesteak

import android.os.Parcel
import android.os.Parcelable
import java.text.DecimalFormat


class MenuItem() : Comparable<MenuItem>, Parcelable {
    private var nama :String = "undefined";
    private var harga :Int = 0;
    private var idFoto : Int = -1;
    private var bintang : Int = 0;
    private var deskripsi : String = "";
    private var isLiked : Boolean = false;

    constructor(parcel: Parcel) : this() {
        nama = parcel.readString().toString()
        harga = parcel.readInt()
        idFoto = parcel.readInt()
        bintang = parcel.readInt()
        deskripsi = parcel.readString().toString()
        isLiked = parcel.readByte() != 0.toByte()
    }

    constructor(pnama: String, pharga: Int, pIdFoto: Int, pbintang: Int, pdeskripsi: String) : this() {
        this.nama = pnama;
        this.harga = pharga;
        this.idFoto = pIdFoto;
        this.bintang = pbintang;
        this.deskripsi = pdeskripsi;
        this.isLiked = false;
    }

    fun getNama() : String{
        return nama
    }

    fun getHargaFormat() : String{
        //pemisah koma
        val decim = DecimalFormat("#,###.##")

        //return "$harga"

        return "${decim.format(harga)}"
    }

    fun getHarga() : Int{
        return harga
    }

    fun getBintangReview() : Int{
        return bintang
    }

    fun getIdFoto() : Int{
        return idFoto
    }

    fun getDeskripsi() : String{
        return deskripsi
    }

    fun isLiked() : Boolean{
        return isLiked
    }

    fun setIsLiked(pliked: Boolean){
        this.isLiked = pliked
    }

    override fun toString(): String {
        return "$nama - $bintang / 5 - Rp. $harga - $deskripsi"
    }

    override fun compareTo(other: MenuItem): Int {
        // sort asc nama menu
        return this.nama.compareTo(other.nama)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nama)
        parcel.writeInt(harga)
        parcel.writeInt(idFoto)
        parcel.writeInt(bintang)
        parcel.writeString(deskripsi)
        parcel.writeByte(if (isLiked) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MenuItem> {
        override fun createFromParcel(parcel: Parcel): MenuItem {
            return MenuItem(parcel)
        }

        override fun newArray(size: Int): Array<MenuItem?> {
            return arrayOfNulls(size)
        }
    }


}