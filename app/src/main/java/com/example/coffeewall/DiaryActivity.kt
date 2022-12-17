package com.example.coffeewall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeewall.data.model.DiaryEntry
import com.example.coffeewall.util.MenuButtonsSetuper
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class DiaryActivity : AppCompatActivity() {
    val dateTimeFormatter = SimpleDateFormat("dd.MM.yyyy hh:mm")
    val dateFormatter = SimpleDateFormat("dd.MM.yyyy")
    private val diary = listOf(
        DiaryEntry(
            dateTimeFormatter.parse("14.11.2022 10:15"),
            "Cappuccino",
            "M",
            "Home",
            "Good",
        ),
        DiaryEntry(
            dateTimeFormatter.parse("14.11.2022 20:28"),
            "Latte",
            "L",
            "Café",
            "Excellent",
        ),
        DiaryEntry(
            dateTimeFormatter.parse("15.11.2022 12:30"),
            "Cappuccino",
            "XL",
            "Work",
            "Good",
        )
    )

    private lateinit var menuButtons: List<Button>
    private lateinit var rvDiaryDays: RecyclerView
    private lateinit var btTrack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        menuButtons = MenuButtonsSetuper.setupMenuButtons(this@DiaryActivity)

        rvDiaryDays = findViewById(R.id.rvDiaryDays)
        setupRecycleView(rvDiaryDays)

        btTrack = findViewById(R.id.btDiaryTrack)
        btTrack.setOnClickListener { onCoffeeTrackClick() }
    }

    private fun setupRecycleView(rv: RecyclerView) {
        val layoutManager = LinearLayoutManager(this@DiaryActivity)
        rv.layoutManager = layoutManager
        rv.adapter = DiaryDayAdapter(this@DiaryActivity, splitDiaryByDays(diary).toList())
        val dividerItemDecoration =
            DividerItemDecoration(rv.context, layoutManager.orientation)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.drinks_divider, null))
        rv.addItemDecoration(dividerItemDecoration)
    }

    private fun splitDiaryByDays(diary: List<DiaryEntry>): Map<String, List<DiaryEntry>> {
        val diaryMap = HashMap<String, MutableList<DiaryEntry>>()
        for (entry in diary) {
            val key = dateFormatter.format(entry.time!!)
            if (diaryMap[key] == null) {
                diaryMap[key] = ArrayList<DiaryEntry>()
            }
            diaryMap[key]!!.add(entry)
        }
        return diaryMap
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }


    private fun onCoffeeTrackClick() {
        val intent = Intent(this@DiaryActivity, TrackDrinkActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        this@DiaryActivity.startActivity(intent)
    }
}