package com.example.test

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "shoppingapp.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "grocerylist"
        const val COLUMN_ID = "id"
        const val COLUMN_PRODUCT_NAME = "productname"
        const val COLUMN_PRICE = "price"
        const val COLUMN_INFO = "info"
        const val COLUMN_IMAGE = "image"
        const val COLUMN_RATING = "rating"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PRODUCT_NAME + " TEXT NOT NULL,"
                + COLUMN_INFO + " TEXT,"
                + COLUMN_PRICE + " REAL NOT NULL,"
                + COLUMN_IMAGE + " TEXT,"
                + COLUMN_RATING + " REAL NOT NULL" + ")")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertProduct(productname: String, info: String, price: Double, image: String, rating: Double){
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PRODUCT_NAME, productname)
            put(COLUMN_INFO, info)
            put(COLUMN_PRICE, price)
            put(COLUMN_IMAGE, image)
            put(COLUMN_RATING, rating)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun deleteProduct(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

    fun getAllProducts(): List<Product> {
        val productList = ArrayList<Product>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                val product = Product(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INFO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_RATING))
                )
                productList.add(product)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return productList
    }
}
