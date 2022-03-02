package com.beok.kakaogallerysearch.presentation.ext

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// 이것도 왜 쓰는지 잘 정리해가면 좋아보여요. 물어보기 딱인 코드네요
fun launchAndRepeatOnLifecycle(
    scope: LifecycleCoroutineScope,
    owner: LifecycleOwner,
    state: Lifecycle.State = Lifecycle.State.CREATED,
    action: suspend CoroutineScope.() -> Unit
) {
    scope.launch {
        owner.repeatOnLifecycle(state) {
            action()
        }
    }
}
