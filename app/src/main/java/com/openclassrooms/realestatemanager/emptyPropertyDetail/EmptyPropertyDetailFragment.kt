package com.openclassrooms.realestatemanager.emptyPropertyDetail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.openclassrooms.realestatemanager.R

class EmptyPropertyDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_empty_property_detail, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                EmptyPropertyDetailFragment().apply { }
    }
}
