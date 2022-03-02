package com.beok.kakaogallerysearch.presentation.ext

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

// 뭔가 사용성이 그냥 String으로 내보내도 될것 같은 느낌이네요.
fun EditText.textChanges(): Flow<CharSequence?> = callbackFlow {
    val listener = doOnTextChanged { text, _, _, _ ->
        trySend(text)
    }
    awaitClose { removeTextChangedListener(listener) }
}.onStart { emit(text) }
