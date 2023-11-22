package com.rono.toTrade.ui.cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import bensalcie.payhero.mpesa.mpesa.model.AccessToken
import bensalcie.payhero.mpesa.mpesa.services.DarajaApiClient
import bensalcie.payhero.mpesa.mpesa.services.Environment
import com.rono.toTrade.R
import com.rono.toTrade.app.invisible
import com.rono.toTrade.app.updateStatusBarColor
import com.rono.toTrade.app.visible
import com.rono.toTrade.dataStructures.courses.Course
import com.rono.toTrade.databinding.FragmentCartBinding

import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception
import retrofit2.Callback


@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CartViewModel by viewModels()
    private val cartAdapter: CartAdapter by lazy { CartAdapter(viewModel) }
    var mApiClient: DarajaApiClient? = null //Intitialization before on create

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root

        requireActivity().updateStatusBarColor(R.color.grey_E3E2E5)

        mApiClient = DarajaApiClient(
            "xxxconsumerkeyxxxxx",
            "xxxxxconsumersecretxxxx",
            Environment.SANDBOX
        )
        //use Environment.PRODUCTION when on production
        //get consumerkey and secret from https://developer.safaricom.co.ke/user/me/apps
        mApiClient!!.setIsDebug(true) //Set True to enable logging, false to disable.
        getAccessToken()//make request availabe and ready for processing.



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

    private fun getAccessToken() {
        mApiClient!!.setGetAccessToken(true)
        mApiClient!!.mpesaService()!!.getAccessToken().enqueue(object : Callback<AccessToken> {
            override fun onResponse(call: Call<AccessToken?>, response: Response<AccessToken>) {
                if (response.isSuccessful) {
                    mApiClient!!.setAuthToken(response.body()?.accessToken)
                }
            }
            override fun onFailure(call: Call<AccessToken?>, t: Throwable) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}