package github.com.vikramezhil.dvu.views.flipper

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.animation.AnimationUtils
import github.com.vikramezhil.dvu.R
import kotlin.math.abs

/**
 * Droid View Utils - Flipper View Swipe Gesture Detector
 * @author vikramezhil
 */

class DvuFvSGDetector(private val context: Context, private val view: DvuFlipperView, private val listener: OnDvuFvListener): GestureDetector.SimpleOnGestureListener()  {

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        try {
            if (e1 != null && e2 != null) {
                if (e1.x - e2.x > DvuFvProps.SWIPE_MIN_DISTANCE && abs(velocityX) > DvuFvProps.SWIPE_THRESHOLD_VELOCITY) {
                    // right to left swipe
                    view.inAnimation = AnimationUtils.loadAnimation(context, R.anim.left_in)
                    view.outAnimation = AnimationUtils.loadAnimation(context, R.anim.left_out)
                    view.showNext()
                } else if (e2.x - e1.x > DvuFvProps.SWIPE_MIN_DISTANCE && abs(velocityX) > DvuFvProps.SWIPE_THRESHOLD_VELOCITY) {
                    // left to right swipe
                    view.inAnimation = AnimationUtils.loadAnimation(context, R.anim.right_in)
                    view.outAnimation = AnimationUtils.loadAnimation(context, R.anim.right_out)
                    view.showPrevious()
                } else {
                    return false
                }
            } else {
                return false
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Sending an update back on the current visible view
        listener.onCurrentViewVisible(view.currentView, view.displayedChild)

        return true
    }
}