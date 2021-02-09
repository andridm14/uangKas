package com.example.uangkas.adapter

import android.app.Activity
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.uangkas.R
import com.example.uangkas.data.model.kasModel
import com.example.uangkas.utility.Converter

class MainAdapter (val activity: Activity, kasModel: List<kasModel>) : BaseAdapter() {

    var kasModel = ArrayList<kasModel>()

    init {
        this.kasModel = kasModel as ArrayList<kasModel>
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view : View?
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(R.layout.adapter_main, null)

        val textStatus: TextView = view.findViewById(R.id.textStatus)
        val textJumlah: TextView = view.findViewById(R.id.textJumlah)
        val textKeterangan: TextView = view.findViewById(R.id.textKeterangan)
        val textTanggal: TextView = view.findViewById(R.id.textTanggal)

        textStatus.text = kasModel[position].status
        textJumlah.text = kasModel[position].jumlah.toString()
        textKeterangan.text = kasModel[position].keterangan
        textTanggal.text = Converter.dateFormat(kasModel[position].tanggal)

        when(kasModel[position].status) {
            "MASUK" -> textStatus.setTextColor(ContextCompat.getColor(activity, R.color.green))
            "KELUAR" -> textStatus.setTextColor(ContextCompat.getColor(activity, R.color.red))
        }

        return view
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return kasModel.size
    }
}