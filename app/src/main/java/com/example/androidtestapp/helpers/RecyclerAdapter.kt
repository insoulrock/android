package com.example.androidtestapp.helpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_recycler_view.view.*
import com.example.androidtestapp.R
import com.example.androidtestapp.models.TickerModel
import kotlin.collections.ArrayList

class RecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private var items: List<TickerModel> = ArrayList()
    private val colorWinner: Int = Color.parseColor("#33AD2F")
    private val colorLoser: Int = Color.parseColor("#FF0000")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_recycler_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var ticker = items[position]

        if(ticker.last == null) return;

        holder.itemView.tickerName.setText(ticker.instrument)
        holder.itemView.tickerCourse.setText(String.format("%.10f", ticker.last))

        var percentChange = ticker.percentChange?.toDouble()
        if(percentChange != null) {
            var percentChangeDesc = ""

            if (percentChange > 0)
            {
                holder.itemView.tickerPercentChange.setTextColor(colorWinner)
                percentChangeDesc = "+$percentChange%"
            }
            else if (percentChange < 0){
                holder.itemView.tickerPercentChange.setTextColor(colorLoser)
                percentChangeDesc = "$percentChange%"
            }
            holder.itemView.tickerPercentChange.setText(percentChangeDesc)
        }
    }

    override fun getItemCount() = items.size

    fun submitList(tickers: List<TickerModel>){
        items = tickers
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}