package github.com.vikramezhil.dvu.views.flipper

interface OnDvuFvListener {
    /**
     * Sends an update when a page is selected
     * @param position Int The current visible view position
     */
    fun onPageSelected(position: Int)
}