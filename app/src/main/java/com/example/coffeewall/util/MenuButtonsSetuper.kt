package com.example.coffeewall.util

import android.content.Intent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat.getDrawable
import com.example.coffeewall.CoffeeBookActivity
import com.example.coffeewall.DiaryActivity
import com.example.coffeewall.MainActivity
import com.example.coffeewall.R


class MenuButtonsSetuper {
    companion object {
        private val activityClasses =
            listOf(DiaryActivity::class.java, MainActivity::class.java, CoffeeBookActivity::class.java)

        fun setupMenuButtons(activity: AppCompatActivity): List<Button> {
            val menuButtons = listOf(
                activity.findViewById<Button>(R.id.btMenuTab1),
                activity.findViewById(R.id.btMenuTab2),
                activity.findViewById(R.id.btMenuTab3)
            )

            for ((i, activityClass) in activityClasses.withIndex()) {
                if (activityClass == activity.javaClass) {
                    menuButtons[i].setBackgroundResource(R.drawable.menu_active_button)
                    continue
                }
                menuButtons[i].setOnClickListener {
                    val intent = Intent(activity, activityClass)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    activity.startActivity(intent)
                }
            }

            return menuButtons
        }
    }
}