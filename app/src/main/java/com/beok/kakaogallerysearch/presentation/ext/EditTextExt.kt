package com.beok.kakaogallerysearch.presentation.ext

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

fun EditText.textChanges(): Flow<CharSequence?> = callbackFlow {
    val listener = doOnTextChanged { text, _, _, _ ->
        trySend(text)
    }
    awaitClose { removeTextChangedListener(listener) }
}.onStart { emit(text) }
