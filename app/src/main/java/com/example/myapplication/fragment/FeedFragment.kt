package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.activity.FeedActivity
import com.example.myapplication.adapter.SubCategoryAdapter
import com.example.myapplication.databinding.FragmentFeedBinding

/**
 * A placeholder fragment containing a simple view.
 */
class FeedFragment : Fragment() {
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

        val subCatlist = feedData.subCategoryList.filter{
            it.catID == categoryID
        }
binding.mainRecycler.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = SubCategoryAdapter(subCatlist,feedData.feetItemsList)
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
}