package github.com.vikramezhil.dvu.views.seekbar.circle

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.AttrRes
import androidx.appcompat.widget.AppCompatTextView
import github.com.vikramezhil.dvu.R
import github.com.vikramezhil.dvu.utils.getPlaceHolderIcon
import kotlin.math.*

/**
 * Droid View Utils - Seek Bar Circle View
 * @author vikramezhil
 */

class DvuSeekBarCircleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0): View(context, attrs, defStyleAttr) {

    companion object {
        private const val MIN = 0
        private const val MAX = 100

        private const val ANGLE_OFFSET = -90
        private const val INVALID_VALUE = -1
    }

    /**
     * The counts of point update to determine whether to change previous progress.
     */
    private var mUpdateTimes = 0
    private var mPreviousProgress = -1
    private var mCurrentProgress = 0

    /**
     * Determine whether reach max of point.
     */
    private var isMax = false

    /**
     * Determine whether reach min of point.
     */
    private var isMin = false

    // For Arc
    private var mArcRect = RectF()
    private var mArcPaint: Paint = Paint()

    // For Progress
    private var mProgressPaint: Paint = Paint()
    private var mProgressSweep = 0f

    private var mCenterX = 0
    private var mCenterY = 0
    private var mCircleRadius = 0

    // Coordinator (X, Y) of Indicator icon
    private var mThumbX = 0
    private var mThumbY = 0
    private var mThumbSize = 0

    private var mPadding = 0
    private var mAngle = 0.0
    private var mIsThumbSelected = false

    var listener: OnDvuSbCvListener? = null

