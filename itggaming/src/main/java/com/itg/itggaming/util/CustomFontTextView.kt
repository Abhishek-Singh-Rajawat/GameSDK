package com.itg.itggaming.util

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.itg.itggaming.R

open class CustomFontTextView : AppCompatTextView {
    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        applyCustomFont(context, attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
        applyCustomFont(context, attrs)
    }


    private fun applyCustomFont(
        context: Context,
        attrs: AttributeSet?
    ) {
        val array = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomFontTextView,
            0, 0
        )
        val typefaceType: Int
        typefaceType = try {
            array.getInteger(R.styleable.CustomFontTextView_customFontStyle, 0)
        } finally {
            array.recycle()
        }
        if (!isInEditMode) {
            typeface = getTypeFace(typefaceType)
        }
    }

    private fun getTypeFace(type: Int): Typeface {
        val typeface: Typeface
        typeface = when (type) {
            FontConstants.BOLD, FontConstants.EXTRA_BOLD, FontConstants.SEMI_BOLD -> Typeface.createFromAsset(
                context.assets,
                NS_BOLD
            )
            else -> Typeface.createFromAsset(
                context.assets,
                NS_REGULAR
            )
        }
        return typeface
    }

    interface FontConstants {
        companion object {

            const val SEMI_BOLD = 3
            const val BOLD = 4
            const val EXTRA_BOLD = 5
        }
    }

    companion object {
        var NS_REGULAR = "NotoSans-Regular.ttf"
        var NS_BOLD = "NotoSans-Bold.ttf"
    }
}