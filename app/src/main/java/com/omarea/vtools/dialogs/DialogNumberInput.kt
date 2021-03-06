package com.omarea.vtools.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.omarea.common.ui.DialogHelper
import com.omarea.vtools.R

class DialogNumberInput(private val context: Context) {
    interface DialogNumberInputRequest {
        var min: Int
        var max: Int
        var default: Int

        fun onApply(value: Int)
    }

    fun showDialog(dialogRequest: DialogNumberInputRequest) {
        var alertDialog: DialogHelper.DialogWrap? = null
        val dialog = LayoutInflater.from(context).inflate(R.layout.dialog_number_input, null)
        val value = dialog.findViewById<TextView>(R.id.number_input_value)
        var current = dialogRequest.default

        dialog.findViewById<ImageButton>(R.id.number_input_minus).setOnClickListener {
            if (current > dialogRequest.min) {
                current -= 1
            }
            value.setText(current.toString())
        }
        dialog.findViewById<ImageButton>(R.id.number_input_plus).setOnClickListener {
            if (current < dialogRequest.max) {
                current += 1
            }
            value.setText(current.toString())
        }

        dialog.findViewById<Button>(R.id.number_input_applay).setOnClickListener {
            val text = value.text.toString()
            if (text != current.toString()) {
                if (Regex("^[-0-9]{1,10}").matches(text)) {
                    try {
                        val intValue = value.text.toString().toInt()
                        if (intValue >= dialogRequest.min && intValue <= dialogRequest.max) {
                            current = intValue
                        }
                    } catch (ex: Exception) {
                    }
                }
            }
            if (text == current.toString()) {
                dialogRequest.onApply(current)
                alertDialog?.dismiss()
            } else {
                value.text = current.toString()
            }
        }

        dialog.findViewById<Button>(R.id.number_input_cancel).setOnClickListener {
            alertDialog?.dismiss()
        }
        dialog.findViewById<TextView>(R.id.number_input_help).setText(dialogRequest.min.toString() + " ~ " + dialogRequest.max.toString())

        value.setOnEditorActionListener { v, actionId, event ->
            if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
                if (Regex("^[-0-9]{1,10}").matches(value.text.toString())) {
                    try {
                        val intValue = value.text.toString().toInt()
                        if (intValue >= dialogRequest.min && intValue <= dialogRequest.max) {
                            current = intValue
                            return@setOnEditorActionListener true
                        }
                    } catch (ex: Exception) {
                    }
                }
                value.text = current.toString()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        value.setText(dialogRequest.default.toString())
        alertDialog = DialogHelper.animDialog(AlertDialog.Builder(context).setView(dialog))
    }
}