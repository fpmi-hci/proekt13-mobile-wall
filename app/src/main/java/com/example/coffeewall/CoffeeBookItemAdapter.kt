package com.example.coffeewall

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeewall.data.model.Drink

class CoffeeBookItemAdapter internal constructor(context: Context?, data: List<Drink>) :
    RecyclerView.Adapter<CoffeeBookItemAdapter.ViewHolder>() {
    private val mData: List<Drink>
    private val mInflater: LayoutInflater
    private var mClickListener: ItemClickListener? = null
    private val mContext: Context

    init {
        mContext = context!!
        mInflater = LayoutInflater.from(context)
        mData = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = mInflater.inflate(R.layout.coffee_book_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val drink = mData[position]
        holder.tvDrinkName.text = drink.name
        holder.tvCalories.text = String.format(mContext.getString(R.string.сalories), drink.calories)
        holder.tvDescription.text = drink.description
        populateCollectionsLayout(holder.layoutCoffeeBookCollections, drink.collections)
    }

    private fun populateCollectionsLayout(layout: LinearLayout, collections: Array<String>) {
        for (collection in collections) {
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.leftMargin = 10

            val btCollection = Button(layout.context)
            btCollection.layoutParams = layoutParams
            btCollection.text = collection
            btCollection.isAllCaps = false
            val font = ResourcesCompat.getFont(layout.context, R.font.blogger_sans_bold);
            btCollection.typeface = font
            btCollection.setBackgroundResource(R.drawable.collections_filter_button)
            layout.addView(btCollection)
        }
    }


    override fun getItemCount(): Int {
        return mData.size
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var tvDrinkName: TextView
        var tvCalories: TextView
        var tvDescription: TextView
        var svCollections: HorizontalScrollView
        var layoutCoffeeBookCollections: LinearLayout

        init {
            tvDrinkName = itemView.findViewById(R.id.tvDrinkName)
            tvCalories = itemView.findViewById(R.id.tvCalories)
            tvDescription = itemView.findViewById(R.id.tvDescription)
            svCollections = itemView.findViewById(R.id.scrollCoffeeBookCollections)
            layoutCoffeeBookCollections = itemView.findViewById(R.id.layoutCoffeeBookCollections)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }
    }

    fun getItem(id: Int): String {
        return mData[id].name
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}