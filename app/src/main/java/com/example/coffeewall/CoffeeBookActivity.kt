package com.example.coffeewall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.coffeewall.util.MenuButtonsSetuper

class CoffeeBookActivity : AppCompatActivity() {
    private lateinit var menuButtons: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coffee_book)

        MenuButtonsSetuper.setupMenuButtons(this@CoffeeBookActivity)
    }
}