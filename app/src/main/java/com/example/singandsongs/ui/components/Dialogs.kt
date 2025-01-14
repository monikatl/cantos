package com.example.singandsongs.ui.components

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

open class YesNoDialog(
  val context: Context?,
  val title: String,
  val message: String,
  val positiveAction: (DialogInterface?) -> Unit
)

fun buildSimpleYesNoDialog(dialogContent: YesNoDialog): AlertDialog.Builder = with(dialogContent) {
  AlertDialog.Builder(context)
    .setMessage(message)
    .setTitle(title)
    .setPositiveButton("TAK") { dialog, _ -> positiveAction.invoke(dialog) }
    .setNegativeButton("NIE") { dialog, _ -> dialog.dismiss() }

}

fun createAndShowSimpleYesNoDialog(dialogContent: YesNoDialog) {
  buildSimpleYesNoDialog(dialogContent)
    .create()
    .show()
}

fun createAndShowYesNoNeutralDialog(dialogContent: YesNoDialog, neutralButtonText: String, neutralAction: (DialogInterface?) -> Unit) {
  buildSimpleYesNoDialog(dialogContent)
    .setNeutralButton(neutralButtonText) { dialog,_ -> neutralAction.invoke(dialog) }
    .create()
    .show()
}
