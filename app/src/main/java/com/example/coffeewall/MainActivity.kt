package com.example.coffeewall

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.coffeewall.util.MenuButtonsSetuper


class MainActivity : AppCompatActivity() {
    private var cupIsEmpty = true
    private var collections: MutableMap<String, Boolean> = mutableMapOf(
        Pair("Milky", true),
        Pair("Favorite", true),
        Pair("Popular", true),
        Pair("Strong", false),
        Pair("Not for me", false),
        Pair("Check", false)
    ) // todo: load from api

    private lateinit var ivCoffeeMaker: ImageView
    private lateinit var ivCoffeeCup: ImageView

    private lateinit var layoutMainCollections: LinearLayout
    private lateinit var tvDrinkProposal: TextView
    private lateinit var btMainTrack: Button
    private lateinit var btTryAgain: Button
    private lateinit var btDrinkByMood: Button

    private lateinit var menuButtons: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivCoffeeMaker = findViewById(R.id.ivCoffeeMaker)
        ivCoffeeMaker.setOnClickListener { onCoffeeMakerTouch() }
        setupCoffeeMakerBlink(ivCoffeeMaker)

        layoutMainCollections = findViewById(R.id.layoutMainCollections)
        populateCollectionsLayout(layoutMainCollections)

        ivCoffeeCup = findViewById(R.id.ivCup)
        ivCoffeeCup.setOnClickListener { onCoffeeMakerTouch() }

        tvDrinkProposal = findViewById(R.id.tvDrinkProposal)

        btMainTrack = findViewById(R.id.btMainTrack)
        btMainTrack.setOnClickListener { onCoffeeTrackClick() }

        btTryAgain = findViewById(R.id.btMainTryAgain)
        btTryAgain.setOnClickListener { onCoffeeMakerTouch() }

        btDrinkByMood = findViewById(R.id.btMainDrinkByMood)
        btDrinkByMood.setOnClickListener { onDrinkByMoodClick() }


        menuButtons = MenuButtonsSetuper.setupMenuButtons(this@MainActivity)
    }

    private fun populateCollectionsLayout(layoutMainCollections: LinearLayout) {
        for (collection in collections.toList().sortedBy { (_, v) -> !v }) {

            val collectionName = collection.first
            val collectionSelected = collection.second

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.leftMargin = 10

            val btCollection = Button(this)
            btCollection.layoutParams = layoutParams
            btCollection.text = collectionName
            btCollection.isAllCaps = false
            val font = ResourcesCompat.getFont(this, R.font.blogger_sans_bold);
            btCollection.typeface = font
            updateButtonStyleToSelection(btCollection, collectionSelected)
            btCollection.setOnClickListener {
                collections[collectionName] = !collections[collectionName]!!
                layoutMainCollections.removeAllViewsInLayout()
                populateCollectionsLayout(layoutMainCollections)
            }
            layoutMainCollections.addView(btCollection)
        }
    }

    private fun updateButtonStyleToSelection(btCollection: Button, selected: Boolean) {
        if (selected) {
            btCollection.setBackgroundResource(R.drawable.collections_filter_button)
            return
        }
        btCollection.setBackgroundResource(R.drawable.inactive_collections_filter_button)
        btCollection.setTextColor(ContextCompat.getColor(this, R.color.inactive))
    }

    private fun onCoffeeMakerTouch() {
        ivCoffeeMaker.animation = null
        if (cupIsEmpty) {
            setupCoffeeMakerPour(ivCoffeeMaker)
            return
        }
        tvDrinkProposal.visibility = View.INVISIBLE
        ivCoffeeCup.setBackgroundResource(R.drawable.empty_coffee_cup)
        ivCoffeeCup
            .animate()
            .setDuration(1000)
            .rotation(-135f)
            .withEndAction {
                ivCoffeeCup
                    .animate()
                    .setDuration(1000)
                    .rotation(0f)
                    .withEndAction {
                        setupCoffeeMakerPour(ivCoffeeMaker)
                    }
            }
        return
    }

    private fun setupCoffeeMakerPour(imageView: ImageView) {
        imageView.animation = null
        imageView
            .animate()
            .setDuration(1000)
            .rotation(-45f)
            .withEndAction { setupCoffeeMakerPourReturn(imageView) }
    }

    private fun setupCoffeeMakerPourReturn(imageView: ImageView) {
        imageView.animate().setDuration(1000).rotation(0f)
        ivCoffeeCup.setBackgroundResource(R.drawable.coffee_cup)
        tvDrinkProposal.text = "Cappuccino" // todo: fetch from api
        tvDrinkProposal.visibility = View.VISIBLE
        btMainTrack.visibility = View.VISIBLE
        btTryAgain.visibility = View.VISIBLE
        btDrinkByMood.visibility = View.VISIBLE
        cupIsEmpty = false
    }

    private fun setupCoffeeMakerBlink(imageView: ImageView) {
        val blink = AlphaAnimation(1f, 0.5f)
        blink.duration = 1000
        blink.interpolator = LinearInterpolator()
        blink.repeatCount = 10
        blink.repeatMode = Animation.REVERSE
        imageView.animation = blink
    }

    private fun onDrinkByMoodClick() {
        val drinkByMoodIntent = Intent(this@MainActivity, DrinkByMoodActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        this@MainActivity.startActivity(drinkByMoodIntent)
    }

    private fun onCoffeeTrackClick() {
        val intent = Intent(this@MainActivity, TrackDrinkActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        this@MainActivity.startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }
}