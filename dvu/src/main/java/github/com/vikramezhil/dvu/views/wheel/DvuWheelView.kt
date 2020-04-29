package github.com.vikramezhil.dvu.views.wheel

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout.HORIZONTAL
import android.widget.LinearLayout.VERTICAL
import androidx.annotation.AttrRes
import github.com.vikramezhil.dvu.R
import github.com.vikramezhil.dvu.utils.DvuScreenUtils
import kotlinx.android.synthetic.main.layout_dvuwv.view.*

/**
 * Droid View Utils - Wheel View
 * @author vikramezhil
 */

class DvuWheelView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0): FrameLayout(context, attrs, defStyleAttr) {

    private var dvuWheelProps = object: DvuWvProps() {
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

        override var scrolling: Boolean = false

        override var itemTxtSize: Float = 22f

        override var selectedItemTxtAlpha: Float = 1f

        override var unselectedItemTxtAlpha: Float = 1f

        override var height: Int = DvuScreenUtils.dpToPx(context, 300)

        override var vPadding: Int = DvuScreenUtils.dpToPx(context, 5)

        override var hPadding: Int = DvuScreenUtils.dpToPx(context, 75)

        override var itemTextStyle: Int = 0

        override var selectedItemPos: Int = 0

        override var orientation: Int = HORIZONTAL

        override var initialScrollDelay: Long = 300
    }

    private var adapter: DvuWvAdapter? = null
    private var listener: OnDvuWvListener? = null

    init {
        View.inflate(context, R.layout.layout_dvuwv, this)

        minimumHeight = dvuWheelProps.height

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
                dvuWheelProps.itemsList = typedArray.resources.getStringArray(items).toCollection(ArrayList())
            }

            dvuWheelProps.itemTextStyle = typedArray.getResourceId(R.styleable.DvuWheelView_dvuWvItemTxtStyle, dvuWheelProps.itemTextStyle)

            dvuWheelProps.bgColor = typedArray.getInt(R.styleable.DvuWheelView_dvuWvBgColor, Color.BLACK)

            dvuWheelProps.selectedItemBgColor = typedArray.getInt(R.styleable.DvuWheelView_dvuWvSelectedItemBgColor, dvuWheelProps.bgColor)
            dvuWheelProps.selectedItemTxtColor = typedArray.getInt(R.styleable.DvuWheelView_dvuWvSelectedItemTxtColor, Color.WHITE)

            dvuWheelProps.unselectedItemBgColor = typedArray.getInt(R.styleable.DvuWheelView_dvuWvUnselectedItemBgColor, dvuWheelProps.bgColor)
            dvuWheelProps.unselectedItemTxtColor = typedArray.getInt(R.styleable.DvuWheelView_dvuWvUnselectedItemTxtColor, Color.WHITE)

            dvuWheelProps.dividerColor = typedArray.getInt(R.styleable.DvuWheelView_dvuWvDividerColor, Color.WHITE)
            dvuWheelProps.dividerColor = typedArray.getInt(R.styleable.DvuWheelView_dvuWvDividerColor, Color.WHITE)
            dvuWheelProps.dividerColor = typedArray.getInt(R.styleable.DvuWheelView_dvuWvDividerColor, Color.WHITE)

            dvuWheelProps.showDivider = typedArray.getBoolean(R.styleable.DvuWheelView_dvuWvShowDivider, true)
            dvuWheelProps.itemTxtBold = typedArray.getBoolean(R.styleable.DvuWheelView_dvuWvItemTxtBold, false)
            dvuWheelProps.itemTxtItalic = typedArray.getBoolean(R.styleable.DvuWheelView_dvuWvItemTxtItalic, false)
            dvuWheelProps.infiniteScrolling = typedArray.getBoolean(R.styleable.DvuWheelView_dvuWvInfiniteScrolling, false)
            dvuWheelProps.orientation = if(typedArray.getBoolean(R.styleable.DvuWheelView_dvuWvOrientationVertical, false)) {
                VERTICAL
            } else {
                HORIZONTAL
            }

            dvuWheelProps.itemTxtSize = typedArray.getFloat(R.styleable.DvuWheelView_dvuWvItemTxtSize, dvuWheelProps.itemTxtSize)
            dvuWheelProps.unselectedItemTxtAlpha = typedArray.getFloat(R.styleable.DvuWheelView_dvuWvUnselectedItemTxtAlpha, dvuWheelProps.unselectedItemTxtAlpha)

            dvuWheelProps.selectedItemPos = typedArray.getInt(R.styleable.DvuWheelView_dvuWvDefaultSelectedItemPos, 0)
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

