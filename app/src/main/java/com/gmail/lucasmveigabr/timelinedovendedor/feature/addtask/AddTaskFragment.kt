package com.gmail.lucasmveigabr.timelinedovendedor.feature.addtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.lucasmveigabr.timelinedovendedor.R
import com.gmail.lucasmveigabr.timelinedovendedor.core.NavigationEvent
import com.gmail.lucasmveigabr.timelinedovendedor.core.NavigationViewModel
import com.gmail.lucasmveigabr.timelinedovendedor.feature.addtask.AddTaskError.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.add_task_fragment.*
import kotlinx.android.synthetic.main.date_time_picker_dialog.view.*
import java.text.SimpleDateFormat
import java.util.*

class AddTaskFragment : Fragment() {

    companion object {
        fun newInstance() = AddTaskFragment()
    }

    private lateinit var viewModel: AddTaskViewModel
    private lateinit var navigationViewModel: NavigationViewModel
    private lateinit var adapter: TaskTypeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.add_task_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this, SavedStateViewModelFactory
                (requireActivity().application, this)
        )[AddTaskViewModel::class.java]
        navigationViewModel = ViewModelProvider(requireActivity())[NavigationViewModel::class.java]
        viewModel.taskDate.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            taskDateTextView.text =
                SimpleDateFormat("dd/MM/yyyy, HH:mm", Locale("pt", "BR")).format(it)
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                EMPTY_DATE -> showSnackbar(R.string.empty_date)
                EMPTY_CUSTOMER -> showSnackbar(R.string.empty_customer)
                EMPTY_DESCRIPTION -> showSnackbar(R.string.empty_description)
                FIREBASE_ERROR -> showSnackbar(R.string.save_error)
                else -> throw RuntimeException("Invalid Option")
            }
        })
        viewModel.insertedAction.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            navigationViewModel.setNavigation(NavigationEvent.TimelineNavigation)
        })
        if (savedInstanceState != null) {
            adapter =
                TaskTypeAdapter(requireContext(), savedInstanceState.getInt("state_selected_type"))
        } else {
            adapter = TaskTypeAdapter(requireContext())
        }
        taskTypeRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        taskTypeRecyclerView.adapter = adapter
        selectDateButton.setOnClickListener { showDateDialog() }
        createTaskButton.setOnClickListener {
            viewModel.createTaskButtonClick(
                adapter.getSelected().type, customerEditText.text.toString(),
                descriptionEditText.text.toString()
            )
        }
    }

    private fun showSnackbar(resId: Int) {
        val view = view ?: return
        Snackbar.make(view, resId, Snackbar.LENGTH_LONG).show()
    }

    private fun showDateDialog() {
        val view = View.inflate(requireContext(), R.layout.date_time_picker_dialog, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(view)
            .create()
        view.timePicker.setIs24HourView(true)
        view.confirmButton.setOnClickListener {
            val calendar = GregorianCalendar(
                view.datePicker.year,
                view.datePicker.month,
                view.datePicker.dayOfMonth,
                view.timePicker.hour,
                view.timePicker.minute
            )
            viewModel.setDate(Date(calendar.timeInMillis))
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("state_selected_type", adapter.getSelectedIndex())
    }

}