package com.kosti.tarea1
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.kosti.tarea1.R

class MainActivity : AppCompatActivity() {
    lateinit var operateBtn: Button;
    lateinit var valueone: EditText;
    lateinit var  valuetwo: EditText;
    lateinit var radioGroup: Spinner
    lateinit var resultText: TextView
    var selectedOption: String = "+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        operateBtn = findViewById(R.id.operateBtn)
        valueone = findViewById(R.id.valueone)
        valuetwo = findViewById(R.id.valuetwo)
        radioGroup = findViewById(R.id.radiogp)
        resultText = findViewById(R.id.resultText)

        configureSpinner()
        operateBtn.setOnClickListener {
            executeOperation()
        }
    }

    fun executeOperation(){
        if(valueone.text.isEmpty() || valuetwo.text.isEmpty()) {
            Toast.makeText(this, "None of the values should be empty", Toast.LENGTH_SHORT).show()
            return
        }
        when (selectedOption) {
            "+" -> resultText.text = (valueone.text.toString().toLong() + valuetwo.text.toString().toLong()).toString()
            "-" -> resultText.text = (valueone.text.toString().toLong() - valuetwo.text.toString().toLong()).toString()
            "x" -> resultText.text = (valueone.text.toString().toLong() * valuetwo.text.toString().toLong()).toString()
            "/" -> {
                if(valuetwo.text.toString().toInt() == 0) {
                    Toast.makeText(this, "You cannot divide by 0", Toast.LENGTH_SHORT).show()
                    return
                }
                resultText.text = (valueone.text.toString().toInt() / valuetwo.text.toString().toInt()).toString()
            }
            else -> return
        }
    }


    fun configureSpinner(){
        val stringArray = resources.getStringArray(R.array.operators);
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,stringArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        radioGroup.adapter = adapter
        radioGroup.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedOption = stringArray[p2]
                println(selectedOption)

                Toast.makeText(this@MainActivity, "Selected $selectedOption", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }
}