package github.com.vikramezhil.dvu.views.search

interface OnDvuSvItemListener {
    /**
     * Sends an update when a suggestion item is clicked
     * @param clickedSuggestionItem DvuSvItem The selected suggestion item
     */
    fun onSuggestionItemClicked(clickedSuggestionItem: DvuSvItem)

    /**
     * Sends an update if suggestions are found
     * @param suggestionsFound The suggestions found status
     */
    fun onSuggestionsFound(suggestionsFound: Boolean)
}