package com.rono.toTrade.ui.courseDetails.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rono.toTrade.R
import com.rono.toTrade.dataStructures.lectures.Lecture


class LecturesAdapter :
    RecyclerView.Adapter<LecturesAdapter.CustomVH>() {
    private var lectures: List<Lecture?>? = null

    class CustomVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvLectureName: TextView

        init {
            tvLectureName = itemView.findViewById(R.id.tv_lecture_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lecture, parent, false)
        return CustomVH(view)
    }

    override fun onBindViewHolder(holder: CustomVH, position: Int) {
        val context = holder.itemView.context
        val current = lectures?.get(position)

        holder.apply {
            tvLectureName.text = current?.title.toString()
        }
    }

    override fun getItemCount(): Int = lectures?.size ?: 0

    fun setData(list: List<Lecture?>?) {
        lectures = list
    }
}