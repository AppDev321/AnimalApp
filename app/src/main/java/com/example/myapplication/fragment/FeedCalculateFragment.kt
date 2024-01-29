package com.example.myapplication.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.activity.QuantityCalculateActivity
import com.example.myapplication.adapter.FeedItemAdapter
import com.example.myapplication.adapter.FeedItemListener
import com.example.myapplication.databinding.FragmentFeedCalculateBinding
import com.example.myapplication.databinding.ItemAnimalDetailBinding
import com.example.myapplication.model.FeedItem
import com.example.myapplication.utils.AppUtils
import com.example.myapplication.utils.DataManagerUtils

/**
 * A placeholder fragment containing a simple view.
 */
class FeedCalculateFragment : Fragment(), FeedItemListener {
    private var _binding: FragmentFeedCalculateBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFeedCalculateBinding.inflate(inflater, container, false)
        val root = binding.root

       val animalDetailData = DataManagerUtils.lifeStageActivityData
        showAnimalDetailData(getString(R.string.name),animalDetailData.animalName)
        showAnimalDetailData(getString(R.string.txt_weight),"${animalDetailData.animalWeight} KG")
        showAnimalDetailData(getString(R.string.txt_avg_daily_gain),""+animalDetailData.avgDailyWeightGain)
        showAnimalDetailData(getString(R.string.txt_life_stage_select),""+animalDetailData.lifeStage)
        showAnimalDetailData(getString(R.string.txt_life_pregnant_stage),""+animalDetailData.pregnancyDuration)
        showAnimalDetailData(getString(R.string.txt_milk_yield),""+animalDetailData.milkYield)
        showAnimalDetailData(getString(R.string.txt_fat),""+animalDetailData.fat)


       binding.mainRecycler.apply {
           layoutManager = LinearLayoutManager(requireActivity())
            adapter = FeedItemAdapter(DataManagerUtils.selectedFeetItem,this@FeedCalculateFragment,showCheck = false,
                showCPCDetails = true)
        }

        binding.btnCalculate.setOnClickListener {
           startActivity(Intent(activity, QuantityCalculateActivity::class.java))
        }


        return root
    }

    private fun showAnimalDetailData (name:String,value:String)
    {
        val animalDataLayout = binding.containerAnimalDetail
        val rowView = ItemAnimalDetailBinding.inflate(LayoutInflater.from(animalDataLayout.context), animalDataLayout, false)
        rowView.txtItemName.text =name
        rowView.txtItemValue.text = value
        animalDataLayout.addView(rowView.root)
    }

    companion object {
        @JvmStatic
        fun newInstance(): FeedCalculateFragment {
            return FeedCalculateFragment()
            }
        }

    override fun feedItemClicked(checked: Boolean, item: FeedItem) {

    }
}




