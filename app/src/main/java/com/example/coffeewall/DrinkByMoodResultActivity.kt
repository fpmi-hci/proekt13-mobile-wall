package com.example.coffeewall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.coffeewall.util.MenuButtonsSetuper

class DrinkByMoodResultActivity : AppCompatActivity() {
    private lateinit var btByMoodToMain: Button
    private lateinit var btByMoodTrackDrink: Button
    private lateinit var layoutSelectedOptions: LinearLayout

    private lateinit var selectedOptions: ArrayList<Int>
    private lateinit var stages: ArrayList<MoodStage>

    private lateinit var menuButtons: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_by_mood_result)

        menuButtons = MenuButtonsSetuper.setupMenuButtons(this@DrinkByMoodResultActivity)

        stages = intent.getParcelableArrayListExtra<MoodStage>("stages") as ArrayList<MoodStage>
        selectedOptions = intent.getIntegerArrayListExtra("selected") as ArrayList<Int>

        btByMoodToMain = findViewById(R.id.btByMoodToMain)
        btByMoodToMain.setOnClickListener { finish() }

        btByMoodTrackDrink = findViewById(R.id.btByMoodTrack)
        btByMoodTrackDrink.setOnClickListener { onCoffeeTrackClick() }

        layoutSelectedOptions = findViewById(R.id.layoutSelectedOptions)
        populateSelectedOptions()
    }

    private fun populateSelectedOptions() {
        for ((index, stage) in stages.withIndex()) {
            val tv = TextView(this)
            if (selectedOptions[index] == 0) tv.text = stage.firstOptionName
            else tv.text = stage.secondOptionName
            tv.setCompoundDrawablesWithIntrinsicBounds(stage.resID, 0, 0, 0)
            tv.textSize = 24f
            val font = ResourcesCompat.getFont(this, R.font.blogger_sans_bold)
            tv.typeface = font
            tv.gravity = Gravity.CENTER
            tv.scaleX = 0.75f
            tv.scaleY = 0.75f
            layoutSelectedOptions.addView(tv)
        }
    }

    private fun onCoffeeTrackClick() {
        val intent = Intent(this@DrinkByMoodResultActivity, TrackDrinkActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        this@DrinkByMoodResultActivity.startActivity(intent)
    }
}