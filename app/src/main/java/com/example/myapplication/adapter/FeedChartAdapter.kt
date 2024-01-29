package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.FeedItem
import com.example.myapplication.model.IngredientData

class FeedChartAdapter(val itemList: List<FeedItem>) :
    RecyclerView.Adapter<FeedChartAdapter.ModelViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedChartAdapter.ModelViewHolder {

        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_feed_chart_list, parent, false)
        return ModelViewHolder(v)
    }

    override fun onBindViewHolder(holder: FeedChartAdapter.ModelViewHolder, position: Int) {

        val item = itemList[position]
        holder.itemCat.text =  item.categoryName
        holder.itemName.text = item.name.toString()
        holder.itemKG.text = item.amountOfKg.toString()
        holder.itemDCP.text = item.dcp.toString()
        holder.itemDM.text = item.dm.toString()
        holder.itemTDN.text = item.tdn.toString()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemSr: TextView
        var itemName: TextView
        var itemDCP: TextView
        var itemDM: TextView
        var itemTDN: TextView
        var itemKG: TextView
        var itemCat: TextView
        init {
            itemSr = itemView.findViewById(R.id.txtSr) as TextView
            itemDCP = itemView.findViewById(R.id.txtDCP) as TextView
            itemName = itemView.findViewById(R.id.txtName) as TextView
            itemDM = itemView.findViewById(R.id.txtDM) as TextView
            itemTDN = itemView.findViewById(R.id.txtTDN) as TextView
            itemKG = itemView.findViewById(R.id.txtKG) as TextView
            itemCat = itemView.findViewById(R.id.txtCat) as TextView
        }

    }


}


