package com.rono.toTrade.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rono.toTrade.R
import com.rono.toTrade.app.invisible
import com.rono.toTrade.app.updateStatusBarColor
import com.rono.toTrade.app.visible
import com.rono.toTrade.dataStructures.courses.Course
import com.rono.toTrade.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CartViewModel by viewModels()
    private val cartAdapter: CartAdapter by lazy { CartAdapter(viewModel) }
   // private val cartAdapter: CartAdapter by lazy { CartAdapter(viewModel, this) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root
        requireActivity().updateStatusBarColor(R.color.grey_E3E2E5)
        setupCartObserver()
        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

   // override fun onBuyBtnClicked(item: CartEntity) {
      //  Toast.makeText(requireContext(), "Check Back Soon", Toast.LENGTH_SHORT).show()
    //}

    private fun setupCartObserver() {
        viewModel.readCartFromDB.observe(viewLifecycleOwner) { cartEntity ->
            if (cartEntity.isNotEmpty()) {
                val courses = cartEntity.map { it.course }
                val coursesPrice = calculateCoursesPrice(courses)

                with(binding) {
                    rvCourses.adapter = cartAdapter
                    errorLayout.invisible()
                    rvCourses.visible()
                    tvTotal.text = "Total: ${coursesPrice.sum()}"
                }

                cartAdapter.setData(cartEntity)
                binding.rvCourses.adapter = cartAdapter
            } else {
                with(binding) {
                    errorLayout.visible()
                    rvCourses.invisible()
                    tvTotal.text = "Total: 0.0" // Set a default value if cartEntity is empty
                }
            }
        }
    }

    private fun calculateCoursesPrice(courses: List<Course>): List<Float> {
        return courses.mapNotNull { course ->
            try {
                course.price
                    ?.replace(",", "")
                    ?.removePrefix("E£")
                    ?.removePrefix("$")
                    ?.toFloatOrNull()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}
