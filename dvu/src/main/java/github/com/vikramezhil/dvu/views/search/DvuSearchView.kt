package github.com.vikramezhil.dvu.views.search

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import github.com.vikramezhil.dvu.R
import github.com.vikramezhil.dvu.databinding.LayoutDvusvBinding
import github.com.vikramezhil.dvu.utils.DvuScreenUtils
import github.com.vikramezhil.dvu.utils.getPlaceHolderIcon
import github.com.vikramezhil.dvu.utils.isAcceptingText
import github.com.vikramezhil.dvu.views.edittext.OnDvuEtListener
import kotlinx.android.synthetic.main.layout_dvusv.view.*

/**
 * Droid View Utils - Search View
 * @author vikramezhil
 */

class DvuSearchView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0): FrameLayout(context, attrs, defStyleAttr) {

    private var binding: LayoutDvusvBinding
    private var suggestionsAdapter: DvuSvAdapter? = null
    private var oldQuery: String? = null
    private var placeHolderIcon: Drawable? = context.getPlaceHolderIcon()

    var onDvuSvListener: OnDvuSvListener? = null

    val properties = object: DvuSvProps() {
        override var hintText: String? = null
            set(value) {
                field = value
                updateViews()
            }

        override var textStyle: Int = 0
            set(value) {
                field = value
                updateViews()
            }

        override var suggestionTitleTextStyle: Int = 0
            set(value) {
                field = value
                refreshAdapter()
            }

        override var suggestionSubTitleTextStyle: Int = 0
            set(value) {
                field = value
                refreshAdapter()
            }

        override var bgColor: Int = Color.WHITE
            set(value) {
                field = value
                updateViews()
            }

        override var overlayBgColor: Int = Color.WHITE
            set(value) {
                field = value
                updateViews()
            }

        override var textColor: Int = Color.BLACK
            set(value) {
                field = value
                updateViews()
                refreshAdapter()
            }

        override var hintTextColor: Int = Color.BLACK
            set(value) {
                field = value
                updateViews()
            }

        override var dividerColor: Int = Color.BLACK
            set(value) {
                field = value
                refreshAdapter()
            }

        override var closeIcon: Drawable? = placeHolderIcon
            set(value) {
                field = value
                updateViews()
            }

        override var clearIcon: Drawable? = placeHolderIcon
            set(value) {
                field = value
                updateViews()
            }

        override var micIcon: Drawable? = placeHolderIcon
            set(value) {
                field = value
                updateViews()
            }

        override var actionIcon: Drawable? = placeHolderIcon
            set(value) {
                field = value
                updateViews()
            }

        override var searchViewHeight: Int = 0
            set(value) {
                field = value
                updateViews()
            }

        override var suggestionItemHeight: Int = 0
            set(value) {
                field = value
                refreshAdapter()
            }

        override var margin: Int = 0
            set(value) {
                field = value
                updateViews()
            }

        override var cornerRadius: Float = 0f
            set(value) {
                field = value
                updateViews()
            }

        override var elevation: Float = 0f
            set(value) {
                field = value
                updateViews()
            }

        override var searchViewAlpha: Float = 1f
            set(value) {
                field = value
                updateViews()
            }

        override var overlayAlpha: Float = 1f

        override var searchViewIconAlpha: Float = 1f
            set(value) {
                field = value
                updateViews()
            }

        override var suggestionItemIconAlpha: Float = 1f
            set(value) {
                field = value
                refreshAdapter()
            }

        override var oneStepSuggestionClickVerify: Boolean = false

        override var continuousSearch: Boolean = false

        override var closeOnOverlayTouch: Boolean = false

        override var showMicIcon: Boolean = false
            set(value) {
                field = value
                updateViews()
            }

        override var showActionIcon: Boolean = false
            set(value) {
                field = value
                updateViews()
            }

        override var closeKeyboardOnSuggestionsScroll: Boolean = false

        override var defaultSuggestions: ArrayList<DvuSvItem>? = null
            set(value) {
                field = value
                setDefaultSuggestions()
            }

        override var suggestions: ArrayList<DvuSvItem>? = null
            set(value) {
                field = value
                setSuggestions()
            }
    }

