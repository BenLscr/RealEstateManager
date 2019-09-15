package com.openclassrooms.realestatemanager.loanSimulator

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.Utils

class LoanSimulatorViewModel : ViewModel() {

    var price: String = ""
    var period: String = ""
    var contribution: String = ""
    var amountResult: Long = 0
    var rateResult: Double = 0.toDouble()
    var checked: Boolean = false

    fun calculatesAmount(context: Context): LiveData<String> {
        amountResult = if (price > contribution) {
            price.toLong() - contribution.toLong()
        } else {
            0
        }
        val amountResultString = Utils.fromPriceToString(amountResult)
        return MutableLiveData<String>("${context.getString(R.string.loan_simulator_amount)} $amountResultString")
    }

    fun calculatesRate(context: Context): LiveData<String> {
        rateResult = when (contribution.toDouble()/price.toDouble()) {
            in 0.1..0.2 -> 1.95
            in 0.2..0.3 -> 1.85
            in 0.3..0.4 -> 1.75
            in 0.4..0.5 -> 1.65
            in 0.5..0.6 -> 1.55
            in 0.6..0.7 -> 1.45
            in 0.7..0.8 -> 1.35
            in 0.8..0.9 -> 1.25
            in 0.9..0.99 -> 1.15
            else -> 0.0
        }
        return MutableLiveData<String>("${context.getString(R.string.loan_simulator_rate)} $rateResult%")
    }

    fun calculatesMonthlyPayment(context: Context): LiveData<String> {
        val monthlyPaymentResult: Double = amountResult * (1 + (rateResult/100)) / (period.toInt() * 12)
        val monthlyPaymentResultString = Utils.fromPriceToString(monthlyPaymentResult.toLong())
        return MutableLiveData<String>("${context.getString(R.string.loan_simulator_monthly_payment)} $monthlyPaymentResultString$")
    }

}