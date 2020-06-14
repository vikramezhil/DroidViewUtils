package github.com.vikramezhil.droidviewutils.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import github.com.vikramezhil.droidviewutils.R
import github.com.vikramezhil.dvu.views.search.DvuSvItem
import github.com.vikramezhil.dvu.views.search.OnDvuSvListener

/**
 * Search View Model
 * @author vikramezhil
 */

class SearchViewModel(application: Application): AndroidViewModel(application) {

    val defaultSuggestions = ArrayList<DvuSvItem>()
    val suggestions = ArrayList<DvuSvItem>()

    val searchListener: OnDvuSvListener = object: OnDvuSvListener {
        override fun onHasFocus() {
            Log.d(SearchViewModel::class.java.name, "onHasFocus")
        }

        override fun onLostFocus() {
            Log.d(SearchViewModel::class.java.name, "onLostFocus")
        }

        override fun onSearchTextChanged(oldQuery: String?, newQuery: String?) {
            Log.d(SearchViewModel::class.java.name, "onSearchTextChanged - old query = $oldQuery, new query = $newQuery")
        }

        override fun onSearchText(searchQuery: String?) {
            Log.d(SearchViewModel::class.java.name, "onSearchText - search query = $searchQuery")
        }

        override fun onSearchSuggestionItem(clickedSuggestionItem: DvuSvItem) {
            Log.d(SearchViewModel::class.java.name, "onSearchSuggestionItem = $clickedSuggestionItem")
        }

        override fun onMicClicked() {
            Log.d(SearchViewModel::class.java.name, "onMicClicked")
        }

        override fun onActionItemClicked() {
            Log.d(SearchViewModel::class.java.name, "onActionItemClicked")
        }
    }

    init {
        (1..10).toList().map {
            defaultSuggestions.add(DvuSvItem(null, R.drawable.ic_dvu_icon_placeholder, "Default Counter $it", null))
        }

        (10..20).toList().map {
            suggestions.add(DvuSvItem(null, R.drawable.ic_dvu_icon_placeholder, "New Counter $it", "Sub New Counter ${it*2}"))
        }
    }
}