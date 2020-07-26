package github.com.vikramezhil.dvu.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.graphics.ColorUtils
import github.com.vikramezhil.dvu.R
import kotlin.math.roundToInt

/**
 * Screen Utils
 * @author vikramezhil
 */

class DvuScreenUtils {
    companion object {
        /**
         * Returns the view default size. Uses the supplied size if the MeasureSpec imposed no constraints.
         * Will get larger if allowed by the MeasureSpec.
         * @param size Int The supplied size
         * @param measureSpec Int The measured size
         * @return Int The converted default height
         */
        fun getDefaultSize(size: Int, measureSpec: Int): Int {
            val specMode = MeasureSpec.getMode(measureSpec)
            val specSize = MeasureSpec.getSize(measureSpec)
            return when (specMode) {
                MeasureSpec.EXACTLY -> specSize
                MeasureSpec.UNSPECIFIED, MeasureSpec.AT_MOST -> size
                else -> size
            }
        }

        /**
         * Gets a drawable with corner radius
         * @param color Int The drawable color
         * @param cornerRadius Float The corner radius color
         * @return Drawable? The generated drawable
         * {mTopLeftRadius, mTopLeftRadius, mTopRightRadius, mTopRightRadius, mBottomRightRadius, mBottomRightRadius, mBottomLeftRadius, mBottomLeftRadius}
         */
        fun getDrawableWithCornerRadius(color: Int, cornerRadius: Float): Drawable? {
            val gradientDrawable = GradientDrawable()
            gradientDrawable.cornerRadii = floatArrayOf(cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius)
            gradientDrawable.setColor(color)

            return gradientDrawable
        }

        /**
         * Gets a drawable with start corner radius
         * @param color Int The drawable color
         * @param cornerRadius Float The corner radius color
         * @return Drawable? The generated drawable
         * {mTopLeftRadius, mTopLeftRadius, mTopRightRadius, mTopRightRadius, mBottomRightRadius, mBottomRightRadius, mBottomLeftRadius, mBottomLeftRadius}
         */
        fun getDrawableWithStartCornerRadius(color: Int, cornerRadius: Float): Drawable? {
            val gradientDrawable = GradientDrawable()
            gradientDrawable.cornerRadii = floatArrayOf(cornerRadius, cornerRadius, 0f, 0f, 0f, 0f, cornerRadius, cornerRadius)
            gradientDrawable.setColor(color)

            return gradientDrawable
        }

        /**
         * Gets a drawable with end corner radius
         * @param color Int The drawable color
         * @param cornerRadius Float The corner radius color
         * @return Drawable? The generated drawable
         * {mTopLeftRadius, mTopLeftRadius, mTopRightRadius, mTopRightRadius, mBottomRightRadius, mBottomRightRadius, mBottomLeftRadius, mBottomLeftRadius}
         */
        fun getDrawableWithEndCornerRadius(color: Int, cornerRadius: Float): Drawable? {
            val gradientDrawable = GradientDrawable()
            gradientDrawable.cornerRadii = floatArrayOf(0f, 0f, cornerRadius, cornerRadius, cornerRadius, cornerRadius, 0f, 0f)
            gradientDrawable.setColor(color)

            return gradientDrawable
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


        /**
         * Blends two colors with alpha value
         * @param color1 Int The color value 1
         * @param color2 Int The color value 1
         * @param alpha Float The alpha value
         * @return Int The blended color
         */
        fun blendColor(color1: Int, color2: Int, alpha: Float): Int {
            return ColorUtils.blendARGB(color1, color2, alpha)
        }

        /**
         * Adjusts the color based on alpha
         * @param color Int The color to be adjusted
         * @param alpha Float The alpha value
         * @return Int The adjusted color based on alpha
         */
        fun adjustAlpha(color: Int, alpha: Float): Int {
            val factor = (Color.alpha(color) * alpha).roundToInt()
            val red = Color.red(color)
            val green = Color.green(color)
            val blue = Color.blue(color)

            return Color.argb(factor, red, green, blue)
        }
    }
}

/**
 * Gets the screen width
 * @receiver context Context The application context
 * @return Int The screen width
 */
fun Context.getScreenWidth(): Int {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(dm)
    return dm.widthPixels
}

/**
 * Gets the screen height
 * @receiver context Context The application context
 * @return Int The screen height
 */
fun Context.getScreenHeight(): Int {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(dm)
    return dm.heightPixels
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

/**
 * Gets the place holder icon
 * @receiver context Context The application context
 * @return Drawable The place holder icon drawable
 */
fun Context.getPlaceHolderIcon(): Drawable {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        resources.getDrawable(R.drawable.ic_dvu_icon_placeholder, theme)
    } else {
        @Suppress("DEPRECATION")
        resources.getDrawable(R.drawable.ic_dvu_icon_placeholder)
    }
}