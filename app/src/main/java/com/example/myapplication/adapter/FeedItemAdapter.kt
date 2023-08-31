package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.App
import com.example.myapplication.databinding.RowFeedItemBinding
import com.example.myapplication.model.FeedItem
import com.example.myapplication.utils.AppUtils
import com.example.myapplication.utils.DataManagerUtils
import com.example.myapplication.utils.hide
import com.example.myapplication.utils.show

class FeedItemAdapter(
    private val list: List<FeedItem>,
    private val feedItemClickListener: FeedItemListener,
    val showCheck:Boolean= true,
    val showCPCDetails:Boolean = false
) :
    RecyclerView.Adapter<FeedItemAdapter.MyViewHolder>() {

    inner class MyViewHolder(val viewDataBinding: RowFeedItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedItemAdapter.MyViewHolder {
        val binding =
            RowFeedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: FeedItemAdapter.MyViewHolder, position: Int) {
        holder.viewDataBinding.txtItemName.text =
            if(App.languageSwitcher.currentLocale.language.equals("hi"))
                list[position].hindi
            else if(App.languageSwitcher.currentLocale.language.equals("mr"))
                list[position].marathi
            else
                list[position].name

            if(showCheck) {
                    holder.viewDataBinding.checkbox.show()
                holder.viewDataBinding.checkbox.isChecked =
                    DataManagerUtils.selectedFeetItem.contains(list[position])
            }
        else  holder.viewDataBinding.checkbox.hide()

        if(showCPCDetails)
            holder.viewDataBinding.itemDetailContainer.show()
        else
            holder.viewDataBinding.itemDetailContainer.hide()

        holder.viewDataBinding.txtDm.text = list[position].dm
        holder.viewDataBinding.txtCp.text = list[position].cp
        holder.viewDataBinding.txtTdn.text = list[position].tdn

        holder.viewDataBinding.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            feedItemClickListener.feedItemClicked(isChecked,list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

interface  FeedItemListener{
    fun feedItemClicked(checked:Boolean,item:FeedItem )
}