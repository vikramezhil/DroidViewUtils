package github.com.vikramezhil.dvu.views.seekbar.circle

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import androidx.annotation.AttrRes
import androidx.appcompat.widget.AppCompatTextView
import github.com.vikramezhil.dvu.R
import github.com.vikramezhil.dvu.utils.DvuScreenUtils
import kotlin.math.abs
import kotlin.math.min

/**
 * Droid View Utils - Seek Bar Circle View
 * @author vikramezhil
 */

class DvuSeekBarCircleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0): AppCompatTextView(context, attrs, defStyleAttr) {

    companion object {
        private const val MIN = 0
        private const val MAX = 100
        private const val CLICK_THRESHOLD = 200
        private const val THICKNESS = 35f
        private const val ADJUST_ALPHA = 0.3f
        private const val BLEND_ALPHA = 0.8f
    }

    private val rectF: RectF = RectF()
    private var point: PointF? = null

    private val backgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.style = Paint.Style.FILL
        it.color = Color.TRANSPARENT
    }
    private val progressBackgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.style = Paint.Style.STROKE
        it.strokeWidth = THICKNESS
    }
    private val progressForegroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.style = Paint.Style.STROKE
        it.strokeWidth = THICKNESS
    }
    private val exceededBackgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.style = Paint.Style.STROKE
        it.strokeWidth = THICKNESS
    }
    private val exceededForegroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.style = Paint.Style.STROKE
        it.strokeWidth = THICKNESS
    }

    var onDvuSbCvListener: OnDvuSbCvListener? = null

    var properties = object: DvuSbCvProps() {
        override var units: String? = null
            set(value) {
                field = value ?: ""

                refreshProgressText()
                invalidate()
                requestLayout()
            }
        override var separator: String? = null
            set(value) {
                field = value ?: ""

                refreshProgressText()
                invalidate()
                requestLayout()
            }
        override var progress: Int = 0
            set(value) {
                field = value

                refreshProgressText()
                invalidate()
                requestLayout()

                // Sending an update back on the progress
                onDvuSbCvListener?.onProgress(progress, if (progress in 0..max) { max - progress } else { 0 })
            }
        override var min: Int = MIN
            set(value) {
                field = value
                invalidate()
            }
        override var max: Int = MAX
            set(value) {
                field = value
                invalidate()
            }
        override var progressColor: Int = Color.GREEN
            set(value) {
                field = value
                initDrawProps()

                invalidate()
                requestLayout()
            }
        override var exceededColor: Int = Color.RED
            set(value) {
                field = value
                initDrawProps()

                invalidate()
                requestLayout()
            }
        override var thickness: Float = THICKNESS
            set(value) {
                field = value
                initDrawProps()

                invalidate()
                requestLayout()
            }
        override var enableClick: Boolean = false
            set(value) {
                field = value
                invalidate()
            }
    }

    init {
        gravity = Gravity.CENTER

        attrs?.let {
            val typedArray = context.theme.obtainStyledAttributes(it, R.styleable.DvuSeekBarCircleView, 0, 0)

            try {
                properties.progress = typedArray.getInt(R.styleable.DvuSeekBarCircleView_dvuSbCvProgress, properties.progress)
                properties.min = typedArray.getInt(R.styleable.DvuSeekBarCircleView_dvuSbCvMin, properties.min)
                properties.max = typedArray.getInt(R.styleable.DvuSeekBarCircleView_dvuSbCvMax, properties.max)
                properties.progressColor = typedArray.getInt(R.styleable.DvuSeekBarCircleView_dvuSbCvProgressColor, properties.progressColor)
                properties.exceededColor = typedArray.getInt(R.styleable.DvuSeekBarCircleView_dvuSbCvExceededColor, properties.exceededColor)

                properties.thickness = typedArray.getFloat(R.styleable.DvuSeekBarCircleView_dvuSbCvArcThickness, properties.thickness)

                properties.enableClick = typedArray.getBoolean(R.styleable.DvuSeekBarCircleView_dvuSbCvEnableClick, properties.enableClick)

                properties.units = typedArray.getString(R.styleable.DvuSeekBarCircleView_dvuSbCvProgressUnit)
                properties.separator = typedArray.getString(R.styleable.DvuSeekBarCircleView_dvuSbCvProgressSeparator)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                typedArray.recycle()
            }

            initDrawProps()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val height = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        val width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val min = min(width, height)
        setMeasuredDimension(min, min)

        val padding = properties.thickness / 2
        rectF.set(padding, padding, min - padding, min - padding)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            val ovalBackgroundPaint = if (properties.progress > properties.max) {
                exceededBackgroundPaint
            } else {
                progressBackgroundPaint
            }

            val ovalForegroundPaint = if (properties.progress > properties.max) {
                exceededForegroundPaint
            } else {
                progressForegroundPaint
            }

            // Background oval
            it.drawOval(rectF, ovalBackgroundPaint)

            // Foreground oval
            val angle = ((360f * properties.progress)/properties.max)
            val startAngle = -90f
            it.drawArc(rectF, startAngle, angle, false, ovalForegroundPaint)

            // Fill Oval
            // it.drawArc(rectF, 0f, 360f, false, backgroundPaint)

            // Refreshing the progress text
            refreshProgressText()

            if (properties.enableClick) {
                point?.let { _ ->
                    // Drawn when the view is touched
                    it.drawArc(rectF, 0f, 360f, false, backgroundPaint)
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (properties.enableClick) {
            event?.let {
                val startX = it.x
                val startY = it.y

                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        point = PointF(startX, startY)
                        invalidate()
                    }
                    MotionEvent.ACTION_MOVE -> {
                        point?.let { pointF ->  
                            pointF.set(startX, startY)
                            invalidate()
                        }
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        if (it.action == MotionEvent.ACTION_UP) {
                            val endX = it.x
                            val endY = it.y

                            if (isAValidClick(startX, endX, startY, endY)) {
                                // Sending an update that the view was clicked by the user
                                onDvuSbCvListener?.onClicked()
                            }
                        }

                        point = null
                        invalidate()
                    }
                    else -> {
                        Log.d(DvuSeekBarCircleView::class.java.name, "onTouchEvent ${it.action} not supported")
                    }
                }
            }
        }

        return properties.enableClick
    }

    /**
     * Initializes the draw props
     */
    private fun initDrawProps() {
        progressBackgroundPaint.color = DvuScreenUtils.adjustAlpha(properties.progressColor, ADJUST_ALPHA)
        progressBackgroundPaint.strokeWidth = properties.thickness

        progressForegroundPaint.color = properties.progressColor
        progressForegroundPaint.strokeWidth = properties.thickness

        val blendColor = DvuScreenUtils.blendColor(properties.progressColor, properties.exceededColor, BLEND_ALPHA)
        exceededBackgroundPaint.color = DvuScreenUtils.adjustAlpha(blendColor, ADJUST_ALPHA)
        exceededBackgroundPaint.strokeWidth = properties.thickness

        exceededForegroundPaint.color = blendColor
        exceededForegroundPaint.strokeWidth = properties.thickness
    }

    /**
     * Refreshes the progress text
     */
    private fun refreshProgressText() {
        text = properties.separator?.let { "${properties.progress} ${properties.units} \n\n $it \n\n ${properties.max} ${properties.units}" }
                            ?: "${properties.progress} ${properties.units} \n\n ${properties.max} ${properties.units}"
    }

    /**
     * Checks if the view touch is a valid click
     * @param startX Float The start X position
     * @param endX Float The end X position
     * @param startY Float The start Y position
     * @param endY Float The end Y position
     * @return Boolean The valid click status
     */
    private fun isAValidClick(startX: Float, endX: Float, startY: Float, endY: Float): Boolean {
        val differenceX = abs(startX - endX)
        val differenceY = abs(startY - endY)

        return !(differenceX > CLICK_THRESHOLD || differenceY > CLICK_THRESHOLD)
    }
}