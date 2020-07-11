package github.com.vikramezhil.droidviewutils.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import github.com.vikramezhil.droidviewutils.R
import github.com.vikramezhil.droidviewutils.databinding.FragmentSeekBarCircleViewBinding
import github.com.vikramezhil.droidviewutils.viewmodel.SeekBarCircleViewModel

class FragmentSeekBarCircleView: Fragment() {

    companion object {
        fun newInstance() = FragmentSeekBarCircleView()
    }

    private lateinit var binding: FragmentSeekBarCircleViewBinding
    lateinit var seekBarCircleVm: SeekBarCircleViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_seek_bar_circle_view, container, false)
        seekBarCircleVm = ViewModelProvider(this).get(SeekBarCircleViewModel::class.java)
        binding.seekBarCircleVm = seekBarCircleVm

        return binding.root
    }
}