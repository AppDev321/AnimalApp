package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.App
import com.example.myapplication.databinding.RowFeedSubCategoryItemBinding
import com.example.myapplication.model.FeedItem
import com.example.myapplication.model.FeedSubCategory

class SubCategoryAdapter(val list: List<FeedSubCategory>,val itemlist : List<FeedItem>) : RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder>() {

    inner class MyViewHolder(val viewDataBinding: RowFeedSubCategoryItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryAdapter.MyViewHolder {
        val binding =
            RowFeedSubCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubCategoryAdapter.MyViewHolder, position: Int) {
        holder.viewDataBinding.subCategory.text=
            if(App.languageSwitcher.currentLocale.language.equals("hi"))
                list[position].hindi
            else if(App.languageSwitcher.currentLocale.language.equals("mr"))
                list[position].marathi
            else
                list[position].name





        holder.viewDataBinding.feedItemRecycler.apply {
           val listItems = itemlist.filter { item ->
               item.subCatID == list[position].id
        }
            adapter= FeedItemAdatper(listItems)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}