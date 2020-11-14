package github.com.vikramezhil.droidviewutils.viewmodel

import android.app.Application
import android.util.Log
import android.view.View.OnClickListener
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import github.com.vikramezhil.dvu.views.seekbar.circle.OnDvuSbCvListener

/**
 * Seek Bar Circle View Model
 * @author vikramezhil
 */

class SeekBarCircleViewModel(application: Application): AndroidViewModel(application)  {

    var incrementVal: MutableLiveData<Boolean> = MutableLiveData()
    var decrementVal: MutableLiveData<Boolean> = MutableLiveData()

    val seekBarListener: OnDvuSbCvListener = object: OnDvuSbCvListener {
        override fun onClicked() {
            Log.d(SeekBarCircleViewModel::class.java.name, "Seek bar circle view clicked")
        }

        override fun onProgress(currentProgress: Float, remainingProgress: Float) {
            Log.d(SeekBarCircleViewModel::class.java.name, "Seek bar circle view -> current progress = $currentProgress, remaining progress = $remainingProgress")
        }
    }

    val increment: OnClickListener = OnClickListener {
        incrementVal.value = true
    }

    val decrement: OnClickListener = OnClickListener {
        decrementVal.value = true
    }
}