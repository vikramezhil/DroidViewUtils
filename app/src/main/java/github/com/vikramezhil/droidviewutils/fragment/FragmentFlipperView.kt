package github.com.vikramezhil.droidviewutils.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import github.com.vikramezhil.droidviewutils.R
import github.com.vikramezhil.droidviewutils.databinding.FragmentFlipperViewBinding
import github.com.vikramezhil.droidviewutils.viewmodel.FlipperViewModel
import kotlinx.android.synthetic.main.fragment_flipper_view.*

/**
 * Droid View Utils - Flipper View Fragment
 * @author vikramezhil
 */

class FragmentFlipperView: Fragment() {

    companion object {
        fun newInstance() = FragmentFlipperView()
    }

    private lateinit var binding: FragmentFlipperViewBinding
    lateinit var flipperVm: FlipperViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_flipper_view, container, false)
        flipperVm = ViewModelProvider(this).get(FlipperViewModel::class.java)
        binding.flipperVm = flipperVm

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dvuFvPager.dvuFlipperView = dvuFlipperView
    }
}