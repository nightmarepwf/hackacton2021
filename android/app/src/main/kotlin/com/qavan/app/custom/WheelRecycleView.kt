package com.qavan.app.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import androidx.core.content.res.getStringOrThrow
import androidx.recyclerview.widget.RecyclerView
import com.qavan.app.R
import com.qavan.app.extensions.fetchAttrs
import com.qavan.app.extensions.getColorOrDefault


class WheelRecycleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var _cornerRadius = 0f
    private var _backgroundColor = Color.WHITE

    private var _paintCenter = Paint()


    init {
        fetchAttrs(context, R.styleable.WheelRecycleView, attrs) {
            _cornerRadius = getStringOrThrow(
                R.styleable.WheelRecycleView_wrvBackgroundCorner
            ).toFloat()
            _backgroundColor = getColorOrDefault(
                R.styleable.WheelRecycleView_wrvBackgroundColor
            ) { Color.RED }
        }

//        _paint.color = _backgroundColor
//        _paint.isAntiAlias = true;

        _paintCenter.color = _backgroundColor;
        _paintCenter.isAntiAlias = true;
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val rectF = RectF()

        val viewWidth = width.toFloat()

        rectF.offset(0f, - viewWidth / 2f)
        rectF.set(
            -viewWidth / 20f,
            0f,
            viewWidth + viewWidth / 20f,
            viewWidth / 2f,
        )

        canvas.drawOval(rectF, _paintCenter)
    }

}
