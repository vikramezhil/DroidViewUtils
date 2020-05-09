package github.com.vikramezhil.dvu.views.edittext

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import androidx.annotation.AttrRes
import androidx.appcompat.widget.AppCompatEditText

/**
 * Droid View Utils - Edit Text
 * @author vikramezhil
 */

class DvuEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0): AppCompatEditText(context, attrs, defStyleAttr) {

    private var listener: OnDvuEtListener? = null

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK) {
            listener?.onKeyboardClosed()

            return true
        }

        return false
    }

    /**
     * Sets the edit text listener
     * @param listener OnDvuEtListener? The class instance which implements the listener
     */
    fun setOnDvuEtListener(listener: OnDvuEtListener?) {
        this.listener = listener
    }
}