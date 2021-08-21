package com.covid.covidapps.ui.cardlist

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.covid.covidapps.R
import com.covid.covidapps.databinding.CardListViewBinding

class CardListView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr, defStyleRes) {

    private val cardlistAdapter by lazy {
        CardListAdapter()
    }

    private var binding: CardListViewBinding

    init {
        val view = View.inflate(context, R.layout.card_list_view, this)
        binding = CardListViewBinding.bind(view)
    }

    init {
        binding.rvCard.apply {
            adapter = cardlistAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }


    fun setItems(items: List<CardItem>){
        cardlistAdapter.submitList(items)
    }

}