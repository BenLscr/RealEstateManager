package com.openclassrooms.realestatemanager.propertyDetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.openclassrooms.realestatemanager.R

class PropertyDetailFragment : Fragment() {

    companion object {
        fun newInstance() = PropertyDetailFragment()
    }

    private lateinit var viewModel: PropertyDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.property_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PropertyDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
