package github.com.vikramezhil.dvu.views.seekbar.circle

interface OnDvuSbCvListener {
    /**
     * Sends an update when the view is clicked
     */
    fun onClicked()

    /**
     * Sends an update on the progress
     * @param currentProgress Float The current progress
     * @param remainingProgress Float The remaining progress
     */
    fun onProgress(currentProgress: Float, remainingProgress: Float)
}