    var properties = object: DvuSbCvProps() {
        override var progress: Int = 0
            set(value) {
                field = value
                invalidate()
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
        override var step: Int = 1
            set(value) {
                field = value
                invalidate()
            }
        override var thumbIcon: Drawable = context.getPlaceHolderIcon()
            set(value) {
                field = value
                invalidate()
            }
        override var progressColor: Int = Color.GREEN
            set(value) {
                field = value
                invalidate()
            }
        override var progressWidth: Int = 12
            set(value) {
                field = value
                invalidate()
            }
        override var arcColor: Int = Color.GRAY
            set(value) {
                field = value
                invalidate()
            }
        override var arcWidth: Int = 8
            set(value) {
                field = value
                invalidate()
            }
        override var textStyle: Int = 0
            set(value) {
                field = value
                invalidate()
            }
    }

    init {
        attrs?.let {
            val typedArray = context.theme.obtainStyledAttributes(it, R.styleable.DvuSeekBarCircleView, 0, 0)

            try {
                properties.progress = typedArray.getInt(R.styleable.DvuSeekBarCircleView_dvuSbCvProgress, properties.progress)

                properties.min = typedArray.getInt(R.styleable.DvuSeekBarCircleView_dvuSbCvMin, properties.min)
                properties.max = typedArray.getInt(R.styleable.DvuSeekBarCircleView_dvuSbCvMax, properties.max)

                properties.step = typedArray.getInt(R.styleable.DvuSeekBarCircleView_dvuSbCvMax, properties.step)

                properties.progressColor = typedArray.getInt(R.styleable.DvuSeekBarCircleView_dvuSbCvProgressColor, properties.progressColor)
                properties.progressWidth = typedArray.getInt(R.styleable.DvuSeekBarCircleView_dvuSbCvProgressWidth, properties.progressWidth)

                properties.arcColor = typedArray.getInt(R.styleable.DvuSeekBarCircleView_dvuSbCvArcColor, properties.arcColor)
                properties.arcWidth = typedArray.getInt(R.styleable.DvuSeekBarCircleView_dvuSbCvArcWidth, properties.arcWidth)

                properties.textStyle = typedArray.getResourceId(R.styleable.DvuSeekBarCircleView_dvuSbCvTxtStyle, properties.textStyle)

                val thumbIcon = typedArray.getDrawable(R.styleable.DvuSeekBarCircleView_dvuSbCvThumbIcon)
                if (thumbIcon != null) {
                    properties.thumbIcon = thumbIcon
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                typedArray.recycle()
            }
        }

//        val all = paddingLeft + paddingRight + paddingBottom + paddingTop + paddingEnd + paddingStart
//        mPadding = all / 6

        val density = context.resources.displayMetrics.density
        properties.progressWidth = (density * properties.progressWidth).toInt()
        properties.arcWidth = (density * properties.arcWidth).toInt()

        properties.progress = if (properties.progress > properties.max) properties.max else properties.progress
        properties.progress = if (properties.progress < properties.min) properties.min else properties.progress

        mProgressSweep = properties.progress.toFloat() / valuePerDegree()
        mAngle = Math.PI / 2 - mProgressSweep * Math.PI / 180
        mCurrentProgress = (mProgressSweep * valuePerDegree()).roundToInt()

        mArcPaint.color = properties.arcColor
        mArcPaint.isAntiAlias = true
        mArcPaint.style = Paint.Style.STROKE
        mArcPaint.strokeWidth = properties.arcWidth.toFloat()

        mProgressPaint.color = properties.progressColor
        mProgressPaint.isAntiAlias = true
        mProgressPaint.style = Paint.Style.STROKE
        mProgressPaint.strokeWidth = properties.progressWidth.toFloat()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val min = w.coerceAtMost(h)

        // find circle's rectangle points
        val alignLeft = (w - min) / 2
        val alignTop = (h - min) / 2
        val alignRight = alignLeft + min
        val alignBottom = alignTop + min

        // save circle coordinates
        mCenterX = alignRight / 2 + (w - alignRight) / 2
        mCenterY = alignBottom / 2 + (h - alignBottom) / 2

        val progressDiameter = min - mPadding.toFloat()
        mCircleRadius = (progressDiameter / 2).toInt()

        val top = h / 2 - progressDiameter / 2
        val left = w / 2 - progressDiameter / 2
        mArcRect[left, top, left + progressDiameter] = top + progressDiameter

        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        // draw the arc and progress
        canvas?.let {
            it.drawCircle(mCenterX.toFloat(), mCenterY.toFloat(), mCircleRadius.toFloat(), mArcPaint)
            it.drawArc(mArcRect, ANGLE_OFFSET.toFloat(), mProgressSweep, false, mProgressPaint)

            // find thumb position
            mThumbX = (mCenterX + mCircleRadius * cos(mAngle)).toInt()
            mThumbY = (mCenterY - mCircleRadius * sin(mAngle)).toInt()

            properties.thumbIcon.setBounds(mThumbX - mThumbSize / 2, mThumbY - mThumbSize / 2, mThumbX + mThumbSize / 2, mThumbY + mThumbSize / 2)
            properties.thumbIcon.draw(canvas)
        }

        super.onDraw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                // start moving the thumb (this is the first touch)
                val x = event.x.toInt()
                val y = event.y.toInt()
                if (x < mThumbX + mThumbSize && x > mThumbX - mThumbSize && y < mThumbY + mThumbSize && y > mThumbY - mThumbSize) {
                    parent.requestDisallowInterceptTouchEvent(true)
                    mIsThumbSelected = true

                    updateProgressState(x, y)

                    // Sending an update that the touch event has started
                    listener?.onStartTrackingTouch(this)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                // still moving the thumb (this is not the first touch)
                if (mIsThumbSelected) {
                    val x = event.x.toInt()
                    val y = event.y.toInt()

                    updateProgressState(x, y)
                }
            }
            MotionEvent.ACTION_UP -> {
                // finished moving (this is the last touch)
                parent.requestDisallowInterceptTouchEvent(false)
                mIsThumbSelected = false

                // Sending an update that the touch event has stopped
                listener?.onStartTrackingTouch(this)
            }
        }

        // redraw the whole component
        return true
    }

    /**
     * Updates the progress state
     * @param touchX Int The touch point X
     * @param touchY Int The touch point Y
     */
    private fun updateProgressState(touchX: Int, touchY: Int) {
        val distanceX = touchX - mCenterX
        val distanceY = mCenterY - touchY

        val c = sqrt(distanceX.toDouble().pow(2.0) + distanceY.toDouble().pow(2.0))
        mAngle = acos(distanceX / c)

        if (distanceY < 0) { mAngle = -mAngle }
        mProgressSweep = (90 - mAngle * 180 / Math.PI).toFloat()

        if (mProgressSweep < 0) mProgressSweep += 360f
        val progress = (mProgressSweep * valuePerDegree()).roundToInt()

        updateProgress(progress, true)
    }

    /**
     * Updates the progress state
     * @param progressVal Int The progress value
     * @param fromUser Boolean The value from user status
     */
    private fun updateProgress(progressVal: Int, fromUser: Boolean) {
        var progress = progressVal

        // detect points change closed to max or min
        val maxDetectValue = (properties.max.toDouble() * 0.99).toInt()
        val minDetectValue: Int = (properties.max.toDouble() * 0.005).toInt() + properties.min

        mUpdateTimes++
        if (progress == INVALID_VALUE) {
            return
        }

        // avoid accidentally touch to become max from original point
        if (progress > maxDetectValue && mPreviousProgress == INVALID_VALUE) {
            return
        }

        // record previous and current progress change
        if (mUpdateTimes == 1) {
            mCurrentProgress = progress
        } else {
            mPreviousProgress = mCurrentProgress
            mCurrentProgress = progress
        }

        properties.progress = progress - progress % properties.step

        /**
         * Determine whether reach max or min to lock point update event.
         *
         * When reaching max, the progress will drop from max (or maxDetectPoints ~ max
         * to min (or min ~ minDetectPoints) and vice versa.
         *
         * If reach max or min, stop increasing / decreasing to avoid exceeding the max / min.
         */
        if (mUpdateTimes > 1 && !isMin && !isMax) {
            if (mPreviousProgress >= maxDetectValue && mCurrentProgress <= minDetectValue && mPreviousProgress > mCurrentProgress) {
                isMax = true
                progress = properties.max
                properties.progress = properties.max
                mProgressSweep = 360f

                // Sending an update that the points have changed
                listener?.onPointsChanged(this, progress, fromUser)

                invalidate()
            } else if (mCurrentProgress >= maxDetectValue && mPreviousProgress <= minDetectValue && mCurrentProgress > mPreviousProgress || mCurrentProgress <= properties.min) {
                isMin = true
                progress = properties.min
                properties.progress = properties.min
                mProgressSweep = properties.min / valuePerDegree()

                // Sending an update that the points have changed
                listener?.onPointsChanged(this, progress, fromUser)

                invalidate()
            }
        } else {
            // Detect whether decreasing from max or increasing from min, to unlock the update event.
            // Make sure to check in detect range only.
            if (isMax and (mCurrentProgress < mPreviousProgress) && mCurrentProgress >= maxDetectValue) {
                isMax = false
            }

            if (isMin && mPreviousProgress < mCurrentProgress && mPreviousProgress <= minDetectValue && mCurrentProgress <= minDetectValue &&  properties.progress >= properties.min) {
                isMin = false
            }
        }

        if (!isMax && !isMin) {
            properties.progress  = if (properties.progress  > properties.max) properties.max else progress
            properties.progress  = if (properties.progress  < properties.min) properties.min else progress

            // Sending an update that the points have changed
            progress -= progress % properties.step
            listener?.onPointsChanged(this, progress, fromUser)

            invalidate()
        }
    }

    private fun valuePerDegree(): Float {
        return properties.max / 360.0f
    }
}