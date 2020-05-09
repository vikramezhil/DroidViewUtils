package github.com.vikramezhil.droidviewutils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import github.com.vikramezhil.dvu.views.search.DvuSvItem
import kotlinx.android.synthetic.main.activity_search_view.*

class SearchViewActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search_view)

        val defaultSuggestions = ArrayList<DvuSvItem>()
        (1..10).toList().map {
            defaultSuggestions.add(DvuSvItem(null, R.drawable.ic_dvu_icon_placeholder, "Default Counter $it", null))
        }

        val suggestions = ArrayList<DvuSvItem>()
        (10..20).toList().map {
            suggestions.add(DvuSvItem(null, R.drawable.ic_dvu_icon_placeholder, "New Counter $it", "Sub New Counter ${it*2}"))
        }

        searchView.setDefaultSuggestions(defaultSuggestions)
        searchView.setSuggestions(suggestions)
    }
}