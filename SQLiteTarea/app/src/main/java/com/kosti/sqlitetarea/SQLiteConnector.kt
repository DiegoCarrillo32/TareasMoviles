package com.kosti.sqlitetarea

import android.content.Context
import android.database.sqlite.SQLiteDatabase

object SQLiteConnector {
    private var dbHelper: DatabaseHelper? = null

    fun initialize(context: Context) {
        dbHelper = DatabaseHelper(context)
    }

    fun getWriteableDatabase(): SQLiteDatabase {
        return dbHelper!!.writableDatabase
    }

    fun closeDatabase() {
        dbHelper?.close()
    }
}