        dvuWheelProps.height = h
        if (dvuWheelProps.orientation == VERTICAL) {
            dvuWheelProps.vPadding = (h/2) - dvuWheelProps.vPadding
            dvu_wheel.setPadding(0, dvuWheelProps.vPadding, 0, dvuWheelProps.vPadding)
        } else {
            dvuWheelProps.hPadding = (w/2) - dvuWheelProps.hPadding
            dvu_wheel.setPadding(dvuWheelProps.hPadding, 0, dvuWheelProps.hPadding, 0)
        }

        adapter?.update(dvuWheelProps)
    }

    /**
     * Initializes the wheel view
     */
    private fun initWheelView() {
        // Setting the background color
        dvu_wheel.setBackgroundColor(dvuWheelProps.bgColor)

        // Wheel Adapter
        adapter = DvuWvAdapter(context, dvuWheelProps, object: OnDvuWvListener {
            override fun onItemSelected(position: Int, value: String) {
                dvuWheelProps.scrolling = false
                dvuWheelProps.selectedItemPos = position

                // Scrolling to the selected item position
                dvu_wheel.smoothScrollToPosition(position)

                // Sending an update when an item is selected
                listener?.onItemSelected(position, dvuWheelProps.itemsList[position])
            }

            override fun onScrolling() {
                // Sending an update when the wheel is scrolling
                listener?.onScrolling()
            }
        })

        // Layout manager
        dvu_wheel.layoutManager = DvuWvSliderManager(context, dvuWheelProps.orientation, object: OnDvuWvListener {
            override fun onItemSelected(position: Int, value: String) {
                dvuWheelProps.scrolling = false
                dvuWheelProps.selectedItemPos = position
                adapter?.update(dvuWheelProps)

                // Sending an update when an item is selected
                listener?.onItemSelected(position, dvuWheelProps.itemsList[position])
            }

            override fun onScrolling() {
                dvuWheelProps.scrolling = true
                adapter?.update(dvuWheelProps)

                // Sending an update when the wheel is scrolling
                listener?.onScrolling()
            }
        })

        // Setting the adapter
        dvu_wheel.adapter = adapter

        // Refreshing the wheel
        refreshWheel(dvuWheelProps.selectedItemPos)
    }

    /**
     * Refreshing the wheel
     * @param position Int The scroll position
     */
    private fun refreshWheel(position: Int) {
        val prevPosition = dvuWheelProps.selectedItemPos
        if (dvuWheelProps.itemsList.size > 0) {
            if (position < 0 || position >= dvuWheelProps.itemsList.size) {
                dvuWheelProps.selectedItemPos = 0
            } else {
                dvuWheelProps.selectedItemPos = position
            }

            // Finding initial scroll to position to prevent long animation when there are
            // lot of items in the list during smooth scroll
            val scrollToPosition = when {
                dvuWheelProps.selectedItemPos > prevPosition -> {
                    // Scroll down position
                    dvuWheelProps.selectedItemPos - 1
                }
                dvuWheelProps.selectedItemPos < prevPosition -> {
                    // Scroll up position
                    dvuWheelProps.selectedItemPos + 1
                }
                else -> {
                    dvuWheelProps.selectedItemPos
                }
            }

            if (scrollToPosition >= 0 &&  scrollToPosition < dvuWheelProps.itemsList.size) {
                dvu_wheel.scrollToPosition(scrollToPosition)
            }

            dvu_wheel.smoothScrollToPosition(dvuWheelProps.selectedItemPos)

            dvuWheelProps.enableItemHighlight = true
            adapter?.update(dvuWheelProps)
        }
    }

    /**
     * Sets the wheel listener
     * @param listener OnDvuWvListener The class which initializes wheel view listener
     */
    fun setOnDvuWvWheelListener(listener: OnDvuWvListener) {
        this.listener = listener
    }

    /**
     * Sets the wheel items
     * @param itemsList ArrayList<String> The items list
     */
    fun setWheelItems(itemsList: ArrayList<String>) {
        dvuWheelProps.itemsList = itemsList
        refreshWheel(0)
    }

    /**
     * Sets the wheel items
     * @param itemsList ArrayList<String> The items list
     * @param scrollToPos Int The scroll to position
     */
    fun setWheelItems(itemsList: ArrayList<String>, scrollToPos: Int) {
        dvuWheelProps.itemsList = itemsList
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
        refreshWheel(dvuWheelProps.itemsList.indexOf(itemName))
    }

    /**
     * Gets the current selected wheel item
     * @return String? The current selected wheel item
     */
    fun getCurrentSelectedWheelItem(): String? {
        return if (dvuWheelProps.selectedItemPos >= 0 && dvuWheelProps.selectedItemPos < dvuWheelProps.itemsList.size) {
            dvuWheelProps.itemsList[dvuWheelProps.selectedItemPos]
        } else {
            null
        }
    }

    /**
     * Gets the current selected wheel item position
     * @return Int The current selected wheel item position
     */
    fun getCurrentSelectedWheelItemPosition(): Int {
        return dvuWheelProps.selectedItemPos
    }
}