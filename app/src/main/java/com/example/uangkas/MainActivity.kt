package com.example.uangkas

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import com.example.uangkas.adapter.MainAdapter
import com.example.uangkas.data.Constant
import com.example.uangkas.data.model.kasModel
import com.example.uangkas.utility.Converter
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.dialog_menu.*

class MainActivity : AppCompatActivity() {

    var kasList = ArrayList<kasModel>()

    companion object{
        var intent: Intent? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
            startActivity(Intent(this, AddActivity2::class.java))
            overridePendingTransition(R.anim.slide_form_right, R.anim.slide_from_left)
        }
    }

    override fun onResume() {
        super.onResume()
        getData(); getTotal()
    }

    fun getData() {
        kasList.clear()
        listView.adapter = null

        kasList.addAll(App.db!!.getData())
        val adapter = MainAdapter(this, kasList)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->

            Constant.transaksi_id = kasList[position].transaksi_id

            intent = Intent(this, EditActivity:: class.java)
            intent.putExtra(getString(R.string.intent_id), kasList[position].transaksi_id.toString())
            intent.putExtra(getString(R.string.intent_status), kasList[position].status)
            intent.putExtra(getString(R.string.intent_jumlah), kasList[position].jumlah.toString())
            intent.putExtra(getString(R.string.intent_keterangan), kasList[position].keterangan)
            intent.putExtra(getString(R.string.intent_tanggal), kasList[position].tanggal)

            menuDialog()
        }
    }

    fun getTotal() {
        App.db!!.getTotal()

        txtMasuk.text = "Rp " + Converter.rupiahFormat(Constant.masuk.toString())
        txtKeluar.text = "Rp " + Converter.rupiahFormat(Constant.keluar.toString())

        txtSaldo.text = "Rp " + Converter.rupiahFormat( (Constant.masuk - Constant.keluar).toString())
    }

    fun menuDialog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_menu)
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

        dialog.textEdit.setOnClickListener {
//            startActivity(Intent(this,EditActivity::class.java))
            startActivity(intent)
            dialog.dismiss()
        }
        dialog.textHapus.setOnClickListener {
            alertDialog()
            dialog.dismiss()
        }
        dialog.show()
    }

    fun alertDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Konfirmasi")
        builder.setMessage("Sure you want to be delete?")
        builder.setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
            App.db!!.deleteData(Constant.transaksi_id!!)

            Toast.makeText(applicationContext, "Transaction success delete!",
                Toast.LENGTH_SHORT).show()

            getData(); getTotal()

            dialog.dismiss()
        })
        builder.setNegativeButton("Batal", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })

        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}