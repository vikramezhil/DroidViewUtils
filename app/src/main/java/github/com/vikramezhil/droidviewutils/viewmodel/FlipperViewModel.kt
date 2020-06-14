package github.com.vikramezhil.droidviewutils.viewmodel

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import github.com.vikramezhil.droidviewutils.R
import github.com.vikramezhil.dvu.views.flipper.OnDvuFvListener

/**
 * Flipper View Model
 * @author vikramezhil
 */

class FlipperViewModel(application: Application): AndroidViewModel(application) {

    var currentFlipperItem: ObservableField<String> = ObservableField()

    val flipperListener: OnDvuFvListener = object: OnDvuFvListener {
        override fun onPageSelected(position: Int) {
            Log.d(FlipperViewModel::class.java.name, "onPageSelected position - $position")
            currentFlipperItem.set("${application.resources.getString(R.string.dvu_flipper_view)} $position")
        }
    }
}