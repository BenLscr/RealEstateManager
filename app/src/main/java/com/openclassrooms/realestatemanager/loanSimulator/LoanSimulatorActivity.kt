package com.openclassrooms.realestatemanager.loanSimulator

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.basgeekball.awesomevalidation.utility.RegexTemplate
import com.openclassrooms.realestatemanager.R
import kotlinx.android.synthetic.main.activity_loan_simulator.*
import kotlinx.android.synthetic.main.toolbar.*

class LoanSimulatorActivity : AppCompatActivity() {

    private val loanSimulatorViewModel: LoanSimulatorViewModel by lazy { ViewModelProviders.of(this).get(LoanSimulatorViewModel::class.java) }
    private val mAwesomeValidation = AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_simulator)
        configureToolbar()
        addEveryListener()
        setEveryAwesomeValidation()
        if (loanSimulatorViewModel.checked) {
            checkField()
        }
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun addEveryListener() {
        loan_simulator_price.doAfterTextChanged {
            loanSimulatorViewModel.price = it.toString()
            checkContribution()
        }
        loan_simulator_period.doAfterTextChanged { loanSimulatorViewModel.period = it.toString() }
        loan_simulator_contribution.doAfterTextChanged {
            loanSimulatorViewModel.contribution = it.toString()
            checkContribution()
        }
        loan_simulator_cancel.setOnClickListener { finish() }
        loan_simulator_estimating.setOnClickListener { checkField() }
    }

    private fun checkContribution(): Boolean =
            if (loanSimulatorViewModel.price.isNotEmpty()) {
                val minContribution: Double = loanSimulatorViewModel.price.toInt() * 0.1
                if (loanSimulatorViewModel.contribution.isNotEmpty()) {
                    if (minContribution <= loanSimulatorViewModel.contribution.toDouble()) {
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
        if (loanSimulatorViewModel.price.isNotEmpty()
                && loanSimulatorViewModel.period.isNotEmpty()
                && loanSimulatorViewModel.contribution.isNotEmpty()
                && checkContribution()) {
            loanSimulatorViewModel.checked = true
            estimating()
        } else {
            mAwesomeValidation.validate()
            checkContribution()
        }
    }

    private fun estimating() {
        loanSimulatorViewModel.calculatesAmount(applicationContext).observe(this, Observer { loan_simulator_amount.text = it })
        loanSimulatorViewModel.calculatesRate(applicationContext).observe(this, Observer { loan_simulator_rate.text = it })
        loanSimulatorViewModel.calculatesMonthlyPayment(applicationContext).observe(this, Observer { loan_simulator_monthly_payment.text = it })
    }

    private fun setEveryAwesomeValidation() {
        mAwesomeValidation.addValidation(this, R.id.loan_simulator_price_layout, RegexTemplate.NOT_EMPTY, R.string.form_error_field_empty)
        mAwesomeValidation.addValidation(this, R.id.loan_simulator_period_layout, RegexTemplate.NOT_EMPTY, R.string.form_error_field_empty)
        mAwesomeValidation.addValidation(this, R.id.loan_simulator_contribution_layout, RegexTemplate.NOT_EMPTY, R.string.form_error_field_empty)
    }

}