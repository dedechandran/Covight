package com.covid.covidapps.ui.handling

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.covid.covidapps.R


class HandlingCaseAdapter : ListAdapter<HandlingCaseItem, HandlingCaseViewHolder>(DIFF_CALLBACK) {
    private var listener: ((String) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HandlingCaseViewHolder {
        return HandlingCaseViewHolder(
            view = LayoutInflater.from(parent.context).inflate(R.layout.handling_case_item, parent, false),
            listener = listener
        )
    }

    override fun onBindViewHolder(holder: HandlingCaseViewHolder, position: Int) {
        holder.bind(item = getItem(position))
    }

    fun setOnItemClickListener(listener: (String) -> Unit){
        this.listener = listener
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HandlingCaseItem>() {
            override fun areItemsTheSame(
                oldItem: HandlingCaseItem,
                newItem: HandlingCaseItem
            ): Boolean {
                return false
            }

            override fun areContentsTheSame(
                oldItem: HandlingCaseItem,
                newItem: HandlingCaseItem
            ): Boolean {
                return false
            }

        }
    }
}