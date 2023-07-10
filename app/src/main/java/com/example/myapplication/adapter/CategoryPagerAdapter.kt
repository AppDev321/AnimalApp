package com.example.myapplication.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.myapplication.App
import com.example.myapplication.AppLocale
import com.example.myapplication.fragment.FeedFragment
import com.example.myapplication.model.FeedDataResponse


class CategoryPagerAdapter(private val context: Context, fm: FragmentManager,private val feedData: FeedDataResponse) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return FeedFragment.newInstance( feedData.categoryList[position].id!!)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(App().getCurrentAppLocale())
        {
           AppLocale.ENG->
               feedData.categoryList[position].name
            AppLocale.HINDI->
                feedData.categoryList[position].hindi
            AppLocale.MARATHI->
                feedData.categoryList[position].marathi
        }
    }

    override fun getCount(): Int {
        return feedData.categoryList.size
    }
}