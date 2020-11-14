package github.com.vikramezhil.droidviewutils.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import github.com.vikramezhil.droidviewutils.R
import github.com.vikramezhil.droidviewutils.databinding.FragmentIconsViewBinding
import github.com.vikramezhil.droidviewutils.viewmodel.IconsViewModel

class FragmentIconsView: Fragment() {

    companion object {
        fun newInstance() = FragmentIconsView()
    }

    private lateinit var binding: FragmentIconsViewBinding
    private lateinit var iconsVm: IconsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_icons_view, container, false)
        iconsVm = ViewModelProvider(this).get(IconsViewModel::class.java)
        binding.iconsVm = iconsVm

        return binding.root
    }
}