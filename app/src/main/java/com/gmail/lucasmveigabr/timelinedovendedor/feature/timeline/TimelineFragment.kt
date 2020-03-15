package com.gmail.lucasmveigabr.timelinedovendedor.feature.timeline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.lucasmveigabr.timelinedovendedor.R
import com.gmail.lucasmveigabr.timelinedovendedor.core.NavigationEvent
import com.gmail.lucasmveigabr.timelinedovendedor.core.NavigationViewModel
import kotlinx.android.synthetic.main.timeline_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TimelineFragment : Fragment() {

    companion object {
        fun newInstance() = TimelineFragment()
    }

    private val viewModel: TimelineViewModel by viewModel()
    private val navigationViewModel: NavigationViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.timeline_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = TimelineAdapter(requireContext())
        val countAdapter = TimelineCountAdapter(requireContext())
        timelineRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        timelineRecyclerView.adapter = adapter
        timelineCountRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        timelineCountRecyclerView.adapter = countAdapter
        viewModel.timelineData.observe(viewLifecycleOwner, Observer {
            adapter.list = it
        })
        viewModel.timelineCountData.observe(viewLifecycleOwner, Observer {
            countAdapter.list = it
        })
        fab.setOnClickListener {
            navigationViewModel.setNavigation(NavigationEvent.AddTaskNavigation)
        }
    }

}
