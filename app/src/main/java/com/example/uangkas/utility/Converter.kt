package com.example.uangkas.utility

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.support.annotation.RequiresApi
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.util.*

class Converter {

    companion object{
        fun  rupiahFormat(number: String): String {
            val rupiahFormat = NumberFormat.getInstance(Locale.GERMANY)
            return rupiahFormat.format(number.toLong())
        }

        @RequiresApi(Build.VERSION_CODES.N)
        fun dateFormat(date: String) : String{
            var format = SimpleDateFormat("yyyy-MM-dd")
            var newDate : Date? = null

            try {
                newDate = format.parse(date)
            }catch (e: ParseException){
            }

            format = SimpleDateFormat("dd/MM/yyyy")

            return format.format(newDate)
        }

        fun decimalFormat(number: Int ): String {
            val numberFormat = DecimalFormat("00")
            return numberFormat.format(number.toLong())
        }
    }
}