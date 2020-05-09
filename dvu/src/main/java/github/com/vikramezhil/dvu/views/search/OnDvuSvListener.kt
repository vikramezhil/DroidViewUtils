package github.com.vikramezhil.dvu.views.search

interface OnDvuSvListener {

    /**
     * Sends an update when the search view has focus
     */
    fun onHasFocus()

    /**
     * Sends an update when the search view has lost focus
     */
    fun onLostFocus()

    /**
     * Sends an update whenever the search text has changed
     * @param oldQuery String? The old query
     * @param newQuery String? The new query
     */
    fun onSearchTextChanged(oldQuery: String?, newQuery: String?)

    /**
     * Sends an update with the query when the search action is triggered
     * @param searchQuery String? The query to be searched
     */
    fun onSearchText(searchQuery: String?)

    /**
     * Sends an update when a suggestion item is clicked
     * @param clickedSuggestionItem DvuSvItem The clicked suggestion item
     */
    fun onSearchSuggestionItem(clickedSuggestionItem: DvuSvItem)

    /**
     * Sends an update when the mic was clicked
     */
    fun onMicClicked()

    /**
     * Sends an update when the action item was clicked
     */
    fun onActionItemClicked()
}