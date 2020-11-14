package github.com.vikramezhil.dvu.views.wheel

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout.HORIZONTAL
import android.widget.LinearLayout.VERTICAL
import androidx.annotation.AttrRes
import github.com.vikramezhil.dvu.R
import github.com.vikramezhil.dvu.databinding.LayoutDvuwvBinding
import kotlinx.android.synthetic.main.layout_dvuwv.view.*

/**
 * Droid View Utils - Wheel View
 * @author vikramezhil
 */

class DvuWheelView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0): FrameLayout(context, attrs, defStyleAttr) {

    private var binding: LayoutDvuwvBinding
    private var adapter: DvuWvAdapter? = null
    private var properties = object: DvuWvProps() {
        override var itemsList: ArrayList<String> = ArrayList()

        override var bgColor: Int = Color.BLACK

        override var selectedItemBgColor: Int = Color.BLACK

        override var selectedItemTxtColor: Int = Color.WHITE

        override var unselectedItemBgColor: Int = Color.BLACK

        override var unselectedItemTxtColor: Int = Color.WHITE

        override var dividerColor: Int = Color.WHITE

        override var enableItemHighlight: Boolean = false

        override var showDivider: Boolean = true

        override var itemTxtBold: Boolean = false

        override var itemTxtItalic: Boolean = false

        override var infiniteScrolling: Boolean = false

        override var scaleDownEnabled: Boolean = false

        override var scrolling: Boolean = false

        override var itemTxtSize: Float = resources.getDimension(R.dimen.dvu_wv_item_txt_size)

        override var selectedItemTxtAlpha: Float = 1f

        override var unselectedItemTxtAlpha: Float = 1f

        override var height: Int = resources.getDimensionPixelSize(R.dimen.dvu_wv_min_height)

        override var vPadding: Int = resources.getDimensionPixelSize(R.dimen.dvu_wv_v_padding)

        override var hPadding: Int = resources.getDimensionPixelSize(R.dimen.dvu_wv_h_padding)

        override var itemTextStyle: Int = 0

        override var selectedItemPos: Int = 0

        override var orientation: Int = HORIZONTAL
    }

    var onDvuWvListener: OnDvuWvListener? = null

    init {
        val layoutInflater = LayoutInflater.from(context)
        layoutInflater.inflate(R.layout.layout_dvuwv, this)
        binding = LayoutDvuwvBinding.inflate(layoutInflater)

        minimumHeight = properties.height

        init(context, attrs)
    }

