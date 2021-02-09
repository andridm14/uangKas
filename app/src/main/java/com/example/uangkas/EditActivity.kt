package com.example.uangkas

import android.app.DatePickerDialog
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import android.widget.Toast
import com.example.uangkas.R.*
import com.example.uangkas.utility.Converter
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_edit.btnSimpan
import kotlinx.android.synthetic.main.activity_edit.editJumlah
import kotlinx.android.synthetic.main.activity_edit.editKeterangan
import kotlinx.android.synthetic.main.activity_edit.radioStatus
import java.time.Month
import java.util.*

class EditActivity : AppCompatActivity() {

    var tanggal: String? = null
    var status: String? = null


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_edit)

        var bundle: Bundle? = intent.extras
        Log.e("_hasilIntent", bundle?.getString(getString(string.intent_keterangan)).toString())

        status =  bundle?.getString(getString(string.intent_status)).toString()
        when(status){
            "MASUK" -> radioMasuk.isChecked = true
            "KELUAR" -> radioKeluar.isChecked = true
        }

        radioStatus.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioMasuk -> status = "MASUK"
                R.id.radioKeluar -> status = "KELUAR"
            }

            Log.e("_logStatus", status.toString());
        }

        editJumlah.setText( bundle?.getString(getString(string.intent_jumlah)).toString())
        editKeterangan.setText( bundle?.getString(getString(string.intent_keterangan)).toString())

        tanggal =  bundle?.getString(getString(string.intent_tanggal)).toString()
        editTanggal.setText(Converter.dateFormat(tanggal.toString()))

        btnSimpan.setOnClickListener {
            if (status.isNullOrBlank() || editJumlah.text.isNullOrBlank() || editKeterangan.text.isNullOrBlank() || tanggal.isNullOrBlank()){
                Toast.makeText(applicationContext, "Isi data dengan benar!",
                    Toast.LENGTH_SHORT).show()

            }else{
                App.db!!.updateData(bundle?.getString(getString(string.intent_id))!!.toInt(), status.toString(),
                    editJumlah.text.toString().toLong(), editKeterangan.text.toString(), tanggal.toString())

                Toast.makeText(applicationContext, "Update Success!",
                    Toast.LENGTH_SHORT).show()

                finish()
                }
            }

        var calendar = Calendar.getInstance()
        var y = calendar.get(Calendar.YEAR)
        var m = calendar.get(Calendar.MONTH)
        var d = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            tanggal = "$year-${Converter.decimalFormat(month+1)}-${Converter.decimalFormat(dayOfMonth)}"
            editTanggal.setText(Converter.dateFormat(tanggal.toString()))
        }, y, m, d)

        editTanggal.setOnClickListener {
            datePickerDialog.show()
        }

        supportActionBar!!.setTitle("Edit")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}