package github.com.vikramezhil.dvu.views.flipper

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.AttrRes
import com.google.android.material.tabs.TabLayout

/**
 * Droid View Utils - Flipper View Indicator
 * @author vikramezhil
 */

class DvuFlipperViewIndicator @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0): TabLayout(context, attrs, defStyleAttr) {

    var onDvuFvListener: OnDvuFvListener? = null

    /**
     * Sets the indicator for the view pager
     * @param dvuFvPager DvuFlipperView? The flipper view instance
     * @param icon Drawable The indicator icon
     * @param activeColor Int The active indicator color
     * @param inActiveColor Int The inactive indicator color
     */
    fun setupIndicator(dvuFvPager: DvuFlipperView?, icon: Drawable, activeColor: Int, inActiveColor: Int) {
        if (tabCount == 0) {
            // Connecting the view pager with the tab layout
            setupWithViewPager(dvuFvPager, true)

            // Adding the indicator icon for each tab
            for(index in 0 until tabCount) {
                getTabAt(index)?.icon = icon.constantState?.newDrawable()?.mutate()
                getTabAt(index)?.let {
                    if (index == 0) {
                        updateIconColor(it, activeColor)
                    } else {
                        updateIconColor(it, inActiveColor)
                    }
                }
            }

            @Suppress("DEPRECATION")
            addOnTabSelectedListener(object: OnTabSelectedListener {
                override fun onTabReselected(tab: Tab?) {}

                override fun onTabUnselected(tab: Tab?) {
                    tab?.let { updateIconColor(it, inActiveColor) }
                }

                override fun onTabSelected(tab: Tab?) {
                    tab?.let {
                        onDvuFvListener?.onPageSelected(it.position)

                        updateIconColor(it, activeColor)
                    }
                }
            })
        }
    }

    /**
     * Updates the icon color
     * @param tab Tab The tab instance
     * @param color Int The color value
     */
    @Suppress("DEPRECATION")
    private fun updateIconColor(tab: Tab, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            tab.icon?.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            tab.icon?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }
    }
}