package github.com.vikramezhil.dvu.views.flipper

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.core.view.get
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import github.com.vikramezhil.dvu.R

/**
 * Droid View Utils - Flipper View
 * @author vikramezhil
 */

class DvuFlipperView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0): ViewPager(context) {

    private var listener: OnDvuFvListener? = null

    private val properties = object: DvuFvProps() {
        override var autoWrap: Boolean = false
    }

    init {
        init(context, attrs)

        // Listening for page changes
        addOnPageChangeListener(object: OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                listener?.onPageSelected(position)
            }
        })
    }

    /**
     * Initializes the view attributes
     * @param context Context The view context
     * @param attrs AttributeSet The view attributes
     */
    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs == null) return

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.DvuSearchView, 0, 0)

        try {
            properties.autoWrap = typedArray.getBoolean(R.styleable.DvuFlipperView_dvuFvAutoWrap, properties.autoWrap)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            typedArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightSpec = heightMeasureSpec
        if (properties.autoWrap) {
            val mode = MeasureSpec.getMode(heightMeasureSpec)
            if (mode == MeasureSpec.UNSPECIFIED || mode == MeasureSpec.AT_MOST) {
                // calling super in the beginning so the child views can be initialized
                super.onMeasure(widthMeasureSpec, heightMeasureSpec)

                var height = 0
                for (i in 0 until childCount) {
                    val child = getChildAt(i)
                    child.measure(
                        widthMeasureSpec,
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                    )
                    val h = child.measuredHeight
                    if (h > height) height = h
                }

                heightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
            }
        }

        // calling super again so the new specs are treated as exact measurements
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