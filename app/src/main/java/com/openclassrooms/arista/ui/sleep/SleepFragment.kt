package com.openclassrooms.arista.ui.sleep

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.arista.R
import com.openclassrooms.arista.databinding.FragmentSleepBinding
import com.openclassrooms.arista.domain.model.Sleep
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset

@AndroidEntryPoint
class SleepFragment : Fragment() {

    private var _binding: FragmentSleepBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SleepViewModel by viewModels()
    private val sleepAdapter = SleepAdapter(emptyList()) { sleep ->
        showDeleteConfirmationDialog(sleep)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSleepBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        binding.sleepRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.sleepRecyclerview.adapter = sleepAdapter
        viewModel.fetchSleeps()
        setupFab()
    }

    private fun setupFab() {
        binding.addSleep.setOnClickListener { showAddSleepDialog() }
    }

    private fun showAddSleepDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_sleep, null)

        val dateEditText: EditText = dialogView.findViewById(R.id.dateEditText)
        val timeEditText: EditText = dialogView.findViewById(R.id.timeEditText)
        val sleepDurationTimeEditTextDialog: EditText = dialogView.findViewById(R.id.sleepDurationTimeEditTextDialog)
        val qualityEditTextDialog: EditText = dialogView.findViewById(R.id.qualityEditTextDialog)

        val datePickerIcon: ImageView = dialogView.findViewById(R.id.datePickerIcon)
        val timePickerIcon: ImageView = dialogView.findViewById(R.id.timePickerIcon)

        datePickerIcon.setOnClickListener {
            showDatePicker(dateEditText)
        }

        timePickerIcon.setOnClickListener {
            showTimePicker(timeEditText)
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Add Sleep")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                // Get data from input fields
                val startTime = getStartTimeFromInputs(dateEditText.text.toString(), timeEditText.text.toString())
                val duration = sleepDurationTimeEditTextDialog.text.toString().toIntOrNull() ?: 0
                val quality = qualityEditTextDialog.text.toString().toIntOrNull() ?: 0

                // Save the sleep data
                if (duration > 0 && quality in 1..10) {
                    viewModel.addSleep(startTime, duration, quality)
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    private fun showDatePicker(dateEditText: EditText) {
        val currentDate = LocalDateTime.now()
        val year = currentDate.year
        val month = currentDate.monthValue
        val dayOfMonth = currentDate.dayOfMonth

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                dateEditText.setText("$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear")
            },
            year, month - 1, dayOfMonth
        )

        datePickerDialog.show()
    }

    private fun showTimePicker(timeEditText: EditText) {
        val currentTime = LocalDateTime.now()
        val hour = currentTime.hour
        val minute = currentTime.minute

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->
                timeEditText.setText(String.format("%02d:%02d", selectedHour, selectedMinute))
            },
            hour, minute, true
        )

        timePickerDialog.show()
    }

    private fun getStartTimeFromInputs(date: String, time: String): Long {
        val (day, month, year) = date.split("/").map { it.toInt() }
        val (hour, minute) = time.split(":").map { it.toInt() }
        val localDateTime = LocalDateTime.of(year, month, day, hour, minute)
        return localDateTime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli()
    }

    private fun showDeleteConfirmationDialog(sleep: Sleep) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Sleep")
            .setMessage("Are you sure you want to delete this sleep record?")
            .setPositiveButton("Yes") { _, _ ->
                // Call the ViewModel to delete the sleep
                viewModel.deleteSleep(sleep)
            }
            .setNegativeButton("No", null)
            .show()
    }
    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sleeps.collect { sleeps ->
                sleepAdapter.updateData(sleeps)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
