package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.App
import com.example.myapplication.databinding.RowFeedItemBinding
import com.example.myapplication.model.FeedItem

class FeedItemAdatper(private val list: List<FeedItem>) :
    RecyclerView.Adapter<FeedItemAdatper.MyViewHolder>() {

    inner class MyViewHolder(val viewDataBinding: RowFeedItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedItemAdatper.MyViewHolder {
        val binding =
            RowFeedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: FeedItemAdatper.MyViewHolder, position: Int) {
        holder.viewDataBinding.txtItemName.text =
            if(App.languageSwitcher.currentLocale.language.equals("hi"))
                list[position].hindi
            else if(App.languageSwitcher.currentLocale.language.equals("mr"))
                list[position].marathi
            else
                list[position].name



        holder.viewDataBinding.txtDm.text = list[position].dm
        holder.viewDataBinding.txtCp.text = list[position].cp
        holder.viewDataBinding.txtTdn.text = list[position].tdn
    }

    override fun getItemCount(): Int {
        return list.size
    }
}