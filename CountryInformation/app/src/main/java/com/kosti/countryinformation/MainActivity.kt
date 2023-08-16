package com.kosti.countryinformation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    lateinit var listView: ListView
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.listView)
        populateListView()

        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result -> if ( result.resultCode == RESULT_OK){
                val data:Intent? = result.data
                val resultado = data?.getStringExtra("result")
                Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show()

            }
        }

    }

    fun populateListView(){

        val stringArray = resources.getStringArray(R.array.countries);
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, stringArray);
        listView.adapter = adapter
        listView.onItemClickListener = object: AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val intent = Intent(this@MainActivity, DetailedCountry::class.java)
                intent.putExtra("country_code", stringArray[p2])
                startForResult.launch(intent)
            }
        }

    }
}