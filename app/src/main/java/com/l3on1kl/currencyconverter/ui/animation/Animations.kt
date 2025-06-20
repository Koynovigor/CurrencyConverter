package com.l3on1kl.currencyconverter.ui.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

object Animations {
    val ListItemPlacement: FiniteAnimationSpec<IntOffset> =
        tween(
            durationMillis = 887,
            easing = LinearOutSlowInEasing
        )

    val FadeSpec: FiniteAnimationSpec<Float> =
        tween(
            durationMillis = 887,
            easing = FastOutSlowInEasing
        )

    val ListScroll: AnimationSpec<Float> =
        tween(
            durationMillis = 887,
            easing = LinearOutSlowInEasing
        )
}

suspend fun smoothScrollToTop(state: LazyListState) = coroutineScope {
    if (state.firstVisibleItemIndex > 0) {
        state.animateScrollToItem(0)
    }

    val start = state.firstVisibleItemScrollOffset.toFloat()
    if (start == 0f) return@coroutineScope

    val anim = Animatable(start)
    var prev = start

    launch {
        snapshotFlow { anim.value }
            .collect { curr ->
                val delta = curr - prev
                prev = curr
                state.scrollBy(delta)
            }
    }

    anim.animateTo(
        targetValue = 0f,
        animationSpec = Animations.ListScroll
    )
}
