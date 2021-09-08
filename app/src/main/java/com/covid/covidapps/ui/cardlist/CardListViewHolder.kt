package com.covid.covidapps.ui.cardlist

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.covid.covidapps.databinding.PatientDetailsItemBinding
import com.covid.covidapps.databinding.PatientSummaryItemBinding
import com.covid.covidapps.utils.PatientStatus

class CardListViewHolder(view: View, private val listener: ((String) -> Unit)? = null) :
    RecyclerView.ViewHolder(view) {

    fun bind(item: CardItem) {
        when (item) {
            is CardItem.PatientDetails -> bindPatientItem(item)
            is CardItem.PatientSummary -> bindPatientSummary(item)
        }
    }

    private fun bindPatientItem(item: CardItem.PatientDetails) {
        val binding = PatientDetailsItemBinding.bind(itemView)
        with(binding) {
            ivPatient.setImageResource(getPatientStatusIcon(patientStatus = item.status))
            tvPatientName.text = item.name
            tvPatientRoom.text = item.roomName
            tvPatientStatus.apply {
                setTextColor(Color.CYAN)
                text = item.status.name
            }
            tvFsrValue.text = ": ${item.patientDetails?.FSR.toString()}"
            tvHeartRateValue.text = ": ${item.patientDetails?.heartRate.toString()}"
            tvSpO2Value.text = ": ${item.patientDetails?.spO2.toString()}"
            tvTemperatureValue.text = ": ${item.patientDetails?.temperature.toString()}"
            cvPatientDetails.setOnClickListener {
                listener?.invoke(item.status.name)
            }
        }
    }

    private fun bindPatientSummary(item: CardItem.PatientSummary) {
        val binding = PatientSummaryItemBinding.bind(itemView)
        with(binding) {
            ivPatient.setImageResource(getPatientStatusIcon(patientStatus = item.status))
            tvPatientStatus.apply {
                setTextColor(Color.CYAN)
                text = item.status.status
            }
            tvPatientTotal.text = item.total
            cvPatientSummary.setOnClickListener {
                listener?.invoke(item.status.name)
            }
        }
    }

    private fun getPatientStatusIcon(patientStatus: PatientStatus): Int {
        return when (patientStatus) {
            PatientStatus.SEDANG -> PatientStatus.SEDANG.image
            PatientStatus.BERAT -> PatientStatus.BERAT.image
            PatientStatus.KRITIS -> PatientStatus.KRITIS.image
            PatientStatus.RINGAN -> PatientStatus.RINGAN.image
            PatientStatus.TANPA_GEJALA -> PatientStatus.TANPA_GEJALA.image
        }
    }
}