    /**
     * Initializes the view attributes
     * @param context Context The view context
     * @param attrs AttributeSet The view attributes
     */
    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs == null) return

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.DvuWheelView, 0, 0)

        try {
            val items = typedArray.getResourceId(R.styleable.DvuWheelView_dvuWvWheelItems, 0)
            if (items != 0) {
                properties.itemsList = typedArray.resources.getStringArray(items).toCollection(ArrayList())
            }

            properties.itemTextStyle = typedArray.getResourceId(R.styleable.DvuWheelView_dvuWvItemTxtStyle, properties.itemTextStyle)

            properties.bgColor = typedArray.getInt(R.styleable.DvuWheelView_dvuWvBgColor, Color.BLACK)

            properties.selectedItemBgColor = typedArray.getInt(R.styleable.DvuWheelView_dvuWvSelectedItemBgColor, properties.bgColor)
            properties.selectedItemTxtColor = typedArray.getInt(R.styleable.DvuWheelView_dvuWvSelectedItemTxtColor, Color.WHITE)

            properties.unselectedItemBgColor = typedArray.getInt(R.styleable.DvuWheelView_dvuWvUnselectedItemBgColor, properties.bgColor)
            properties.unselectedItemTxtColor = typedArray.getInt(R.styleable.DvuWheelView_dvuWvUnselectedItemTxtColor, Color.WHITE)

            properties.dividerColor = typedArray.getInt(R.styleable.DvuWheelView_dvuWvDividerColor, Color.WHITE)
            properties.dividerColor = typedArray.getInt(R.styleable.DvuWheelView_dvuWvDividerColor, Color.WHITE)
            properties.dividerColor = typedArray.getInt(R.styleable.DvuWheelView_dvuWvDividerColor, Color.WHITE)

            properties.showDivider = typedArray.getBoolean(R.styleable.DvuWheelView_dvuWvShowDivider, true)
            properties.itemTxtBold = typedArray.getBoolean(R.styleable.DvuWheelView_dvuWvItemTxtBold, false)
            properties.itemTxtItalic = typedArray.getBoolean(R.styleable.DvuWheelView_dvuWvItemTxtItalic, false)
            properties.infiniteScrolling = typedArray.getBoolean(R.styleable.DvuWheelView_dvuWvInfiniteScrolling, false)
            properties.scaleDownEnabled = typedArray.getBoolean(R.styleable.DvuWheelView_dvuWvScaleDownEnabled, false)

            properties.orientation = if(typedArray.getBoolean(R.styleable.DvuWheelView_dvuWvOrientationVertical, false)) {
                VERTICAL
            } else {
                HORIZONTAL
            }

            properties.itemTxtSize = typedArray.getDimension(R.styleable.DvuWheelView_dvuWvItemTxtSize, properties.itemTxtSize)

            properties.unselectedItemTxtAlpha = typedArray.getFloat(R.styleable.DvuWheelView_dvuWvUnselectedItemTxtAlpha, properties.unselectedItemTxtAlpha)

            properties.selectedItemPos = typedArray.getInt(R.styleable.DvuWheelView_dvuWvDefaultSelectedItemPos, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            typedArray.recycle()
        }

        // Initializing the wheel view
        initWheelView()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        properties.height = h
        if (properties.orientation == VERTICAL) {
            properties.vPadding = (h/2) - properties.vPadding
            dvu_wv.setPadding(0, properties.vPadding, 0, properties.vPadding)
        } else {
            properties.hPadding = (w/2) - properties.hPadding
            dvu_wv.setPadding(properties.hPadding, 0, properties.hPadding, 0)
        }

        adapter?.update(properties)
    }

    /**
     * Initializes the wheel view
     */
    private fun initWheelView() {
        // Setting the background color
        dvu_wv.setBackgroundColor(properties.bgColor)

        // Layout manager
        dvu_wv.layoutManager = DvuWvSliderManager(context, properties.orientation, properties.scaleDownEnabled, object: OnDvuWvListener {
            override fun onItemSelected(position: Int, value: String) {
                properties.scrolling = false

                if (position != properties.selectedItemPos) {
                    properties.selectedItemPos = position

                    // Sending an update when an item is selected
                    onDvuWvListener?.onItemSelected(position, properties.itemsList[position])
                }

                adapter?.update(properties)
            }

            override fun onScrolling() {
                properties.scrolling = true

                adapter?.update(properties)

                // Sending an update when the wheel is scrolling
                onDvuWvListener?.onScrolling()
            }
        })

        // Wheel Adapter
        adapter = DvuWvAdapter(context, properties, object: OnDvuWvListener {
            override fun onItemSelected(position: Int, value: String) {
                properties.scrolling = false

                if (position != properties.selectedItemPos) {
                    properties.selectedItemPos = position

                    // Scrolling to the selected item position
                    dvu_wv.smoothScrollToPosition(position)

                    // Sending an update when an item is selected
                    onDvuWvListener?.onItemSelected(position, properties.itemsList[position])
                }
            }

            override fun onScrolling() {
                // Sending an update when the wheel is scrolling
                onDvuWvListener?.onScrolling()
            }
        })

        // Setting the adapter
        dvu_wv.adapter = adapter

        // Refreshing the wheel
        refreshWheel(properties.selectedItemPos)
    }

    /**
     * Refreshing the wheel
     * @param position Int The scroll position
     */
    private fun refreshWheel(position: Int) {
        val prevPosition = properties.selectedItemPos
        if (properties.itemsList.size > 0) {
            if (position < 0 || position >= properties.itemsList.size) {
                properties.selectedItemPos = 0
            } else {
                properties.selectedItemPos = position
            }

            // Finding initial scroll to position to prevent long animation when there are
            // lot of items in the list during smooth scroll
            val scrollToPosition = when {
                properties.selectedItemPos > prevPosition -> {
                    // Scroll down position
                    properties.selectedItemPos - 1
                }
                properties.selectedItemPos < prevPosition -> {
                    // Scroll up position
                    properties.selectedItemPos + 1
                }
                else -> {
                    properties.selectedItemPos
                }
            }

            if (scrollToPosition >= 0 &&  scrollToPosition < properties.itemsList.size) {
                dvu_wv.scrollToPosition(scrollToPosition)
            }

            dvu_wv.smoothScrollToPosition(properties.selectedItemPos)

            properties.enableItemHighlight = true

            adapter?.update(properties)
        }
    }

    /**
     * Sets the wheel items
     * @param itemsList ArrayList<String> The items list
     */
    fun setWheelItems(itemsList: ArrayList<String>) {
        properties.itemsList = itemsList
        refreshWheel(0)
    }

    /**
     * Sets the wheel items
     * @param itemsList ArrayList<String> The items list
     * @param scrollToPos Int The scroll to position
     */
    fun setWheelItems(itemsList: ArrayList<String>, scrollToPos: Int) {
        properties.itemsList = itemsList
        refreshWheel(scrollToPos)
    }

    /**
     * Scrolls based on the position
     * @param scrollToPos Int The scroll to position
     */
    fun scrollToPosition(scrollToPos: Int) {
        refreshWheel(scrollToPos)
    }

    /**
     * Scrolls based on the item name
     * @param itemName String The scroll to item name
     */
    fun scrollToItem(itemName: String) {
        refreshWheel(properties.itemsList.indexOf(itemName))
    }

    /**
     * Gets the current selected wheel item
     * @return String? The current selected wheel item
     */
    fun getCurrentSelectedWheelItem(): String? {
        return if (properties.selectedItemPos >= 0 && properties.selectedItemPos < properties.itemsList.size) {
            properties.itemsList[properties.selectedItemPos]
        } else {
            null
        }
    }

    /**
     * Gets the current selected wheel item position
     * @return Int The current selected wheel item position
     */
    fun getCurrentSelectedWheelItemPosition(): Int {
        return properties.selectedItemPos
    }
}