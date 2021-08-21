package com.covid.covidapps.ui.cardlist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.covid.covidapps.databinding.PatientDetailsItemBinding

class CardListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(item: CardItem) {
        when (item) {
            is CardItem.PatientDetails -> bindPatientItem(item)
        }
    }

    private fun bindPatientItem(item: CardItem.PatientDetails) {
        val binding = PatientDetailsItemBinding.bind(itemView)
        with(binding) {
            ivPatient.setImageResource(item.image)
            tvPatientName.text = item.name
            tvPatientRoom.text = item.roomName
            tvPatientStatus.text = item.status
            tvFsrValue.text = ": ${item.patientDetails?.FSR.toString()}"
            tvHeartRateValue.text = ": ${item.patientDetails?.heartRate.toString()}"
            tvSpO2Value.text = ": ${item.patientDetails?.spO2.toString()}"
            tvTemperatureValue.text = ": ${item.patientDetails?.temperature.toString()}"
        }
    }
}