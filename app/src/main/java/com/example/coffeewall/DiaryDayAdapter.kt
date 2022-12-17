package com.example.coffeewall

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeewall.data.model.DiaryEntry
import java.text.SimpleDateFormat

class DiaryDayAdapter internal constructor(context: Context?, data: List<Pair<String, List<DiaryEntry>>>) :
    RecyclerView.Adapter<DiaryDayAdapter.ViewHolder>() {
    private val mData: List<Pair<String, List<DiaryEntry>>>
    private val mInflater: LayoutInflater
    private var mClickListener: ItemClickListener? = null
    private val mContext: Context
    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy")
    private val outputDateFormatter = SimpleDateFormat("EEE, MMM d")

    init {
        mContext = context!!
        mInflater = LayoutInflater.from(context)
        mData = data.sortedByDescending { dateWithDrink -> dateWithDrink.first }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = mInflater.inflate(R.layout.diary_day, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = mData[position]
        holder.tvDay.text = outputDateFormatter.format(dateFormatter.parse(entry.first)!!)
        setupRecycleView(holder.rvCoffeeEntries, entry.second)
    }

    private fun setupRecycleView(rv: RecyclerView, data: List<DiaryEntry>) {
        val layoutManager = LinearLayoutManager(mContext)
        rv.layoutManager = layoutManager
        rv.adapter = DiaryItemAdapter(mContext, data)
        val dividerItemDecoration =
            DividerItemDecoration(mContext, layoutManager.orientation)
        dividerItemDecoration.setDrawable(mContext.resources.getDrawable(R.drawable.drinks_divider, null))
        rv.addItemDecoration(dividerItemDecoration)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var rvCoffeeEntries: RecyclerView
        var tvDay: TextView

        init {
            rvCoffeeEntries = itemView.findViewById(R.id.rvCoffeeEntries)
            tvDay = itemView.findViewById(R.id.tvDay)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }
    }

    fun getItem(id: Int): String {
        return mData[id].first
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}