package com.openclassrooms.realestatemanager.propertyList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.models.Address
import com.openclassrooms.realestatemanager.models.AddressHandled
import com.openclassrooms.realestatemanager.models.Property
import com.openclassrooms.realestatemanager.models.PropertyHandled
import com.openclassrooms.realestatemanager.propertyList.injections.Injection

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [PropertyListFragment.OnListFragmentInteractionListener] interface.
 */
class PropertyListFragment : Fragment() {

    //private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    private var propertyViewModel : PropertyViewModel? = null
    private var properties: MutableList<PropertyHandled> = mutableListOf()
    private var addresses: MutableList<AddressHandled> = mutableListOf()
    private var adapter: PropertyListRecyclerViewAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_property_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            view.layoutManager = LinearLayoutManager(context)
            this.adapter = PropertyListRecyclerViewAdapter(properties, addresses, listener)
            view.adapter = this.adapter
            /*with(view) {
                /*layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }*/
                layoutManager = LinearLayoutManager(context)
                this.adapter = PropertyListRecyclerViewAdapter(properties, addresses, DummyContent.ITEMS, listener)
            }*/
        }
        this.configureViewModel()
        this.getCurrentProperty()
        this.getCurrentAddress()
        return view
    }

    private fun configureViewModel() {
        val mViewModelFactory = Injection.provideViewModelFactory(requireContext())
        this.propertyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyViewModel::class.java)
        this.propertyViewModel!!.init()
    }

    private fun getCurrentProperty() {
        this.propertyViewModel!!.getProperties().observe(this, Observer {  updateDataProperties(it) })
    }

    private fun updateDataProperties(properties: List<PropertyHandled>) {
        this.properties.clear()
        this.properties.addAll(properties)
        this.notifyRecyclerView()
    }

    private fun getCurrentAddress() {
        this.propertyViewModel!!.getAddresses().observe(this, Observer { updateDataAddresses(it) })
    }

    private fun updateDataAddresses(addresses: List<AddressHandled>) {
        this.addresses.clear()
        this.addresses.addAll(addresses)
        this.notifyRecyclerView()
    }

    private fun notifyRecyclerView() {
        if (properties.isNotEmpty() && addresses.isNotEmpty()) {
            this.adapter!!.notifyDataSetChanged()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(address: AddressHandled, property: PropertyHandled)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                PropertyListFragment().apply { }
    }
}
