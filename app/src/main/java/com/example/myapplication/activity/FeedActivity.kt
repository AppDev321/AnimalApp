package com.example.myapplication.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityFeedBinding
import com.example.myapplication.model.FeedDataResponse
import com.example.myapplication.adapter.CategoryPagerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout

class FeedActivity : AppCompatActivity() {
    companion object{
        var feedResponseData = FeedDataResponse()
    }

    private lateinit var binding: ActivityFeedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val categoryPagerAdapter = CategoryPagerAdapter(this, supportFragmentManager,
                feedResponseData
            )
            val viewPager: ViewPager = binding.viewPager
            viewPager.adapter = categoryPagerAdapter
            val tabs: TabLayout = binding.tabs
            tabs.setupWithViewPager(viewPager)



        val fab: FloatingActionButton = binding.fab
        fab.visibility = View.GONE
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}