package com.example.uangkas.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.uangkas.data.model.kasModel

class DatabaseHelper : SQLiteOpenHelper {

    companion object {
        val DBName = "lazdayUangkasKotlin"
        val DBVersion = 1
        val tableName = "transaksi"
        val transaksiId = "transaksi_id"
        val status = "status"
        val jumlah = "jumlah"
        val keterangan = "keterangan"
        val tanggal = "tanggal"
    }

    private val createTable = "CREATE TABLE $tableName ($transaksiId INTEGER PRIMARY KEY AUTOINCREMENT" +
            ", $status TEXT, $jumlah TEXT, $keterangan TEXT, $tanggal DATETIME DEFAULT CURRENT_DATE)"

    var context: Context? = null
    var db: SQLiteDatabase

    constructor(context: Context) : super(context,
        DBName, null,
        DBVersion
    ) {
        this.context = context
        db = this.writableDatabase
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL( "DROP TABLE IF EXISTS $tableName")
    }

    fun insertData(stts: String, jmlh: String, ket: String) : Long {
        val values = ContentValues()
        values.put(status, stts)
        values.put(jumlah, jmlh)
        values.put(keterangan, ket)

        val transaksi_id = db.insert(tableName, null, values)

//        db.close()

        return transaksi_id
    }

    fun getData() : List<kasModel> {
        val kas = ArrayList<kasModel>()

        var strSql = "SELECT * FROM $tableName ORDER BY $transaksiId DESC"

        val Cursor : Cursor = db.rawQuery(strSql, null)
        Cursor.moveToFirst()

        for (i in 0 until Cursor.count){
            Cursor.moveToPosition(i)
            kas.add(
                kasModel(
                Cursor.getInt(Cursor.getColumnIndex(transaksiId)),
                Cursor.getString(Cursor.getColumnIndex(status)),
                Cursor.getLong(Cursor.getColumnIndex(jumlah)),
                Cursor.getString(Cursor.getColumnIndex(keterangan)),
                Cursor.getString(Cursor.getColumnIndex(tanggal))
            ))
        }

        return kas
    }

    fun getTotal(){
        val strSql = "SELECT SUM($jumlah) AS total, " + "(SELECT SUM($jumlah) FROM $tableName WHERE $status = 'MASUK' ) AS masuk, " +
                "(SELECT SUM($jumlah) FROM $tableName WHERE $status = 'KELUAR') AS keluar " + "FROM $tableName"

        val Cursor : Cursor = db.rawQuery(strSql, null)
        Cursor.moveToFirst()

        Log.e("_lzdyPemasukan", Cursor.getLong(Cursor.getColumnIndex("masuk")).toString() )
        Log.e("_lzdyPengeluaran", Cursor.getLong(Cursor.getColumnIndex("keluar")).toString() )

        Constant.masuk = Cursor.getLong(Cursor.getColumnIndex("masuk"))
        Constant.keluar = Cursor.getLong(Cursor.getColumnIndex("keluar"))
    }

    fun updateData(id: Int, stts: String, jmlh: Long, ket: String, tgl: String){
        val values = ContentValues()
        values.put(status, stts)
        values.put(jumlah, jmlh)
        values.put(keterangan, ket)
        values.put(tanggal, tgl)

        db.update(tableName, values,"$transaksiId=$id", null)
    }

    fun deleteData(id: Int){
        db.delete(tableName, "$transaksiId='$id'", null)
    }
}