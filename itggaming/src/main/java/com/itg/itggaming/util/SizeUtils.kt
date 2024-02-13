package com.itg.itggaming.util

import android.content.res.Resources
import android.util.TypedValue

object SizeUtils {
    fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }
}