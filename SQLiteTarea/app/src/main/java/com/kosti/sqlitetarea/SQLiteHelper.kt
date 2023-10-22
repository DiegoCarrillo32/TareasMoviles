package com.kosti.sqlitetarea

import android.content.ContentValues
import android.content.Context

class SQLiteHelper {
    companion object {
        const val TABLE_NAME = "Producto"
    }

    constructor(context: Context){
        SQLiteConnector.initialize(context)
    }

    fun readProducto(): ArrayList<Producto>{
        val db = SQLiteConnector.getWriteableDatabase()
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val productos = ArrayList<Producto>()
        if(cursor.moveToFirst()){
            do{
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val desc = cursor.getString(cursor.getColumnIndex("description"))
                productos.add(Producto(id, name, desc))

            }while(cursor.moveToNext())
        }
        SQLiteConnector.closeDatabase()
        return productos
    }

    fun insertProducto(producto: Producto){
        val db = SQLiteConnector.getWriteableDatabase()
        val values = ContentValues()
        values.put("name", producto.name)
        values.put("description", producto.desc)
        db.insert(TABLE_NAME, null, values)
        SQLiteConnector.closeDatabase()
    }

    fun deleteProducto(id: Int){
        val db = SQLiteConnector.getWriteableDatabase()
        db.delete(TABLE_NAME, "id = ?", arrayOf(id.toString()))
        SQLiteConnector.closeDatabase()
    }

    fun editProducto(producto: Producto){
        val db = SQLiteConnector.getWriteableDatabase()
        val values = ContentValues()
        values.put("name", producto.name)
        values.put("description", producto.desc)
        db.update(TABLE_NAME, values, "id = ?", arrayOf(producto.id.toString()))
        SQLiteConnector.closeDatabase()
    }


}