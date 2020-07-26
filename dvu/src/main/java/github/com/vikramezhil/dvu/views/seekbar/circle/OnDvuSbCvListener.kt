package github.com.vikramezhil.dvu.views.seekbar.circle

interface OnDvuSbCvListener {
    /**
     * Sends an update when the view is clicked
     */
    fun onClicked()

    /**
     * Sends an update on the progress
     * @param currentProgress Int The current progress
     * @param remainingProgress Int The remaining progress
     */
    fun onProgress(currentProgress: Int, remainingProgress: Int)
}