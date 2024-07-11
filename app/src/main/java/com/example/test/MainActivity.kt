package com.example.test

import android.content.Intent
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
    private lateinit var editTextRating: EditText
    private lateinit var editTextId: EditText
    private lateinit var buttonAdd: Button
    private lateinit var buttonDelete: Button
    private lateinit var buttonViewGroceryList: Button
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)

        editTextName = findViewById(R.id.editTextName)
        editTextDescription = findViewById(R.id.editTextDescription)
        editTextPrice = findViewById(R.id.editTextPrice)
        editTextImageUrl = findViewById(R.id.editTextImageUrl)
        editTextRating = findViewById(R.id.editTextRating)
        editTextId = findViewById(R.id.editTextId)
        buttonAdd = findViewById(R.id.buttonAdd)
        buttonDelete = findViewById(R.id.buttonDelete)
        buttonViewGroceryList = findViewById(R.id.buttonViewGroceryList)

        buttonAdd.setOnClickListener {
            val name = editTextName.text.toString()
            val description = editTextDescription.text.toString()
            val price = editTextPrice.text.toString().toDoubleOrNull() ?: 0.0
            val imageUrl = editTextImageUrl.text.toString()
            val rating = editTextRating.text.toString().toDoubleOrNull() ?: 0.0

            if (name.isNotEmpty() && description.isNotEmpty() && price > 0 && imageUrl.isNotEmpty() && rating > 0) {
                databaseHelper.insertProduct(name, description, price, imageUrl, rating)
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

        buttonViewGroceryList.setOnClickListener {
            startActivity(Intent(this, GroceryListActivity::class.java))
        }
    }
}
