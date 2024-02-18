package com.example.singandsongs.ui.notifications

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.singandsongs.R
import com.example.singandsongs.databinding.DialogAddPlaylistBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class AddPlayListDialogFragment(private val action: (LocalDate, String, Boolean) -> Unit)  : DialogFragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    var date: LocalDate = LocalDate.now()
    var name: String = "Nowa lista"
    private var isDefault: Boolean = true
    private lateinit var binding: DialogAddPlaylistBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =  activity?.let {
            binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.dialog_add_playlist, null, false);
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
                .setPositiveButton("dodaj"){ _, _ -> clickPositiveButton() }
                .setNegativeButton("Anuluj") { dialog, _ -> dialog.dismiss() }
            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
        return dialog
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.date.setEndIconOnClickListener {
            showDatePickerDialog()
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun clickPositiveButton() {
        dialog?.let{
            name = binding.inputName.text.toString()
            isDefault = binding.isDefault.isChecked
            action.invoke(date, name, isDefault)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, day ->

            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, day)

            val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate.time)
            binding.inputDate.setText(formattedDate)
            date = LocalDate.of(year, month, day)
        }, year, month, day)

        datePickerDialog.show()
    }
}