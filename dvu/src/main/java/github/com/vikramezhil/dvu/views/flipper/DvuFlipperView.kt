package github.com.vikramezhil.dvu.views.flipper

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ViewFlipper
import androidx.annotation.AttrRes

/**
 * Droid View Utils - Flipper View
 * @author vikramezhil
 */

class DvuFlipperView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0): ViewFlipper(context)  {

    private val properties: DvuFvProps = object: DvuFvProps() {
        override var swipeGestureDetector: GestureDetector?  = null
    }

    private var listener: OnDvuFvListener? = null

    init {
        // Initializing the swipe gesture detector
        @Suppress("DEPRECATION")
        properties.swipeGestureDetector = GestureDetector(DvuFvSGDetector(context, this, object: OnDvuFvListener {
            override fun onCurrentViewVisible(view: View, position: Int) {
                listener?.onCurrentViewVisible(view, position)
            }
        }))

        // Setting the touch listener and assigning the event to the swipe gesture detector
        setOnTouchListener { _, event ->
            properties.swipeGestureDetector?.onTouchEvent(event)
            true
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        super.dispatchTouchEvent(ev)
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