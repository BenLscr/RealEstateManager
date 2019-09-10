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
                //if contribution is not empty
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
        //TODO : show result
    }

    private fun setEveryAwesomeValidation() {
        mAwesomeValidation.addValidation(this, R.id.loan_simulator_price_layout, RegexTemplate.NOT_EMPTY, R.string.form_error_field_empty)
        mAwesomeValidation.addValidation(this, R.id.loan_simulator_period_layout, RegexTemplate.NOT_EMPTY, R.string.form_error_field_empty)
        mAwesomeValidation.addValidation(this, R.id.loan_simulator_contribution_layout, RegexTemplate.NOT_EMPTY, R.string.form_error_field_empty)
    }

}