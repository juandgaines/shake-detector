package com.example.shakedetector.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.example.shakedetector.R

class DialogAccelerometer : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.no_accelerometer_message)
                .setPositiveButton(R.string.ok) { dialog, id ->
                    dialog.dismiss()
                    (activity as FragmentActivity).finish()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}