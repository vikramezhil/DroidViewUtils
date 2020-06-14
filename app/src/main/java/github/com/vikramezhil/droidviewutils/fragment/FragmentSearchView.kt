package github.com.vikramezhil.droidviewutils.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import github.com.vikramezhil.droidviewutils.R
import github.com.vikramezhil.droidviewutils.databinding.FragmentSearchViewBinding
import github.com.vikramezhil.droidviewutils.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search_view.*

/**
 * Droid View Utils - Search View Fragment
 * @author vikramezhil
 */

class FragmentSearchView: Fragment() {

    companion object {
        fun newInstance() = FragmentSearchView()
    }

    private lateinit var binding: FragmentSearchViewBinding
    lateinit var searchVm: SearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_view, container, false)
        searchVm = ViewModelProvider(this).get(SearchViewModel::class.java)
        binding.searchVm = searchVm

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dvuSearchView.properties.defaultSuggestions = searchVm.defaultSuggestions
        dvuSearchView.properties.suggestions = searchVm.suggestions
    }
}