package com.example.coffeewall

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeewall.util.MenuButtonsSetuper

class CoffeeBookActivity : AppCompatActivity() {
    private lateinit var menuButtons: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coffee_book)

        MenuButtonsSetuper.setupMenuButtons(this@CoffeeBookActivity)
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }
}