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
import com.openclassrooms.realestatemanager.propertyList.models.IllustrationModelProcessed


const val ARG_PROPERTY_LIST_PROPERTIES_ID = "ARG_PROPERTY_LIST_PROPERTIES_ID"

class PropertyListFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(propertiesId: IntArray? = null) =
                PropertyListFragment().apply {
                    if (propertiesId != null) {
                        arguments = Bundle().apply {
                            putIntArray(ARG_PROPERTY_LIST_PROPERTIES_ID, propertiesId)
                        }
                    }
                }
    }

    private val propertiesId = mutableListOf<Int>()
    private var listener: OnListFragmentInteractionListener? = null
    private val propertyListViewModel : PropertyListViewModel by lazy { ViewModelProviders.of(this, Injection.provideViewModelFactory(requireContext())).get(PropertyListViewModel::class.java) }
    private val propertyListAdapter: PropertyListRecyclerViewAdapter = PropertyListRecyclerViewAdapter()
    private val illustrations = mutableListOf<IllustrationModelProcessed>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.getIntArray(ARG_PROPERTY_LIST_PROPERTIES_ID) != null) {
                propertiesId.addAll(it.getIntArray(ARG_PROPERTY_LIST_PROPERTIES_ID).toMutableList())
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_property_list, container, false)

        // Set the adapter
        (view as RecyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = propertyListAdapter
        }

        getProperties()
        return view
    }

    private fun getProperties() =
            if (propertiesId.isNotEmpty()) {
                propertyListViewModel.getProperties(propertiesId).observe(this, Observer {
                    propertyListAdapter.receivePropertiesDataAndListener(it, listener)
                    it.map { property -> getPropertyIllustration(property.propertyId) }
                })
            } else {
                propertyListViewModel.allProperties.observe(this, Observer {
                    propertyListAdapter.receivePropertiesDataAndListener(it, listener)
                    it.map { property -> getPropertyIllustration(property.propertyId) }
                })
            }


    private fun getPropertyIllustration(propertyId: Int) =
            propertyListViewModel.getPropertyIllustration(propertyId, requireContext()).observe(this, Observer {
                illustrations.add(it)
                propertyListAdapter.receiveIllustrations(illustrations)
            })

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
