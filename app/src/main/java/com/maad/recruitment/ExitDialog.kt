package com.maad.recruitment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ExitDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)
        builder
            .setIcon(R.drawable.ic_close)
            .setTitle("Exit?")
            .setMessage("Are you sure you want to exit?")
            .setPositiveButton("ok") { _, _ -> activity?.finish() }
            .setNegativeButton("cancel") { dialog, _ -> dialog.dismiss() }

        return builder.create()
    }

}