    init {
        val layoutInflater = LayoutInflater.from(context)
        layoutInflater.inflate(R.layout.layout_dvusv, this)
        binding = LayoutDvusvBinding.inflate(layoutInflater)

        init(context, attrs)
    }

    /**
     * Initializes the view attributes
     * @param context Context The view context
     * @param attrs AttributeSet The view attributes
     */
    private fun init(context: Context, attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.theme.obtainStyledAttributes(it, R.styleable.DvuSearchView, 0, 0)

            try {
                properties.hintText = typedArray.getString(R.styleable.DvuSearchView_dvuSvHintTxt)

                properties.textStyle = typedArray.getResourceId(R.styleable.DvuSearchView_dvuSvTxtStyle, properties.textStyle)
                properties.suggestionTitleTextStyle = typedArray.getResourceId(R.styleable.DvuSearchView_dvuSvSuggestionTitleTxtStyle, properties.suggestionTitleTextStyle)
                properties.suggestionSubTitleTextStyle = typedArray.getResourceId(R.styleable.DvuSearchView_dvuSvSuggestionSubTitleTxtStyle, properties.suggestionSubTitleTextStyle)
                properties.bgColor = typedArray.getInt(R.styleable.DvuSearchView_dvuSvBgColor, properties.bgColor)
                properties.overlayBgColor = typedArray.getInt(R.styleable.DvuSearchView_dvuSvOverlayBgColor, properties.overlayBgColor)
                properties.textColor = typedArray.getInt(R.styleable.DvuSearchView_dvuSvTxtColor, properties.textColor)
                properties.hintTextColor = typedArray.getInt(R.styleable.DvuSearchView_dvuSvHintTxtColor, properties.hintTextColor)
                properties.dividerColor = typedArray.getInt(R.styleable.DvuSearchView_dvuSvDividerColor, properties.dividerColor)

                val closeIcon = typedArray.getDrawable(R.styleable.DvuSearchView_dvuSvCloseIcon)
                if (closeIcon != null) {
                    properties.closeIcon = closeIcon
                }

                val clearIcon = typedArray.getDrawable(R.styleable.DvuSearchView_dvuSvClearIcon)
                if (clearIcon != null) {
                    properties.clearIcon = clearIcon
                }

                val micIcon = typedArray.getDrawable(R.styleable.DvuSearchView_dvuSvMicIcon)
                if (micIcon != null) {
                    properties.micIcon = micIcon
                }

                val actionIcon = typedArray.getDrawable(R.styleable.DvuSearchView_dvuSvActionIcon)
                if (actionIcon != null) {
                    properties.actionIcon = actionIcon
                }

                properties.searchViewHeight = typedArray.getDimensionPixelSize(R.styleable.DvuSearchView_dvuSvHeight, properties.searchViewHeight)
                properties.suggestionItemHeight = typedArray.getDimensionPixelSize(R.styleable.DvuSearchView_dvuSvSuggestionItemHeight, properties.suggestionItemHeight)
                properties.margin = typedArray.getDimensionPixelSize(R.styleable.DvuSearchView_dvuSvMargin, properties.margin)

                properties.cornerRadius = typedArray.getDimension(R.styleable.DvuSearchView_dvuSvCornerRadius, properties.cornerRadius)
                properties.elevation = typedArray.getDimension(R.styleable.DvuSearchView_dvuSvElevation, properties.elevation)
                properties.searchViewAlpha = typedArray.getFloat(R.styleable.DvuSearchView_dvuSvAlpha, properties.searchViewAlpha)
                properties.overlayAlpha = typedArray.getFloat(R.styleable.DvuSearchView_dvuSvOverlayAlpha, properties.overlayAlpha)
                properties.searchViewIconAlpha = typedArray.getFloat(R.styleable.DvuSearchView_dvuSvIconAlpha, properties.searchViewIconAlpha)
                properties.suggestionItemIconAlpha = typedArray.getFloat(R.styleable.DvuSearchView_dvuSvSuggestionItemIconAlpha, properties.suggestionItemIconAlpha)

                properties.oneStepSuggestionClickVerify = typedArray.getBoolean(R.styleable.DvuSearchView_dvuSvOneStepSuggestionClickVerify, properties.oneStepSuggestionClickVerify)
                properties.continuousSearch = typedArray.getBoolean(R.styleable.DvuSearchView_dvuSvContinuousSearch, properties.continuousSearch)
                properties.closeOnOverlayTouch = typedArray.getBoolean(R.styleable.DvuSearchView_dvuSvCloseOnOverlayTouch, properties.closeOnOverlayTouch)
                properties.showMicIcon = typedArray.getBoolean(R.styleable.DvuSearchView_dvuSvShowMicIcon, properties.showMicIcon)
                properties.showActionIcon = typedArray.getBoolean(R.styleable.DvuSearchView_dvuSvShowActionIcon, properties.showActionIcon)
                properties.closeKeyboardOnSuggestionsScroll = typedArray.getBoolean(R.styleable.DvuSearchView_dvuSvCloseKeyboardOnSuggestionsScroll, properties.closeKeyboardOnSuggestionsScroll)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                typedArray.recycle()
            }
        }

