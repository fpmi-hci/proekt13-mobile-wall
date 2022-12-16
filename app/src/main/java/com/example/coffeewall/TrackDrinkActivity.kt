package com.example.coffeewall

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeewall.util.MenuButtonsSetuper

class TrackDrinkActivity : AppCompatActivity() {
    private lateinit var menuButtons: List<Button>

    private lateinit var spinnerDrink: Spinner
    private lateinit var spinnerSize: Spinner
    private lateinit var spinnerLocation: Spinner
    private lateinit var spinnerMark: Spinner

    private lateinit var btTrack: Button

    private val drinks =
        arrayOf("Select a drink...", "Latte", "Cappuccino", "Americano", "Espresso")
    private val sizes = arrayOf("Select a size...", "XS", "S", "M", "L", "XL")
    private val locations = arrayOf("Select a location...", "Home", "Work", "Café", "Other")
    private val marks =
        arrayOf("Select your mark...", "Excellent", "Good", "Average", "Poor", "Bad")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_drink)

        menuButtons = MenuButtonsSetuper.setupMenuButtons(this@TrackDrinkActivity)

        spinnerDrink = findViewById(R.id.spinnerDrink)
        setupDropdownValues(spinnerDrink, drinks)

        spinnerSize = findViewById(R.id.spinnerSize)
        setupDropdownValues(spinnerSize, sizes)

        spinnerLocation = findViewById(R.id.spinnerLocation)
        setupDropdownValues(spinnerLocation, locations)

        spinnerMark = findViewById(R.id.spinnerMark)
        setupDropdownValues(spinnerMark, marks)

        btTrack = findViewById(R.id.btTrack)
        btTrack.setOnClickListener { onTrackClick() }
    }

    private fun setupDropdownValues(spinner: Spinner, values: Array<String>) {
        val adapter =
            object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values) {
                override fun isEnabled(position: Int): Boolean {
                    return position != 0
                }

                override fun getDropDownView(
                    position: Int,
                    convertView: View?,
                    parent: ViewGroup
                ): View {
                    val view: TextView =
                        super.getDropDownView(position, convertView, parent) as TextView
                    if (position == 0) {
                        view.setTextColor(Color.GRAY)
                    }
                    return view
                }
            }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun onTrackClick() {
        val selectedDrink = spinnerDrink.selectedItemPosition
        val selectedSize = spinnerSize.selectedItemPosition
        val selectedLocation =
            if (spinnerLocation.selectedItemPosition == 0) locations.size - 1 else spinnerLocation.selectedItemPosition
        val selectedMark = spinnerMark.selectedItemPosition

        if (selectedDrink == 0) {
            Toast.makeText(this, getString(R.string.track_select_drink), Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedSize == 0) {
            Toast.makeText(this, getString(R.string.track_select_size), Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedMark == 0) {
            Toast.makeText(this, getString(R.string.track_select_mark), Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(this, getString(R.string.track_success), Toast.LENGTH_LONG).show()
        finish()
    }
}