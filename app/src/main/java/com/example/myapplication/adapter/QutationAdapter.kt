package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.IngredientData

class QutationAdapter(val itemList: List<IngredientData>) :
    RecyclerView.Adapter<QutationAdapter.ModelViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QutationAdapter.ModelViewHolder {

        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_qutation_list, parent, false)
        return ModelViewHolder(v)
    }

    override fun onBindViewHolder(holder: QutationAdapter.ModelViewHolder, position: Int) {

        val item = itemList[position]
        holder.itemSr.text = "${position+1}"
        holder.itemName.text = item.name.toString()
        holder.itemCP.text = item.cp.toString()
        holder.itemQty.text = item.qty.toString()
        holder.itemPrice.text = "${String.format("%.2f", item.price)}"
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemSr: TextView
        var itemName: TextView
        var itemCP: TextView
        var itemPrice: TextView
        var itemQty: TextView


        init {
            itemSr = itemView.findViewById(R.id.txtSr) as TextView
            itemCP = itemView.findViewById(R.id.txtCP) as TextView
            itemName = itemView.findViewById(R.id.txtName) as TextView
            itemPrice = itemView.findViewById(R.id.txtPrice) as TextView
            itemQty = itemView.findViewById(R.id.txtQty) as TextView


        }

    }


}


