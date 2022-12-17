package com.example.coffeewall

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeewall.data.model.Drink
import com.example.coffeewall.util.MenuButtonsSetuper


class CoffeeBookActivity : AppCompatActivity() {
    private lateinit var menuButtons: List<Button>
    private lateinit var rvDrinks: RecyclerView

    private val drinks = listOf(
        Drink(
            "Cappuccino",
            31,
            "The perfect balance of espresso, steamed milk and foam",
            arrayOf("Milky", "Popular", "Favorite")
        ),
        Drink(
            "Latte",
            28,
            "Milk coffee that is a made up of " +
                    "one or two shots of espresso, steamed milk and a final, thin layer of frothed milk on top.",
            arrayOf("Milky", "Popular")
        ),
        Drink(
            "Americano",
            10,
            "Made by pouring hot water over one or two espresso shots, resulting in a drink " +
                    "of similar volume and strength to regular coffee. ",
            arrayOf("Popular")
        ),
        Drink(
            "Espresso",
            2,
            "A process of brewing coffee and is instead made by forcing high-pressured hot" +
                    " water through very finely ground coffee beans",
            arrayOf("Popular", "Strong", "Not for me")
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coffee_book)

        menuButtons = MenuButtonsSetuper.setupMenuButtons(this@CoffeeBookActivity)

        rvDrinks = findViewById(R.id.rvDrinks)
        setupRecycleView(rvDrinks)
    }

    private fun setupRecycleView(rv: RecyclerView) {
        val layoutManager = LinearLayoutManager(this@CoffeeBookActivity)
        rv.layoutManager = layoutManager
        rv.adapter = CoffeeBookItemAdapter(this@CoffeeBookActivity, drinks)
        val dividerItemDecoration =
            DividerItemDecoration(rv.context, layoutManager.orientation)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.drinks_divider, null))
        rv.addItemDecoration(dividerItemDecoration)
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }
}