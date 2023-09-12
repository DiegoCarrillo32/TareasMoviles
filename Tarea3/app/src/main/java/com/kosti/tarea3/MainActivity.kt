package com.kosti.tarea3

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import kotlin.Exception
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var listView: ListView
    var eventos: MutableList<Event> = mutableListOf()
    var nombreEventos: MutableList<String> = mutableListOf()
    lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.listView)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nombreEventos )

        listView.adapter =adapter
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { p0, p1, p2, p3 ->
                val intent = Intent(this@MainActivity, DetailedEvent::class.java)
                intent.putExtra("event", Gson().toJson(eventos[p2]))
                startActivity(intent)
            }
        val toolbar: Toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Agenda"
        isFirstTimer()
        var eventList = getEvents()
        eventos.clear()
        for(event in eventList.eventList){
            Toast.makeText(this, "Nombre ${event.name}", Toast.LENGTH_SHORT).show()
            eventos.add(event)
            nombreEventos.add(event.name)
        }
        adapter.notifyDataSetChanged()
        //loadEvents()
        //loadListView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_item -> {
                // Acción para el elemento de búsqueda
                mostrarDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun isFirstTimer(){
        val sharedPreferences: SharedPreferences = getSharedPreferences("MiPref", Context.MODE_PRIVATE)

        // Recuperar valores de SharedPreferences
        val result: Boolean? = sharedPreferences.getBoolean("isFirster", true)
        if (result == false) return
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_first, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val dialog = builder.create()

        val yesBtn: Button = dialogView.findViewById(R.id.yesBtn)
        val noBtn: Button = dialogView.findViewById(R.id.noBtn)
        yesBtn.setOnClickListener {
            savePreferences(true)
            dialog.dismiss()
        }
        noBtn.setOnClickListener {
            savePreferences(false)
            dialog.dismiss()
        }

        dialog.show()
    }

    fun mostrarDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val dialog = builder.create()
        val btnCerrar:Button = dialogView.findViewById(R.id.btnCerrar)
        val btnAdd:Button = dialogView.findViewById(R.id.button2)
        val etNombre: EditText = dialogView.findViewById(R.id.etNombre)
        val etDesc: EditText = dialogView.findViewById(R.id.editTextTextMultiLine)
        val cvOt: CalendarView = dialogView.findViewById(R.id.calendarView)

        btnCerrar.setOnClickListener {
            dialog.dismiss() // Cierra el diálogo cuando se hace clic en el botón "Cerrar"
        }

        btnAdd.setOnClickListener {
            //TODO: Guardar la data
            saveEvent(etDesc.text.toString(), etNombre.text.toString(), cvOt.date.toString())
            dialog.dismiss()
        }
        dialog.show()
    }

    fun savePreferences(result: Boolean){
        // Obtener una referencia al objeto SharedPreferences
        val sharedPreferences: SharedPreferences = getSharedPreferences("MiPref", Context.MODE_PRIVATE)

        // Editor para realizar cambios en SharedPreferences
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        // Almacenar un valor en SharedPreferences
        editor.putBoolean("internalSave", result)
        editor.putBoolean("isFirster", false)
        editor.apply() // Guardar los cambios
    }


    fun getEvents(): EventList {
        val sharedPreferences: SharedPreferences = getSharedPreferences("MiPref", Context.MODE_PRIVATE)

        // Recuperar valores de SharedPreferences
        val filename = "saved_events.txt"
        val result: Boolean? = sharedPreferences.getBoolean("internalSave", false)

        if (result == true) {
            try {
                var content = StringBuilder()
                val fileInputStream = openFileInput(filename)
                val inputStringReader = InputStreamReader(fileInputStream)
                val bufferedReader = BufferedReader(inputStringReader)
                var linea: String? = bufferedReader.readLine()
                while (linea != null) {
                    content.append(linea).append("\n")
                    linea = bufferedReader.readLine()
                }
                var result = Gson().fromJson(content.toString(), EventList::class.java)
                Toast.makeText(this, "Lista recuperada correctamente en true", Toast.LENGTH_SHORT).show()
                bufferedReader.close()
                return result

            }catch (e:Exception) {

            }



        } else if (result == false){
            val file = File(getExternalFilesDir(null), filename)
            try {
                val bufferedReader = BufferedReader(FileReader(file))
                val stringBuilder = StringBuilder()
                var line: String ?
                while (bufferedReader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }
                bufferedReader.close()
                val content = stringBuilder.toString()

                return try {
                    Toast.makeText(this, "Lista recuperada correctamente en false", Toast.LENGTH_SHORT).show()

                    Gson().fromJson(content, EventList::class.java)
                } catch (e: Exception) {
                    Toast.makeText(this, "No se pudo recuperar la lista", Toast.LENGTH_SHORT).show()

                    EventList(eventList = mutableListOf())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return EventList(eventList = mutableListOf())
    }

    fun saveEvent(desc: String, name: String, calendar: String){
        val sharedPreferences: SharedPreferences = getSharedPreferences("MiPref", Context.MODE_PRIVATE)

        // Recuperar valores de SharedPreferences
        val result: Boolean? = sharedPreferences.getBoolean("internalSave", false)
        Toast.makeText(this, "Resultado es $result", Toast.LENGTH_SHORT).show()
        if (result == true) {
            val filename = "saved_events.txt"

            val content =  Event(name, desc, calendar)

            try {
                var eventList = getEvents()
                eventList.eventList.add(content)
                val outputStreamWriter = OutputStreamWriter(openFileOutput(filename, Context.MODE_PRIVATE))
                outputStreamWriter.write(Gson().toJson(eventList).toString())
                outputStreamWriter.close()
                eventos.clear()
                for (event in eventList.eventList){
                    eventos.add(event)
                    nombreEventos.add(event.name)
                }
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Guardado exitosamente en true", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Error en guardado", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        } else if (result == false) {
            val content = Event(name, desc, calendar)
            val file = File(getExternalFilesDir(null), "saved_events.txt")
            try {
                var eventList = getEvents()
                eventList.eventList.add(content)
                val outputStream = FileOutputStream(file)
                outputStream.write(Gson().toJson(eventList).toString().toByteArray())
                outputStream.close()

                eventos.clear()
                for (event in eventList.eventList){
                    eventos.add(event)
                    nombreEventos.add(event.name)
                }

                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Guardado exitosamente en false", Toast.LENGTH_SHORT).show()

            } catch (e:Exception){
                e.printStackTrace()
            }
        }

    }

}