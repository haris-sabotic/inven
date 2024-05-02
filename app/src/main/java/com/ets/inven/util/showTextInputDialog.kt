package com.ets.inven.util

import android.app.AlertDialog
import android.content.Context
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.ets.inven.R

fun showTextInputDialog(context: Context, title: String, inputType: Int, lineCount: Int? = null, onSubmit: (String) -> Unit) {
    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
    builder.setTitle(title)

    val layout = LinearLayout(context)

    val input = EditText(context)
    if (lineCount != null) {
        input.setLines(lineCount)
        input.inputType = EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE or inputType
    } else {
        input.inputType = inputType
    }

    val lp = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    lp.setMargins(20F.toDp(context), 0, 20F.toDp(context), 0)
    input.layoutParams = lp

    layout.addView(input)

    builder.setView(layout)


    builder.setPositiveButton("OK") { _, _ ->
        val text = input.text.toString()

        onSubmit(text)
    }
    builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

    val dialog = builder.create()

    dialog.window?.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.dialog_background))

    dialog.show()
}