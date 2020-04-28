package github.com.vikramezhil.dvu.views.wheel

interface OnDvuWvListener {
    /**
     * Sends an update when the item is selected
     * @param position Int The item position
     * @param value String The item value
     */
    fun onItemSelected(position: Int, value: String)

    /**
     * Sends an update when scrolling is triggered
     */
    fun onScrolling()
}