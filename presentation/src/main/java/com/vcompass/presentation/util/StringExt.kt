package com.vcompass.presentation.util

import java.text.Normalizer


fun String.removeAccent(): String {
    val normalized = Normalizer.normalize(this, Normalizer.Form.NFD)
    return normalized.replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
}