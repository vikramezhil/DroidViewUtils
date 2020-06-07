package github.com.vikramezhil.dvu.views.flipper

import android.view.View

interface OnDvuFvListener {
    /**
     * Sends the current view visible data
     * @param view View The current visible view
     * @param position Int The current visible view position
     */
    fun onCurrentViewVisible(view: View, position: Int)
}