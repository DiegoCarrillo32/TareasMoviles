package com.kosti.tarea3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TextView
import com.google.gson.Gson

class DetailedEvent : AppCompatActivity() {
    lateinit var eventName: TextView
    lateinit var eventDate: TextView
    lateinit var eventDescription: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_event)
        val event = intent.getStringExtra("event")
        val eventObj = Gson().fromJson(event, Event::class.java)
        eventName = findViewById(R.id.eventName)
        eventDate = findViewById(R.id.eventDate)
        eventDescription = findViewById(R.id.eventDescription)
        eventName.text = eventObj.name
        // the date is in epoch time, so we need to convert it to a readable format ( dd/mm/yyyy )
        val date = DateFormat.format("dd/MM/yyyy", eventObj.date.toLong())
        eventDate.text = date.toString()

        eventDescription.text = eventObj.desc


    }
}