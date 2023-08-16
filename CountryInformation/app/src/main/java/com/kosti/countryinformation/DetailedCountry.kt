package com.kosti.countryinformation

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.kosti.countryinformation.constants.Country
import com.kosti.countryinformation.constants.countries_info

class DetailedCountry : AppCompatActivity() {
    lateinit var askedData:TextView;

    lateinit var detailedCountry: Country
    lateinit var spinnerDude:Spinner
    lateinit var acceptBtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_country)

        askedData = findViewById(R.id.askedData)

        spinnerDude = findViewById(R.id.spinnerDude)

        acceptBtn = findViewById(R.id.acceptBtn)
        populateSpinner()

        val country = intent.getStringExtra("country_code")
        detailedCountry = countries_info[country]!!;

        acceptBtn.setOnClickListener {
            sendResult()
        }

    }

    fun sendResult(){
        var result = askedData.text.toString()
        val intent = Intent()
        intent.putExtra("result", result)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun populateSpinner(){
        val elementos = listOf("Poblacion", "Nombre", "Capital")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, elementos)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerDude.adapter = adapter

        spinnerDude.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val itemSeleccionado = elementos[position]
                when (itemSeleccionado){
                    "Poblacion"-> askedData.text = "La poblacion del pais es ${detailedCountry.population}"
                    "Nombre"-> askedData.text = "El nombre del pais es ${detailedCountry.name}"
                    "Capital"->askedData.text = "La capital del pais es ${detailedCountry.capital}"
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

                Toast.makeText(this@DetailedCountry, "Nada", Toast.LENGTH_SHORT).show()
            }
        }
    }

}




