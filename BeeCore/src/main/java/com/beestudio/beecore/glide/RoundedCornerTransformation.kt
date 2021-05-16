package com.beestudio.beecore.glide

import android.graphics.*
import coil.annotation.ExperimentalCoilApi
import coil.size.Size
import coil.transform.AnimatedTransformation
import coil.transform.PixelOpacity

@ExperimentalCoilApi
class RoundedCornerTransformation: AnimatedTransformation {
    override fun transform(canvas: Canvas): PixelOpacity {
        return PixelOpacity.TRANSLUCENT
    }
}