package com.lastofend.uaconvert

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class ProcessAcuteActivity : Activity() {

    private val combiningAcute = '\u0301'

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selected = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)?.toString().orEmpty()
        val readonly = intent.getBooleanExtra(Intent.EXTRA_PROCESS_TEXT_READONLY, false)

        if (readonly) {
            setResult(RESULT_CANCELED)
            finish()
            return
        }

        val outText = toggleAcute(selected)
        val out = Intent().putExtra(Intent.EXTRA_PROCESS_TEXT, outText)
        setResult(RESULT_OK, out)
        finish()
    }

    private fun toggleAcute(text: String): String {
        return when {
            text.length == 1 -> text + combiningAcute
            text.length == 2 && text[1] == combiningAcute -> text[0].toString()
            else -> text
        }
    }
}