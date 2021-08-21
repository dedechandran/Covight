package com.covid.covidapps.ui.cardlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.covid.covidapps.R

class CardListAdapter : ListAdapter<CardItem, CardListViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardListViewHolder {
        val layout = when (viewType) {
            PATIENT_ITEM_TYPE -> R.layout.patient_details_item
            PATIENT_SUMMARY_ITEM_TYPE -> R.layout.patient_summary_item
            NEWS_ITEM_TYPE -> R.layout.news_item
            else -> throw error("")
        }
        return CardListViewHolder(
            LayoutInflater.from(parent.context).inflate(layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CardListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CardItem.PatientDetails -> PATIENT_ITEM_TYPE
            is CardItem.News -> NEWS_ITEM_TYPE
            is CardItem.PatientSummary -> PATIENT_SUMMARY_ITEM_TYPE
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CardItem>() {
            override fun areItemsTheSame(oldItem: CardItem, newItem: CardItem): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: CardItem, newItem: CardItem): Boolean {
                return false
            }

        }

        private const val PATIENT_ITEM_TYPE = 0
        private const val NEWS_ITEM_TYPE = 1
        private const val PATIENT_SUMMARY_ITEM_TYPE = 2
    }
}