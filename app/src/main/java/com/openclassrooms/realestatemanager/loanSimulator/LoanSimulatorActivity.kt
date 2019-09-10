package com.openclassrooms.realestatemanager.loanSimulator

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.basgeekball.awesomevalidation.utility.RegexTemplate
import com.openclassrooms.realestatemanager.R
import kotlinx.android.synthetic.main.activity_loan_simulator.*
import kotlinx.android.synthetic.main.toolbar.*

class LoanSimulatorActivity : AppCompatActivity() {

    private var price: String = ""
    private var period: String = ""
    private var contribution: String = ""
    private val mAwesomeValidation = AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT)
    private var amountResult: Int = 0
    private var rateResult: Double = 0.toDouble()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_simulator)
        configureToolbar()
        addEveryListener()
        setEveryAwesomeValidation()
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun addEveryListener() {
        loan_simulator_price.doAfterTextChanged {
            price = it.toString()
            checkContribution()
        }
        loan_simulator_period.doAfterTextChanged { period = it.toString() }
        loan_simulator_contribution.doAfterTextChanged {
            contribution = it.toString()
            checkContribution()
        }
        loan_simulator_cancel.setOnClickListener { finish() }
        loan_simulator_estimating.setOnClickListener { checkField() }
    }
    private fun checkContribution(): Boolean =
            if (price.isNotEmpty()) {
                val minContribution: Double = price.toInt() * 0.1
                if (contribution.isNotEmpty()) {
                    if (minContribution <= contribution.toDouble()) {
                        loan_simulator_error_contribution.visibility = View.GONE
                        true
                    } else {
                        loan_simulator_error_contribution.visibility = View.VISIBLE
                        false
                    }
                } else {
                    loan_simulator_error_contribution.visibility = View.GONE
                    true
                }

            } else {
                loan_simulator_error_contribution.visibility = View.GONE
                true
            }


    private fun checkField() {
        if (price.isNotEmpty() && period.isNotEmpty() && contribution.isNotEmpty() && checkContribution()) {
            estimating()
        } else {
            mAwesomeValidation.validate()
            checkContribution()
        }
    }

    private fun estimating() {
        loan_simulator_amount.text = calculatesAmount()
        loan_simulator_rate.text = calculatesRate()
        loan_simulator_monthly_payment.text = calculatesMonthlyPayment()
    }

    private fun calculatesAmount(): String {
        return if (price > contribution) {
            amountResult = price.toInt() - contribution.toInt()
            "${getString(R.string.loan_simulator_amount)} $amountResult$"
        } else {
            amountResult = 0
            "${getString(R.string.loan_simulator_amount)} $amountResult$"
        }
    }

    private fun calculatesRate(): String {
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
        return "${getString(R.string.loan_simulator_rate)} $rateResult%"
    }

    private fun calculatesMonthlyPayment(): String {
        val monthlyPaymentResult: Double? = amountResult * (1 + (rateResult/100)) / (period.toInt() * 12)
        return "${getString(R.string.loan_simulator_monthly_payment)} $monthlyPaymentResult$"
    }

    private fun setEveryAwesomeValidation() {
        mAwesomeValidation.addValidation(this, R.id.loan_simulator_price_layout, RegexTemplate.NOT_EMPTY, R.string.form_error_field_empty)
        mAwesomeValidation.addValidation(this, R.id.loan_simulator_period_layout, RegexTemplate.NOT_EMPTY, R.string.form_error_field_empty)
        mAwesomeValidation.addValidation(this, R.id.loan_simulator_contribution_layout, RegexTemplate.NOT_EMPTY, R.string.form_error_field_empty)
    }

}