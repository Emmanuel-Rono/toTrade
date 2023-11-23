package com.rono.toTrade.ui.cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rono.toTrade.R
import com.rono.toTrade.app.invisible
import com.rono.toTrade.app.updateStatusBarColor
import com.rono.toTrade.app.visible
import com.rono.toTrade.dataSources.local.room.entities.CartEntity
import com.rono.toTrade.dataStructures.courses.Course
import com.rono.toTrade.databinding.FragmentCartBinding

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CartFragment : Fragment(), CartAdapter.onViewClickListener {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!



    private val viewModel: CartViewModel by viewModels()
    private val cartAdapter: CartAdapter by lazy { CartAdapter(viewModel, this) }
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root
        requireActivity().updateStatusBarColor(R.color.grey_E3E2E5)
        viewModel.readCartFromDB.observe(viewLifecycleOwner) { cartEntity ->
            val courses = mutableListOf<Course>()
            val coursesPrice = mutableListOf<Float>()
            if (cartEntity.isNotEmpty()) {
                cartEntity.forEach { cartCourse ->
                    courses.add(cartCourse.course)
                    try {
                        coursesPrice.add(
                            cartCourse.course.price?.replace(",", "")?.removePrefix("E£")?.toFloat()
                                ?: 0.0f

                        )
                        Log.d("CartFragment", "Total: ${coursesPrice.sum()}")

                    }catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                cartAdapter.setData(cartEntity)
                binding.rvCourses.adapter = cartAdapter
            }
            binding.errorLayout.apply {
                if (cartEntity.isEmpty()) visible() else invisible()
            }
            binding.rvCourses.apply {
                if (cartEntity.isNotEmpty()) visible() else invisible()
            }
            binding.tvTotal.text = "Total: ${coursesPrice.sum()}"
        }
        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onBuyBtnClicked(item: CartEntity) {
        Toast.makeText(requireContext(),"Check Back Soon",Toast.LENGTH_SHORT).show()

}


}