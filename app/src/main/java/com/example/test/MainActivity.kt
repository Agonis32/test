package com.example.test

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var editTextPrice: EditText
    private lateinit var editTextImageUrl: EditText
    private lateinit var editTextId: EditText
    private lateinit var buttonAdd: Button
    private lateinit var buttonDelete: Button
    private lateinit var databaseHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DBHelper(this)

        editTextName = findViewById(R.id.editTextName)
        editTextDescription = findViewById(R.id.editTextDescription)
        editTextPrice = findViewById(R.id.editTextPrice)
        editTextImageUrl = findViewById(R.id.editTextImageUrl)
        editTextId = findViewById(R.id.editTextId)
        buttonAdd = findViewById(R.id.buttonAdd)
        buttonDelete = findViewById(R.id.buttonDelete)

        buttonAdd.setOnClickListener {
            val name = editTextName.text.toString()
            val description = editTextDescription.text.toString()
            val price = editTextPrice.text.toString().toDoubleOrNull() ?: 0.0
            val imageUrl = editTextImageUrl.text.toString()

            if (name.isNotEmpty() && description.isNotEmpty() && price > 0 && imageUrl.isNotEmpty()) {
                databaseHelper.insertProduct(name, description, price, imageUrl)
                Toast.makeText(this, "Product added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please fill in all fields correctly", Toast.LENGTH_SHORT).show()
            }
        }

        buttonDelete.setOnClickListener {
            val id = editTextId.text.toString().toIntOrNull()
            if (id != null) {
                databaseHelper.deleteProduct(id)
                Toast.makeText(this, "Product deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter a valid product ID", Toast.LENGTH_SHORT).show()
            }
        }
    }

    class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(SQL_CREATE_ENTRIES)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL(SQL_DELETE_ENTRIES)
            onCreate(db)
        }

        fun insertProduct(name: String, description: String, price: Double, imageUrl: String) {
            val db = writableDatabase
            val values = ContentValues().apply {
                put("name", name)
                put("description", description)
                put("price", price)
                put("imageUrl", imageUrl)
            }
            db.insert("Products", null, values)
        }

        fun deleteProduct(id: Int) {
            val db = writableDatabase
            db.delete("Products", "id = ?", arrayOf(id.toString()))
        }

        companion object {
            const val DATABASE_VERSION = 1
            const val DATABASE_NAME = "Products.db"
            private const val SQL_CREATE_ENTRIES =
                "CREATE TABLE Products (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT, price REAL, imageUrl TEXT)"
            private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS Products"
        }
    }
}