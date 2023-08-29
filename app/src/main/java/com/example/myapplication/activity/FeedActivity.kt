package com.example.myapplication.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.R
import com.example.myapplication.adapter.CategoryPagerAdapter
import com.example.myapplication.databinding.ActivityFeedBinding
import com.example.myapplication.fragment.FeedCalculateFragment
import com.example.myapplication.model.FeedDataResponse
import com.example.myapplication.utils.AppUtils
import com.example.myapplication.utils.DataManagerUtils
import com.example.myapplication.utils.hide
import com.example.myapplication.utils.invisible
import com.example.myapplication.utils.show
import com.example.myapplication.viewmodel.AppViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout

class FeedActivity : AppCompatActivity() {

    companion object {
        var feedResponseData = FeedDataResponse()
    }

    private lateinit var binding: ActivityFeedBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        feedResponseData = DataManagerUtils.feedDataResponse
        val categoryPagerAdapter = CategoryPagerAdapter(
            this, supportFragmentManager,
            feedResponseData
        )
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = categoryPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        binding.fragment.hide()

        binding.txtNext.setOnClickListener{
            binding.tabs.hide()
            binding.viewPager.hide()
            binding.txtNext.hide()
            binding.fragment.show()
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            val fragment = FeedCalculateFragment.newInstance()
            fragmentTransaction.add(R.id.fragment, fragment)

            fragmentTransaction.commit()
        }
    }
    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        val fragment = fragmentManager.findFragmentById(R.id.fragment)

        if (fragment != null) {

            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.remove(fragment)
            fragmentTransaction.commit()

            binding.tabs.show()
            binding.viewPager.show()
            binding.txtNext.show()
            binding.fragment.hide()
        } else {
            super.onBackPressed()
        }
    }
}