package github.com.vikramezhil.dvu.views.flipper

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import com.google.android.material.tabs.TabLayout
import github.com.vikramezhil.dvu.R
import github.com.vikramezhil.dvu.databinding.LayoutDvufvBinding
import kotlinx.android.synthetic.main.layout_dvufv.view.*

/**
 * Droid View Utils - Flipper View Pager
 * @author vikramezhil
 */

class DvuFvPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0): FrameLayout(context, attrs, defStyleAttr) {

    private var binding: LayoutDvufvBinding

    var dvuFlipperView: DvuFlipperView? = null
        set(value) {
            field = value
            setupPageIndicator()
        }

    var onDvuFvListener: OnDvuFvListener? = null

    val properties = object: DvuFvProps() {
        override var activePageIndicatorBgColor: Int = Color.BLACK
            set(value) {
                field = value
                invalidate()
            }

        override var inActivePageIndicatorBgColor: Int = Color.GRAY
            set(value) {
                field = value
                invalidate()
            }
    }

    init {
        val layoutInflater = LayoutInflater.from(context)
        layoutInflater.inflate(R.layout.layout_dvufv, this, false)
        binding = LayoutDvufvBinding.inflate(layoutInflater)

        init(context, attrs)
    }

    /**
     * Initializes the view attributes
     * @param context Context The view context
     * @param attrs AttributeSet The view attributes
     */
    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs == null) return

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.DvuFlipperView, 0, 0)

        try {
            properties.activePageIndicatorBgColor = typedArray.getInt(R.styleable.DvuFlipperView_dvuFvActivePageIndicatorBgColor, properties.activePageIndicatorBgColor)
            properties.inActivePageIndicatorBgColor = typedArray.getInt(R.styleable.DvuFlipperView_dvuFvInactivePageIndicatorBgColor, properties.inActivePageIndicatorBgColor)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            typedArray.recycle()
        }
    }

    /**
     * Sets up the page indicator
     */
    private fun setupPageIndicator() {
        dvuFlipperView?.let { dvuFlipperView ->
            tl_dvu_fv.setupWithViewPager(dvuFlipperView, true)
            for (index in 0..tl_dvu_fv.childCount) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tl_dvu_fv.getTabAt(index)?.icon = resources.getDrawable(R.drawable.ic_dvu_page_selector, context.theme)
                } else {
                    @Suppress("DEPRECATION")
                    tl_dvu_fv.getTabAt(index)?.icon = resources.getDrawable(R.drawable.ic_dvu_page_selector)
                }
            }

            @Suppress("DEPRECATION")
            tl_dvu_fv.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {}

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    tab?.let {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            it.icon?.colorFilter = BlendModeColorFilter(properties.inActivePageIndicatorBgColor, BlendMode.SRC_ATOP)
                        } else {
                            it.icon?.setColorFilter(properties.inActivePageIndicatorBgColor, PorterDuff.Mode.SRC_IN)
                        }
                    }
                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let {
                        onDvuFvListener?.onPageSelected(it.position)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            it.icon?.colorFilter = BlendModeColorFilter(properties.activePageIndicatorBgColor, BlendMode.SRC_ATOP)
                        } else {
                            it.icon?.setColorFilter(properties.activePageIndicatorBgColor, PorterDuff.Mode.SRC_IN)
                        }
                    }
                }
            })

            requestLayout()
        }
    }
}