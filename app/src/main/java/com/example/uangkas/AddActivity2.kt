package com.example.uangkas

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add2.*

class AddActivity2 : AppCompatActivity() {

    var status : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add2)

        radioStatus.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioMasuk -> status = "MASUK"
                R.id.radioKeluar -> status = "KELUAR"
            }

            Log.e("_logStatus", status);
        }

        btnSimpan.setOnClickListener {
            if (status.isNullOrBlank() || editJumlah.text.isNullOrBlank() || editKeterangan.text.isNullOrBlank()){
                Toast.makeText(applicationContext, "Isi data dengan benar!",
                    Toast.LENGTH_SHORT).show()

            }else{
               var id = App.db!!.insertData(status, editJumlah.text.toString(), editKeterangan.text.toString())
                Log.e("_logId", id.toString());
                if(id > 0){
                    Toast.makeText(applicationContext, "Berhasil!",
                        Toast.LENGTH_SHORT).show()

                    finish()
                }
            }
        }

        supportActionBar!!.setTitle("Tambah Baru")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}