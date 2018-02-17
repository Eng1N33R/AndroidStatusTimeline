package io.engi.android.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.graphics.RectF
import android.os.Build

@Suppress("unused")
/**
 * Timeline overview of colour-coded statistics.
 * @author Ilya Gavrikov
 */
class StatusTimeline : View {
    private val mUnitPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var mUnitNumber: Int
    private var mDefaultColor : Int
    private var mUnitColors: Array<Int>
    private var mUnitPadding : Int

    private val mUnitBounds = RectF(0f, 0f, 0f, 0f)
    private var mUnitWidth = 0f
    private var mUnitHeight = 0f

    /**
     * Number of coloured units in the timeline instance.
     * @author Ilya Gavrikov
     * @since 1.0.0
     */
    var units
        get() = mUnitNumber
        set(value) {
            mUnitNumber = value
        }
    /**
     * Default colour for newly created units.
     * @author Ilya Gavrikov
     * @since 1.0.0
     */
    var defaultColor
        get() = mDefaultColor
        set(value) {
            mDefaultColor = value
        }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context,
            attrs, defStyleAttr) {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.StatusTimeline,
                defStyleAttr, 0)
        try {
            mUnitNumber = a.getInt(R.styleable.StatusTimeline_units, 7)
            mDefaultColor = a.getColor(R.styleable.StatusTimeline_defaultColor,
                    Color.parseColor("#aaaaaa"))
            mUnitColors = Array(mUnitNumber, { _ -> mDefaultColor })
            mUnitPadding = a.getInt(R.styleable.StatusTimeline_unitPadding, 8)
        } finally {
            a.recycle()
        }
    }

    /**
     * Get the current colour of the unit with the given index.
     * @author Ilya Gavrikov
     * @since 1.0.0
     * @param i unit index
     * @return unit colour integer (AARRGGBB)
     */
    fun getUnitColor(i: Int) : Int {
        return mUnitColors[i]
    }

    /**
     * Set the colour of the unit with the given index.
     * @author Ilya Gavrikov
     * @since 1.0.0
     * @param i unit index
     * @param color colour integer (AARRGGBB)
     */
    fun setUnitColor(i: Int, color: Int) {
        mUnitColors[i] = color
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var i = 0

        while (i < mUnitNumber) {
            mUnitPaint.color = mUnitColors[i]
            canvas.drawRect(mUnitBounds, mUnitPaint)
            mUnitBounds.offset(mUnitWidth + mUnitPadding, 0f)
            i++
        }
        mUnitBounds.offset(-(mUnitWidth + mUnitPadding) * i, 0f)
        postInvalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val xpad =
                if (Build.VERSION.SDK_INT > 17)
                    (paddingLeft + paddingRight + paddingStart + paddingEnd).toFloat()
                else
                    (paddingLeft + paddingRight).toFloat()
        val ypad = (paddingTop + paddingBottom).toFloat()

        val ww = w.toFloat() - xpad
        val hh = h.toFloat() - ypad

        mUnitWidth = ww / mUnitNumber - mUnitPadding
        mUnitHeight = hh
        mUnitBounds.left =
                if (Build.VERSION.SDK_INT > 17) 0f + paddingLeft + paddingStart
                else 0f + paddingLeft
        mUnitBounds.right = mUnitBounds.left + mUnitWidth
        mUnitBounds.top = 0f + paddingTop
        mUnitBounds.bottom = mUnitBounds.top + mUnitHeight
    }
}
