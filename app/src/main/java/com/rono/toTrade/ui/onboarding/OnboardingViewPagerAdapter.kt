package com.cipl.onboardingscreen.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.cipl.onboardingscreen.R
import com.cipl.onboardingscreen.model.OnboardingData

class OnboardingViewPagerAdapter(
    private var context: Context,
    private var onboardingDataList: List<OnboardingData>
) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return onboardingDataList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.onboarding_item_layout, null)
        val imageView: ImageView
        val title: TextView
        val description: TextView

        imageView = view.findViewById(R.id.ivOnboarding)
        title = view.findViewById(R.id.tvTitle)
        description = view.findViewById(R.id.tvDescription)
        imageView.setImageResource(onboardingDataList[position].image)
        title.text = onboardingDataList[position].title
        description.text = onboardingDataList[position].description
        container.addView(view)
        return view
    }

}