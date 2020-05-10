package github.com.vikramezhil.dvu.utils

import android.content.Context
import android.graphics.Color
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import kotlin.math.roundToInt

/**
 * Screen Utils
 * @author vikramezhil
 */

open class DvuScreenUtils {
    companion object {
        /**
         * Gets the screen width
         * @param context Context The application context
         * @return Int The screen width
         */
        fun getScreenWidth(context: Context): Int {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(dm)
            return dm.widthPixels
        }

        /**
         * Gets the screen height
         * @param context Context The application context
         * @return Int The screen height
         */
        fun getScreenHeight(context: Context): Int {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(dm)
            return dm.heightPixels
        }

        /**
         * Converts dp to pixels
         * @param context Context The application context
         * @param value Int The dp value to convert
         * @return Int The pixels value
         */
        fun dpToPx(context: Context, value: Int) : Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), context.resources.displayMetrics).toInt()
        }

        /**
         * Closes the view keyboard
         * @param view View? The view instance
         * @param context Context The application context
         */
        fun closeViewKeyboard(view: View?, context: Context) {
            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
        }

        /**
         * Gets the color with alpha applied
         * @param color Int The color to apply alpha
         * @param ratio Float The alpha ratio
         * @return Int The converted color with alpha applied
         */
        fun colorWithAlpha(color: Int, ratio: Float): Int {
            return Color.argb((Color.alpha(color) * ratio).roundToInt(), Color.red(color), Color.green(color), Color.blue(color))
        }
    }
}

/**
 * Check if input manager is accepting text
 * @receiver context Context The application context
 * @return The input manager accepting text status
 */
fun Context.isAcceptingText(): Boolean {
    val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return inputMethodManager.isAcceptingText
}