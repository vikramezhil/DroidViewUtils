package github.com.vikramezhil.dvu.views.seekbar.circle

interface OnDvuSbCvListener {
    /**
     * Sends an update when points are changed
     * @param view DvuSeekBarCircleView The view instance
     * @param points Int The current points
     * @param fromUser Boolean The points changed by user status
     */
    fun onPointsChanged(view: DvuSeekBarCircleView, points: Int, fromUser: Boolean)

    /**
     * Sends an update when seek bar is touch started
     * @param view DvuSeekBarCircleView The view instance
     */
    fun onStartTrackingTouch(view: DvuSeekBarCircleView)

    /**
     * Sends an update when seek bar is touch stopped
     * @param view DvuSeekBarCircleView The view instance
     */
    fun onStopTrackingTouch(view: DvuSeekBarCircleView)
}