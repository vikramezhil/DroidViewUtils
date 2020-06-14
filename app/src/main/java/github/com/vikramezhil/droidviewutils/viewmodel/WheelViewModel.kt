package github.com.vikramezhil.droidviewutils.viewmodel

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import github.com.vikramezhil.dvu.views.wheel.OnDvuWvListener

/**
 * Wheel View Model
 * @author vikramezhil
 */

class WheelViewModel(application: Application): AndroidViewModel(application) {

    var horizontalWheelLiveData: ObservableField<String> = ObservableField()
    val wheelListenerHorizontal: OnDvuWvListener = object: OnDvuWvListener {
        override fun onItemSelected(position: Int, value: String) {
            horizontalWheelLiveData.set("Horizontal wheel - $value")
        }

        override fun onScrolling() {
            Log.d(WheelViewModel::class.java.name, "horizontal wheel is scrolling")
        }
    }

    var verticalWheelLiveData: ObservableField<String> = ObservableField()
    val wheelListenerVertical: OnDvuWvListener = object: OnDvuWvListener {
        override fun onItemSelected(position: Int, value: String) {
            verticalWheelLiveData.set("Vertical wheel - $value")
        }

        override fun onScrolling() {
            Log.d(WheelViewModel::class.java.name, "vertical wheel is scrolling")
        }
    }
}