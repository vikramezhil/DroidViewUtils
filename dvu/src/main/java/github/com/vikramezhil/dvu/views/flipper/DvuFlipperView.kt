package github.com.vikramezhil.dvu.views.flipper

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.core.view.get
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

/**
 * Droid View Utils - Flipper View
 * @author vikramezhil
 */

class DvuFlipperView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0): ViewPager(context) {

    private var listener: OnDvuFvListener? = null

    private val properties = object: DvuFvProps() {
        override var currentViewPosition: Int = 0
            set(value) {
                field = value
                requestLayout()
            }
    }

    init {
        // Listening for page changes
        addOnPageChangeListener(object: OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                properties.currentViewPosition = position

                listener?.onPageSelected(position)
            }
        })
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightSpec = heightMeasureSpec
        try {
            val wrapHeight = MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST
            if (wrapHeight) {
                val child = getChildAt(properties.currentViewPosition)
                if (child != null) {
                    child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
                    val h = child.measuredHeight
                    heightSpec = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        super.onMeasure(widthMeasureSpec, heightSpec)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        offscreenPageLimit = (childCount - 1)
        adapter = object: PagerAdapter() {
            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                return container[position]
            }

            override fun isViewFromObject(view: View, obj: Any): Boolean {
                return view == obj
            }

            override fun getCount(): Int {
                return childCount
            }
        }
    }

    /**
     * Sets the flipper view listener
     * @param listener OnDvuFvListener The class which initializes flipper view listener
     */
    fun setOnDvuFvFlipperListener(listener: OnDvuFvListener) {
        this.listener = listener
    }
}