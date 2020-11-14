package github.com.vikramezhil.dvu.views.icons

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.appcompat.widget.AppCompatTextView
import github.com.vikramezhil.dvu.R

/**
 * Droid View Utils - Icons View
 * @author vikramezhil
 */

class DvuIconsView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0): AppCompatTextView(context, attrs, defStyleAttr) {

    val properties = object: DvuIvProps() {
        override var isBrandsIcon: Boolean = false
            set(value) {
                field = value
                updateViews()
            }
        override var isSolidIcon: Boolean = false
            set(value) {
                field = value
                updateViews()
            }
        override var isRegularIcon: Boolean = false
            set(value) {
                field = value
                updateViews()
            }
    }

    init { init(context, attrs) }

    /**
     * Initializes the view attributes
     * @param context Context The view context
     * @param attrs AttributeSet The view attributes
     */
    private fun init(context: Context, attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.theme.obtainStyledAttributes(it, R.styleable.DvuIconsView, 0, 0)

            try {
                properties.isBrandsIcon = typedArray.getBoolean(R.styleable.DvuIconsView_dvuIvBrandsIcon, false)
                properties.isSolidIcon = typedArray.getBoolean(R.styleable.DvuIconsView_dvuIvSolidIcon, false)
                properties.isRegularIcon = typedArray.getBoolean(R.styleable.DvuIconsView_dvuIvRegularIcon, false)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                typedArray.recycle()
            }

            updateViews()
        }
    }

    /**
     * Updates the views
     * @param props DvuIvProps The icons view properties
     */
    private fun updateViews(props: DvuIvProps = this.properties) {
        when {
            props.isBrandsIcon -> {
                typeface = properties.getBrandsTypefaceReference(context)
            }
            props.isSolidIcon -> {
                typeface = properties.getSolidTypefaceReference(context)
            }
            props.isRegularIcon -> {
                typeface = properties.getRegularTypefaceReference(context)
            }
        }

        invalidate()
    }
}