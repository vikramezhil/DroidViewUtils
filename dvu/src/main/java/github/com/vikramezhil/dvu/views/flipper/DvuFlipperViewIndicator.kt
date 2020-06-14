package github.com.vikramezhil.dvu.views.flipper

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.AttrRes
import com.google.android.material.tabs.TabLayout
import github.com.vikramezhil.dvu.R

class DvuFlipperViewIndicator @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0): TabLayout(context, attrs, defStyleAttr) {

    var onDvuFvListener: OnDvuFvListener? = null

    /**
     * Sets the indicator for the view pager
     * @param dvuFvPager DvuFlipperView? The flipper view instance
     * @param activeColor Int The active indicator color
     * @param inActiveColor Int The inactive indicator color
     */
    fun setupIndicator(dvuFvPager: DvuFlipperView?, activeColor: Int, inActiveColor: Int) {
        if (tabCount == 0) {
            // Connecting the view pager with the tab layout
            setupWithViewPager(dvuFvPager, true)

            // Adding the indicator icon for each tab
            for(index in 0 until tabCount) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getTabAt(index)?.icon = resources.getDrawable(R.drawable.ic_dvu_page_indicator, context.theme)
                } else {
                    @Suppress("DEPRECATION")
                    getTabAt(index)?.icon = resources.getDrawable(R.drawable.ic_dvu_page_indicator)
                }
            }

            @Suppress("DEPRECATION")
            addOnTabSelectedListener(object: OnTabSelectedListener {
                override fun onTabReselected(tab: Tab?) {}

                override fun onTabUnselected(tab: Tab?) {
                    tab?.let {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            it.icon?.setColorFilter(BlendModeColorFilter(inActiveColor, BlendMode.SRC_ATOP))
                        } else {
                            it.icon?.setColorFilter(inActiveColor, PorterDuff.Mode.SRC_IN)
                        }
                    }
                }

                override fun onTabSelected(tab: Tab?) {
                    tab?.let {
                        onDvuFvListener?.onPageSelected(it.position)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            it.icon?.setColorFilter(BlendModeColorFilter(activeColor, BlendMode.SRC_ATOP))
                        } else {
                            it.icon?.setColorFilter(activeColor, PorterDuff.Mode.SRC_IN)
                        }
                    }
                }
            })
        }
    }
}