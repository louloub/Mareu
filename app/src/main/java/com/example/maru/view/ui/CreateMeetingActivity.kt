package com.example.maru.view.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.maru.R
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_create_meeting.*

class CreateMeetingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_meeting)
        horizontal_scroll_view.background = create_meeting_teit_listOfParticipant.background
        create_meeting_teit_listOfParticipant.background = null

        create_meeting_teit_listOfParticipant.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                val trimmed = s.toString().trim { it <= ' ' }
                if (trimmed.length > 1 && trimmed.endsWith(",")) {
                    val chip = Chip(this@CreateMeetingActivity)
                    chip.text = trimmed.substring(0, trimmed.length - 1)
                    chip.isCloseIconVisible = true
                    chip.setOnCloseIconClickListener {
                        chipGroup.removeView(chip)
                    }
                    chipGroup.addView(chip)
                    s?.clear()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        create_meeting_teit_listOfParticipant.setOnKeyListener { _, _, event ->
            if (event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_DEL) {
                if (create_meeting_teit_listOfParticipant.length() == 0 && chipGroup.childCount > 0) {
                    val chip = chipGroup.getChildAt(chipGroup.childCount - 1) as Chip
                    chipGroup.removeView(chip)
                }
            }
            false
        }
    }
}
