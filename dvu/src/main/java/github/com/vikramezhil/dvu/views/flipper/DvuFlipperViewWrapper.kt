package github.com.vikramezhil.dvu.views.flipper

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.annotation.AttrRes
import androidx.core.view.children
import github.com.vikramezhil.dvu.R
import github.com.vikramezhil.dvu.databinding.LayoutDvufvBinding

/**
 * Droid View Utils - Flipper View
 * @author vikramezhil
 */

class DvuFlipperViewWrapper @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0): RelativeLayout(context, attrs, defStyleAttr) {

    private var binding: LayoutDvufvBinding? = null

    private var dvuFlipperView: DvuFlipperView? = null

    private var dvuFlipperViewIndicator: DvuFlipperViewIndicator? = null

    private val properties = object: DvuFvProps() {
        override var indicatorIcon: Drawable = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            resources.getDrawable(R.drawable.ic_dvu_page_indicator, context.theme)
        } else {
            @Suppress("DEPRECATION")
            resources.getDrawable(R.drawable.ic_dvu_page_indicator)
        }

        override var activePageIndicatorBgColor: Int = Color.BLACK
        override var inActivePageIndicatorBgColor: Int = Color.GRAY
    }

    var onDvuFvListener: OnDvuFvListener? = null
        set(value) {
            field = value
            dvuFlipperViewIndicator?.onDvuFvListener = value
        }

    init {
        val layoutInflater = LayoutInflater.from(context)
        layoutInflater.inflate(R.layout.layout_dvufv, null)
        binding = LayoutDvufvBinding.inflate(layoutInflater)

        init(context, attrs)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        children.forEach {
            if (it is DvuFlipperView) {
                dvuFlipperView = it
            } else if (it is DvuFlipperViewIndicator) {
                dvuFlipperViewIndicator = it
            }
        }

        // Setting up the page indicator
        dvuFlipperViewIndicator?.setupIndicator(dvuFlipperView, properties.activePageIndicatorBgColor, properties.inActivePageIndicatorBgColor)
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
            val indicatorIcon = typedArray.getDrawable(R.styleable.DvuFlipperView_dvuFvIndicatorIcon)
            if (indicatorIcon != null) { properties.indicatorIcon = indicatorIcon }

            properties.activePageIndicatorBgColor = typedArray.getInt(R.styleable.DvuFlipperView_dvuFvActivePageIndicatorBgColor, properties.activePageIndicatorBgColor)
            properties.inActivePageIndicatorBgColor = typedArray.getInt(R.styleable.DvuFlipperView_dvuFvInactivePageIndicatorBgColor, properties.inActivePageIndicatorBgColor)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            typedArray.recycle()
        }
    }
}