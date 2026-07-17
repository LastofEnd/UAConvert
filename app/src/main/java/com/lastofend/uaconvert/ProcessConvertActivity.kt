package com.lastofend.uaconvert

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class ProcessConvertActivity : Activity() {

    private val EN = "qwertyuiop[]asdfghjkl;'zxcvbnm,./"
    private val UA = "йцукенгшщзхїфівапролджєячсмитьбю."

    private val mapEnUa: Map<Char, Char>
    private val mapUaEn: Map<Char, Char>

    init {
        val enAll = EN + EN.uppercase()
        val uaAll = UA + UA.uppercase()
        mapEnUa = enAll.zip(uaAll).toMap()
        mapUaEn = uaAll.zip(enAll).toMap()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selected = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)?.toString().orEmpty()
        val readonly = intent.getBooleanExtra(Intent.EXTRA_PROCESS_TEXT_READONLY, false)

        if (readonly) {
            setResult(RESULT_CANCELED)
            finish()
            return
        }

        val outText = convertUaEn(selected)
        val out = Intent().putExtra(Intent.EXTRA_PROCESS_TEXT, outText)
        setResult(RESULT_OK, out)
        finish()
    }

    private fun convertUaEn(text: String): String {
        if (text.isEmpty()) return text
        if (text == "-") return "—"

        fun score(mp: Map<Char, Char>): Int {
            val conv = text.map { mp[it] ?: it }.joinToString("")
            return text.zip(conv).count { (a, b) -> a != b }
        }

        val mp = if (score(mapEnUa) >= score(mapUaEn)) mapEnUa else mapUaEn
        return text.map { mp[it] ?: it }.joinToString("")
    }
}