package github.com.vikramezhil.droidviewutils.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import github.com.vikramezhil.droidviewutils.R
import github.com.vikramezhil.droidviewutils.databinding.FragmentSeekBarCircleViewBinding
import github.com.vikramezhil.droidviewutils.viewmodel.SeekBarCircleViewModel

/**
 * Droid View Utils - Seek Bar Circle View Fragment
 * @author vikramezhil
 */

class FragmentSeekBarCircleView: Fragment() {

    companion object {
        fun newInstance() = FragmentSeekBarCircleView()
    }

    private lateinit var binding: FragmentSeekBarCircleViewBinding
    private lateinit var seekBarCircleVm: SeekBarCircleViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_seek_bar_circle_view, container, false)
        seekBarCircleVm = ViewModelProvider(this).get(SeekBarCircleViewModel::class.java)
        binding.seekBarCircleVm = seekBarCircleVm

        binding.dvuSbCircleView.onDvuSbCvListener = seekBarCircleVm.seekBarListener

        seekBarCircleVm.incrementVal.observe(viewLifecycleOwner, Observer { increment ->
            if (increment) {
                binding.dvuSbCircleView.properties.progress += 10f
            }
        })

        seekBarCircleVm.decrementVal.observe(viewLifecycleOwner, Observer { decrement ->
            if (decrement) {
                binding.dvuSbCircleView.properties.progress -= 2.5f
            }
        })

        return binding.root
    }
}