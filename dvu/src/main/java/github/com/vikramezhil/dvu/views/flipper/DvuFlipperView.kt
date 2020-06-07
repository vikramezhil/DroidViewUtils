package github.com.vikramezhil.dvu.views.flipper

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.AnimationUtils
import android.widget.ViewFlipper
import androidx.annotation.AttrRes
import github.com.vikramezhil.dvu.R

/**
 * Droid View Utils - Flipper View
 * @author vikramezhil
 */

class DvuFlipperView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0): ViewFlipper(context)  {

    private val properties: DvuFvProps = object: DvuFvProps() {
        override var initialX: Float = 0.0f

        override var infiniteSwipe: Boolean = false
            set(value) {
                field = value
                invalidate()
            }
    }

    private var listener: OnDvuFvListener? = null

    init {
        init(context, attrs)
    }

    /**
     * Initializes the view attributes
     * @param context Context The view context
     * @param attrs AttributeSet The view attributes
     */
    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs == null) return

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.DvuFlipperView, 0, 0)

        try {
            properties.infiniteSwipe = typedArray.getBoolean(R.styleable.DvuFlipperView_dvuFvInfiniteSwipe, properties.infiniteSwipe)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            typedArray.recycle()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        super.dispatchTouchEvent(ev)
        return true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when(it.action) {
                MotionEvent.ACTION_DOWN -> {
                    properties.initialX = it.x
                }
                MotionEvent.ACTION_UP -> {
                    val finalX = it.x
                    if (properties.initialX > finalX) {
                        if ((displayedChild == (childCount - 1)) && !properties.infiniteSwipe) {
                            // Reached end
                            return false
                        }

                        inAnimation = AnimationUtils.loadAnimation(context, R.anim.left_in)
                        outAnimation = AnimationUtils.loadAnimation(context, R.anim.left_out)
                        showNext()
                    } else {
                        if (displayedChild == 0 && !properties.infiniteSwipe) {
                            // Reached start
                            return false
                        }

                        inAnimation = AnimationUtils.loadAnimation(context, R.anim.right_in)
                        outAnimation = AnimationUtils.loadAnimation(context, R.anim.right_out)
                        showPrevious()
                    }

                    // Sending an update back on the current visible view
                    listener?.onCurrentViewVisible(currentView, displayedChild)
                } else -> {
                    return false
                }
            }
        } ?: run {
            return false
        }

        return true
    }

    /**
     * Sets the flipper view listener
     * @param listener OnDvuFvListener The class which initializes flipper view listener
     */
    fun setOnDvuFvFlipperListener(listener: OnDvuFvListener) {
        this.listener = listener
    }
}