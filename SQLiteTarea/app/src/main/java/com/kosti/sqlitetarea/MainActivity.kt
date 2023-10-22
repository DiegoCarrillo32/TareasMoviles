package com.kosti.sqlitetarea

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), ItemListDialogFragment.OnDismissListener {

    private lateinit var helper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        helper = SQLiteHelper(this)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val data = mutableListOf<Producto>()
        val result = helper.readProducto()
        data.addAll(result)
        val adapter = CustomAdapter(data)
        recyclerview.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {

                val bottomSheetFragment = ItemListDialogFragment()
                bottomSheetFragment.setOnDismissListener(this)
                bottomSheetFragment.show(supportFragmentManager, "BSDialogFragment")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDismissOnActivity() {
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerView)
        val data = mutableListOf<Producto>()
        val result = helper.readProducto()
        data.addAll(result)
        val adapter = CustomAdapter(data)
        recyclerview.adapter = adapter
    }
}