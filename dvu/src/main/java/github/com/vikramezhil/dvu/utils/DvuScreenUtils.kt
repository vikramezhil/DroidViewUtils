package github.com.vikramezhil.dvu.utils

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager

/**
 * Screen Utils
 * @author vikramezhil
 */

class DvuScreenUtils {
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
    }
}