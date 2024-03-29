package com.example.myapplication.fragment

import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.activity.FeedActivity
import com.example.myapplication.adapter.FeedItemAdapter
import com.example.myapplication.adapter.FeedItemListener
import com.example.myapplication.databinding.FragmentFeedBinding
import com.example.myapplication.model.FeedItem
import com.example.myapplication.utils.DataManagerUtils
import com.example.myapplication.utils.safeGet

/**
 * A placeholder fragment containing a simple view.
 */
class FeedFragment : Fragment() ,FeedItemListener {
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val root = binding.root

        val categoryID = arguments?.getInt(ARG_SECTION_NUMBER)

        val feedData = FeedActivity.feedResponseData
        val catName = DataManagerUtils.feedDataResponse.categoryList.find { it.id == categoryID }?.name

        val catlist = feedData.feetItemsList.filter{
            it.catID == categoryID
        }.map {
            it.copy(categoryName = catName.safeGet())
        }
            binding.mainRecycler.apply {
            layoutManager = LinearLayoutManager(requireActivity())
             adapter= FeedItemAdapter(catlist,this@FeedFragment)

        }


        return root
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        @JvmStatic
        fun newInstance(categoryID: Int): FeedFragment {
            return FeedFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, categoryID)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun feedItemClicked(checked: Boolean, item: FeedItem) {
        if(checked)
            DataManagerUtils.selectedFeetItem.add(item)
        else
            DataManagerUtils.selectedFeetItem.remove(item)

        DataManagerUtils.allSelectedItems.clear()
        DataManagerUtils.allSelectedItems.addAll(DataManagerUtils.selectedFeetItem)
    }
}