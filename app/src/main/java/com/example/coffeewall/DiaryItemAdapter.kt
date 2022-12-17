package com.example.coffeewall

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeewall.data.model.DiaryEntry
import java.text.SimpleDateFormat

class DiaryItemAdapter(context: Context, data: List<DiaryEntry>) :
    RecyclerView.Adapter<DiaryItemAdapter.ViewHolder>() {
    private val mData: List<DiaryEntry>
    private val mInflater: LayoutInflater
    private var mClickListener: ItemClickListener? = null
    private val mContext: Context
    private val timeFormatter = SimpleDateFormat("hh:mm")

    init {
        mContext = context
        mInflater = LayoutInflater.from(context)
        mData = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = mInflater.inflate(R.layout.diary_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = mData[position]
        holder.tvDiaryTime.text = timeFormatter.format(entry.time!!)
        holder.tvDiaryDrinkName.text = entry.drinkName
        holder.tvDiaryDrinkSize.text = entry.drinkSize
        holder.tvDiaryLocation.text = entry.location
        holder.tvDiaryMark.text = entry.mark
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var tvDiaryTime: TextView
        var tvDiaryDrinkName: TextView
        var tvDiaryDrinkSize: TextView
        var tvDiaryLocation: TextView
        var tvDiaryMark: TextView

        init {
            tvDiaryTime = itemView.findViewById(R.id.tvDiaryTime)
            tvDiaryDrinkName = itemView.findViewById(R.id.tvDiaryDrinkName)
            tvDiaryDrinkSize = itemView.findViewById(R.id.tvDiaryDrinkSize)
            tvDiaryLocation = itemView.findViewById(R.id.tvDiaryLocation)
            tvDiaryMark = itemView.findViewById(R.id.tvDiaryMark)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }
    }

    fun getItem(id: Int): String {
        return mData[id].drinkName
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}
