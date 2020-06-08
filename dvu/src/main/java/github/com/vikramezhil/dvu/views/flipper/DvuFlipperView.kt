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

    init {
        // Listening for page changes
        addOnPageChangeListener(object: OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                listener?.onPageSelected(position)
            }
        })
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        val childrenCount = childCount
        offscreenPageLimit = (childrenCount - 1)
        adapter = object: PagerAdapter() {
            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                return container[position]
            }

            override fun isViewFromObject(view: View, obj: Any): Boolean {
                return view == obj
            }

            override fun getCount(): Int {
                return childrenCount
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