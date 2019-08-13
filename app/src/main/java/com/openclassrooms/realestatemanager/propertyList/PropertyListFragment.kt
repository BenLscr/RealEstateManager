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
import com.openclassrooms.realestatemanager.propertyList.injections.Injection

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [PropertyListFragment.OnListFragmentInteractionListener] interface.
 */
class PropertyListFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() =
                PropertyListFragment().apply { }
    }

    private var listener: OnListFragmentInteractionListener? = null
    private val propertyListViewModel : PropertyListViewModel by lazy { ViewModelProviders.of(this, Injection.provideViewModelFactory(requireContext())).get(PropertyListViewModel::class.java) }
    private val propertyListAdapter: PropertyListRecyclerViewAdapter = PropertyListRecyclerViewAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_property_list, container, false)

        // Set the adapter
        (view as RecyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = propertyListAdapter
        }

        getProperties()
        getPropertiesPhotos()
        return view
    }

    private fun getProperties() = propertyListViewModel.properties.observe(this, Observer { propertyListAdapter.receivePropertiesDataAndListener(it, listener) })


    private fun getPropertiesPhotos() = propertyListViewModel.illustrationsPropertiesPhotos.observe(this, Observer { propertyListAdapter.receivePropertiesPhotos(it, requireContext()) })

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
        fun onListFragmentInteraction(propertyId: Int)
    }
}
