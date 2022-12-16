package com.example.coffeewall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.coffeewall.util.MenuButtonsSetuper

class DiaryActivity : AppCompatActivity() {
    private lateinit var menuButtons: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        menuButtons = MenuButtonsSetuper.setupMenuButtons(this@DiaryActivity)
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }
}