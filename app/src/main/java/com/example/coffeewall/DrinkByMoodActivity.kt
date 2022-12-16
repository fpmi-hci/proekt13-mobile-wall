package com.example.coffeewall

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import com.example.coffeewall.util.MenuButtonsSetuper

data class MoodStage constructor(
    val name: String,
    val resID: Int,
    val firstOptionName: String,
    val secondOptionName: String
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readInt(),
        source.readString()!!,
        source.readString()!!
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(this.name)
        dest?.writeInt(this.resID)
        dest?.writeString(this.firstOptionName)
        dest?.writeString(this.secondOptionName)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MoodStage> = object : Parcelable.Creator<MoodStage> {
            override fun createFromParcel(source: Parcel): MoodStage {
                return MoodStage(source)
            }

            override fun newArray(size: Int): Array<MoodStage?> {
                return arrayOfNulls(size)
            }
        }
    }
}

class DrinkByMoodActivity : AppCompatActivity() {
    private lateinit var menuButtons: List<Button>

    private val moodStages = arrayListOf(
        MoodStage("Temperature", R.drawable.temperature, "Cold", "Hot"),
        MoodStage("Milk", R.drawable.milk, "No", "Yes"),
        MoodStage("Strength", R.drawable.strength, "Weak", "Strong")
    )
    private var selectedOptions = ArrayList<Int>(moodStages.size);
    private var curStage: Int = 0

    private var screenWidth = 0

    private lateinit var ivMoodPicture: ImageView
    private lateinit var tvMoodHeader: TextView
    private lateinit var tvFirstOption: TextView
    private lateinit var tvSecondOption: TextView

    private var startPicturePositionX = 0f
    private var startPicturePositionY = 0f
    private var dX = 0f
    private var dY = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_by_mood)

        menuButtons = MenuButtonsSetuper.setupMenuButtons(this@DrinkByMoodActivity)

        if (savedInstanceState != null) {
            curStage = savedInstanceState.getInt("stage")
            selectedOptions = savedInstanceState.getIntegerArrayList("selected") as ArrayList<Int>
        }

        tvMoodHeader = findViewById(R.id.tvMoodHeader)
        tvFirstOption = findViewById(R.id.tvFirstOption)
        tvSecondOption = findViewById(R.id.tvSecondOption)

        ivMoodPicture = findViewById(R.id.ivMoodPicture)
        ivMoodPicture.doOnLayout {
            startPicturePositionX = ivMoodPicture.x
            screenWidth = (startPicturePositionX * 2).toInt()
            startPicturePositionY = ivMoodPicture.y
        }
        ivMoodPicture.setOnTouchListener { v, e -> moveCriteriaPicture(v, e) }

        ivMoodPicture.setBackgroundResource(moodStages[curStage].resID)
        tvMoodHeader.text = moodStages[curStage].name
        tvFirstOption.text = moodStages[curStage].firstOptionName
        tvSecondOption.text = moodStages[curStage].secondOptionName
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt("stage", curStage)
        savedInstanceState.putIntegerArrayList("selected", selectedOptions)
    }

    private fun loadNextStage() {
        if (curStage == moodStages.size - 1) {
            loadDrinkByMoodResult()
            return
        }
        curStage++
        recreate()
    }

    private fun loadDrinkByMoodResult() {
        val resultIntent = Intent(this@DrinkByMoodActivity, DrinkByMoodResultActivity::class.java)
        resultIntent.putExtra("stages", moodStages)
        resultIntent.putExtra("selected", selectedOptions)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        this@DrinkByMoodActivity.startActivity(resultIntent)
        finish()
    }

    private fun moveCriteriaPicture(v: View, e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                dX = v.x - e.rawX
                dY = v.y - e.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                v.animate()
                    .x(e.rawX + dX)
                    .y(e.rawY + dY)
                    .setDuration(0)
                    .start()
                if (onLeftSide(e.rawX + dX)) {
                    tvFirstOption.setTypeface(null, Typeface.BOLD_ITALIC)
                    if (onVeryLeftSide(e.rawX + dX)) {
                        selectedOptions.add(0)
                        loadNextStage()
                    }
                } else if (onRightSide(e.rawX + dX)) {
                    tvSecondOption.setTypeface(null, Typeface.BOLD_ITALIC)
                    if (onVeryRightSide(e.rawX + dX)) {
                        selectedOptions.add(1)
                        loadNextStage()
                    }
                } else {
                    tvFirstOption.typeface = Typeface.DEFAULT
                    tvSecondOption.typeface = Typeface.DEFAULT
                }
            }
            else -> return false
        }
        return true
    }

    private fun onLeftSide(x: Float): Boolean = x / screenWidth < 0.35f
    private fun onRightSide(x: Float): Boolean = x / screenWidth > 0.65f

    private fun onVeryLeftSide(x: Float): Boolean = x / screenWidth < 0.2f
    private fun onVeryRightSide(x: Float): Boolean = x / screenWidth > 0.8f
}

