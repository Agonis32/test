package com.example.test

data class Product(
    val id: Int,
    val name: String,
    val info: String,
    val image: String,
    val price: Double,
    val rating: Double,
    var inCart: Boolean = false,
    var amount: Int = 1 // New property for quantity
)
