package github.com.vikramezhil.droidviewutils.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import github.com.vikramezhil.droidviewutils.R
import github.com.vikramezhil.droidviewutils.databinding.FragmentWheelViewBinding
import github.com.vikramezhil.droidviewutils.viewmodel.WheelViewModel

/**
 * Droid View Utils - Wheel View Fragment
 * @author vikramezhil
 */

class FragmentWheelView: Fragment() {

    companion object {
        fun newInstance() = FragmentWheelView()
    }

    private lateinit var binding: FragmentWheelViewBinding
    lateinit var wheelVm: WheelViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wheel_view, container, false)
        wheelVm = ViewModelProvider(this).get(WheelViewModel::class.java)
        binding.wheelVm = wheelVm

        return binding.root
    }
}