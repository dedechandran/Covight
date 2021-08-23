package com.covid.covidapps.ui.handling

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.covid.covidapps.databinding.HandlingCaseItemBinding

class HandlingCaseViewHolder(view: View, private val listener: ((String) -> Unit)?) :
    RecyclerView.ViewHolder(view) {

    fun bind(item: HandlingCaseItem) {
        val binding = HandlingCaseItemBinding.bind(itemView)
        with(binding) {
            tvHandlingCase.text = item.action
            ivHandlingCase.setImageResource(item.image)
            divHandlingCase.setOnClickListener {
                listener?.invoke(item.status.name)
            }
        }
    }
}