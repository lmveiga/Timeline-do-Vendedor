package com.gmail.lucasmveigabr.timelinedovendedor.feature.timeline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.lucasmveigabr.timelinedovendedor.R
import kotlinx.android.synthetic.main.timeline_fragment.*

class TimelineFragment : Fragment() {

    companion object {
        fun newInstance() = TimelineFragment()
    }

    private lateinit var viewModel: TimelineViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.timeline_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TimelineViewModel::class.java)
        val adapter = TimelineAdapter(requireContext())
        timelineRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        timelineRecyclerView.adapter = adapter
        adapter.list = viewModel.fakeData()
    }

}
