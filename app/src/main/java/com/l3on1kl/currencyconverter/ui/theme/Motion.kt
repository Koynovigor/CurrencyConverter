package com.l3on1kl.currencyconverter.ui.theme

import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MotionScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Rect

@OptIn(
    ExperimentalSharedTransitionApi::class,
    ExperimentalMaterial3ExpressiveApi::class
)
val MotionScheme.sharedElementTransitionSpec: BoundsTransform
    @Composable
    get() = object : BoundsTransform {
        override fun transform(
            initialBounds: Rect,
            targetBounds: Rect,
        ): FiniteAnimationSpec<Rect> {
            return this@sharedElementTransitionSpec.slowSpatialSpec()
        }
    }