        // Updating the views
        updateViews()

        // Initializing the suggestions adapter
        initSuggestionsAdapter()

        // Initializing the view listeners
        initViewListeners()
    }

    /**
     * Updates the views
     * @param props DvuSvProps The search view properties
     */
    private fun updateViews(props: DvuSvProps = this.properties) {
        // Updating the icons
        iv_dvu_sv_close_search.setImageDrawable(props.closeIcon)
        iv_dvu_sv_close_search.alpha = props.searchViewIconAlpha

        iv_dvu_sv_clear_search.setImageDrawable(props.clearIcon)
        iv_dvu_sv_clear_search.alpha = props.searchViewIconAlpha

        iv_dvu_sv_microphone.setImageDrawable(props.micIcon)
        iv_dvu_sv_microphone.alpha = props.searchViewIconAlpha
        iv_dvu_sv_microphone.visibility = if (props.showMicIcon) View.VISIBLE else View.GONE

        iv_dvu_sv_action.setImageDrawable(props.actionIcon)
        iv_dvu_sv_action.alpha = props.searchViewIconAlpha
        iv_dvu_sv_action.visibility = if (props.showActionIcon) View.VISIBLE else View.GONE

        // Updating the colors
        cv_dvu_sv.setCardBackgroundColor(DvuScreenUtils.colorWithAlpha(props.bgColor, props.searchViewAlpha))
        rv_dvu_sv_suggestion_list.setBackgroundColor(props.bgColor)
        et_dvu_sv_search.setTextColor(props.textColor)
        et_dvu_sv_search.setHintTextColor(props.hintTextColor)

        // Updating the text
        et_dvu_sv_search.hint = props.hintText

        // Updating the text style
        if (props.textStyle != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                et_dvu_sv_search.setTextAppearance(props.textStyle)
            } else {
                @Suppress("DEPRECATION")
                et_dvu_sv_search.setTextAppearance(context, props.textStyle)
            }
        }

        // Setting the dimensions
        if (props.margin != 0) {
            val params = ll_dvu_sv.layoutParams as MarginLayoutParams
            params.setMargins(props.margin, props.margin, props.margin, props.margin)
            ll_dvu_sv.layoutParams = params
        }
        if (props.searchViewHeight != 0) {
            val params = cv_dvu_sv.layoutParams
            params.height = props.searchViewHeight
            cv_dvu_sv.layoutParams = params
        }
        cv_dvu_sv.radius = props.cornerRadius
        cv_dvu_sv.cardElevation = props.elevation
    }

    /**
     * Initializes the suggestion adapter
     */
    private fun initSuggestionsAdapter() {
        suggestionsAdapter = DvuSvAdapter(context, properties, object: OnDvuSvItemListener {
            override fun onSuggestionItemClicked(clickedSuggestionItem: DvuSvItem) {
                // Sending an update on the selected suggestion item
                onDvuSvListener?.onSearchSuggestionItem(clickedSuggestionItem)

                when {
                    properties.oneStepSuggestionClickVerify -> {
                        // Setting the selected item item
                        et_dvu_sv_search.setText(clickedSuggestionItem.title)

                        if (et_dvu_sv_search.text?.isNotEmpty() == true) {
                            // Moving the cursor to the end of the text
                            et_dvu_sv_search.setSelection(et_dvu_sv_search.text!!.length)

                            clearSuggestions(false)
                        }
                    }
                    properties.continuousSearch -> {
                        // Clearing out the existing text
                        et_dvu_sv_search.setText("")
                    }
                    else -> {
                        // Closing search view when a suggestion item is clicked
                        iv_dvu_sv_close_search.performClick()
                    }
                }
            }

            override fun onSuggestionsFound(suggestionsFound: Boolean) {
                rv_dvu_sv_suggestion_list.visibility = if (view_dvu_sv_overlay.visibility == View.VISIBLE && suggestionsFound) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        })

        val layoutManager = LinearLayoutManager(context)
        rv_dvu_sv_suggestion_list.layoutManager = layoutManager
        rv_dvu_sv_suggestion_list.adapter = suggestionsAdapter
    }

    /**
     * Initializes the view listeners
     */
    private fun initViewListeners() {
        et_dvu_sv_search.setOnDvuEtListener(object: OnDvuEtListener {
            override fun onKeyboardClosed() {
                // Closing search view when keyboard closes
                iv_dvu_sv_close_search.performClick()
            }
        })

        et_dvu_sv_search.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                // Sending an update on the searched text
                onDvuSvListener?.onSearchText(et_dvu_sv_search.text.toString())

                if (properties.continuousSearch) {
                    // Clearing out the existing text
                    et_dvu_sv_search.setText("")
                } else {
                    // Closing search view when "enter" is pressed in keyboard
                    iv_dvu_sv_close_search.performClick()
                }

                true
            } else {
                false
            }
        }

        et_dvu_sv_search.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Activating search view when focused
                et_dvu_sv_search.performClick()

                onDvuSvListener?.onHasFocus()
            } else {
                // Closing the search view
                closeSearchView()

                onDvuSvListener?.onLostFocus()
            }
        }

        et_dvu_sv_search.setOnClickListener {
            if (iv_dvu_sv_close_search?.visibility == View.GONE) {
                val slideInRightAnim = AnimationUtils.loadAnimation(context, R.anim.slide_right)
                slideInRightAnim.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {
                        // Making the close view invisible for it to occupy space
                        // during animation start
                        iv_dvu_sv_close_search.visibility = View.INVISIBLE
                    }

                    override fun onAnimationEnd(animation: Animation) {
                        // Clearing out the animation
                        et_dvu_sv_search.clearAnimation()

                        // Showing close and clear views
                        iv_dvu_sv_close_search.visibility = View.VISIBLE

                        // Hiding the mic and action icon
                        iv_dvu_sv_microphone.visibility = View.GONE
                        iv_dvu_sv_action.visibility = View.GONE
                    }

                    override fun onAnimationRepeat(animation: Animation) {}
                })

                et_dvu_sv_search.clearAnimation()
                et_dvu_sv_search.animation = slideInRightAnim

                // Adding the overlay layout to parent
                addOverlayLayout()
            }
        }

        et_dvu_sv_search.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = et_dvu_sv_search.text.toString()
                if (oldQuery?.equals(searchText) == false) {
                    // Sending an update that the search text has changed
                    onDvuSvListener?.onSearchTextChanged(oldQuery, searchText)

                    // Filtering the items
                    suggestionsAdapter?.filterItems(searchText, true)
                } else if (searchText.isEmpty()) {
                    clearSuggestions(true)
                }

                oldQuery = searchText
            }

            override fun afterTextChanged(s: Editable?) {
                iv_dvu_sv_clear_search.visibility = if (s?.isEmpty() == true) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            }
        })

        if (properties.closeKeyboardOnSuggestionsScroll) {
            rv_dvu_sv_suggestion_list.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (context.isAcceptingText()) {
                        DvuScreenUtils.closeViewKeyboard(et_dvu_sv_search, context)
                    }
                }
            })
        }

        iv_dvu_sv_close_search.setOnClickListener {
            // Closing the keyboard
            DvuScreenUtils.closeViewKeyboard(et_dvu_sv_search, context)

            // Hiding close and clear icons and showing the mic and action icons if applicable
            iv_dvu_sv_close_search.visibility = View.GONE
            iv_dvu_sv_clear_search.visibility = View.GONE
            iv_dvu_sv_microphone.visibility = if (properties.showMicIcon) View.VISIBLE else View.GONE
            iv_dvu_sv_action.visibility = if (properties.showActionIcon) View.VISIBLE else View.GONE

            // Removing the overlay layout
            removeOverlayLayout()

            // Clearing the search view text and clearing out the focus
            et_dvu_sv_search.setText("")
            et_dvu_sv_search.clearFocus()

            // Clearing out the suggestions
            clearSuggestions(true)
        }

        iv_dvu_sv_clear_search.setOnClickListener {
            et_dvu_sv_search.setText("")

            // Clearing out the suggestions
            clearSuggestions(true)
        }

        iv_dvu_sv_microphone.setOnClickListener {
            onDvuSvListener?.onMicClicked()
        }

        iv_dvu_sv_action.setOnClickListener {
            onDvuSvListener?.onActionItemClicked()
        }
    }

    /**
     * Refreshes the adapter
     * @param props DvuSvProps The search view properties
     */
    private fun refreshAdapter(props: DvuSvProps = this.properties) {
        suggestionsAdapter?.refreshItems(props)
    }

    /**
     * Adds the overlay layout
     */
    private fun addOverlayLayout() {
        view_dvu_sv_overlay?.setBackgroundColor(properties.overlayBgColor)
        view_dvu_sv_overlay?.alpha = properties.overlayAlpha
        view_dvu_sv_overlay?.visibility = View.VISIBLE
        view_dvu_sv_overlay?.setOnClickListener {
            if (iv_dvu_sv_close_search.visibility == View.VISIBLE && properties.closeOnOverlayTouch) {
                iv_dvu_sv_close_search.performClick()
            }
        }

        // Removing alpha from search view when overlay is added
        cv_dvu_sv.setCardBackgroundColor(DvuScreenUtils.colorWithAlpha(properties.bgColor, 1f))

        // Showing the suggestions item list view
        rv_dvu_sv_suggestion_list.visibility = View.VISIBLE
        suggestionsAdapter?.filterItems(et_dvu_sv_search.text.toString(), true)
    }

    /**
     * Removes the overlay layout
     */
    private fun removeOverlayLayout() {
        view_dvu_sv_overlay?.visibility = View.GONE
        view_dvu_sv_overlay?.setOnClickListener(null)

        // Adding back alpha to search view when overlay is removed
        cv_dvu_sv.setCardBackgroundColor(DvuScreenUtils.colorWithAlpha(properties.bgColor, properties.searchViewAlpha))

        // Hiding the suggestions item list view
        rv_dvu_sv_suggestion_list.visibility = View.GONE
    }

    /**
     * Closes the search if found open
     */
    private fun closeSearchView() {
        // Closing the search view
        if (iv_dvu_sv_close_search.visibility == View.VISIBLE) {
            iv_dvu_sv_close_search.performClick()
        }
    }

    /**
     * Clears out the suggestions
     * @param showDefaultItems The show default items status
     */
    private fun clearSuggestions(showDefaultItems: Boolean) {
        suggestionsAdapter?.filterItems("", showDefaultItems)
    }

    /**
     * Sets the default suggestions
     * @param props DvuSvProps The search view properties
     */
    private fun setDefaultSuggestions(props: DvuSvProps = this.properties) {
        suggestionsAdapter?.updateDefaultItems(et_dvu_sv_search.text.toString(), props.defaultSuggestions)
    }

    /**
     * Sets the suggestions
     * @param props DvuSvProps The search view properties
     */
    private fun setSuggestions(props: DvuSvProps = this.properties) {
        suggestionsAdapter?.updateItems(et_dvu_sv_search.text.toString(), props.suggestions)
    }
}