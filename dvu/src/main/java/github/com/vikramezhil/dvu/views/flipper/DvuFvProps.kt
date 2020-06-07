package github.com.vikramezhil.dvu.views.flipper

import android.view.GestureDetector

/**
 * Droid View Utils - Flipper View Props
 * @author vikramezhil
 */

abstract class DvuFvProps {

    companion object {
        const val SWIPE_MIN_DISTANCE = 120
        const val SWIPE_THRESHOLD_VELOCITY = 200
    }

    abstract var swipeGestureDetector: GestureDetector?
}