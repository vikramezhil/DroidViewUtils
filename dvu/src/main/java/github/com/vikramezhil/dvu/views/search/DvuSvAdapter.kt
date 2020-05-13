package github.com.vikramezhil.dvu.views.search

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import github.com.vikramezhil.dvu.R
import java.util.*
import kotlin.collections.ArrayList

/**
 * Droid View Utils - Search View Suggestions Adapter
 * @author vikramezhil
 */

class DvuSvAdapter(private var context: Context, private var props: DvuSvProps, private var listener: OnDvuSvItemListener): RecyclerView.Adapter<DvuSvAdapter.ViewHolder>() {

    private var defaultItems: ArrayList<DvuSvItem> = ArrayList()
    private var items: ArrayList<DvuSvItem> = ArrayList()
    private var filterItems: ArrayList<DvuSvItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_dvusv_suggestion_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filterItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.card.tag = position
        holder.card.setCardBackgroundColor(props.bgColor)
        holder.card.setOnClickListener {
            // Sending an update when a suggestion item is clicked
            listener.onSuggestionItemClicked(filterItems[it.tag as Int])
        }

        holder.leftIcon.alpha = props.suggestionItemIconAlpha
        holder.leftIcon.visibility = if (filterItems[position].leftIcon != null) {
            holder.leftIcon.setImageResource(filterItems[position].leftIcon!!)
            View.VISIBLE
        } else {
            View.GONE
        }

        holder.rightIcon.alpha = props.suggestionItemIconAlpha
        holder.rightIcon.visibility = if (filterItems[position].rightIcon != null) {
            holder.rightIcon.setImageResource(filterItems[position].rightIcon!!)
            View.VISIBLE
        } else {
            View.GONE
        }

        holder.title.text = filterItems[position].title
        holder.title.setTextColor(props.textColor)

        holder.subTitle.visibility = if (filterItems[position].subTitle?.isNotEmpty() == true) {
            holder.subTitle.text = filterItems[position].subTitle
            holder.subTitle.setTextColor(props.textColor)
            View.VISIBLE
        } else {
            View.GONE
        }

        if (props.suggestionItemHeight != 0) {
            val params = holder.card.layoutParams
            params.height = props.suggestionItemHeight
            holder.card.layoutParams = params
        }

        @Suppress("DEPRECATION")
        if (props.suggestionTitleTextStyle != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.title.setTextAppearance(props.suggestionTitleTextStyle)
            } else {
                holder.title.setTextAppearance(context, props.suggestionTitleTextStyle)
            }
        }

        @Suppress("DEPRECATION")
        if (props.suggestionSubTitleTextStyle != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.subTitle.setTextAppearance(props.suggestionSubTitleTextStyle)
            } else {
                holder.subTitle.setTextAppearance(context, props.suggestionSubTitleTextStyle)
            }
        }

        holder.divider.setBackgroundColor(props.dividerColor)
        holder.divider.visibility = if (position == filterItems.size - 1) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
    }

    /**
     * Refreshes the items
     * @param props DvuSvProps The item properties
     */
    fun refreshItems(props: DvuSvProps) {
        this.props = props
        notifyDataSetChanged()
    }

    /**
     * Updates the search view default suggestions items
     * @param currActiveItem String The current active item
     * @param items ArrayList<DvuSvItem>? The items list
     */
    fun updateDefaultItems(currActiveItem: String, items: ArrayList<DvuSvItem>?) {
        this.defaultItems = items ?: ArrayList()
        filterItems(currActiveItem, true)
    }

    /**
     * Updates the search view suggestions items
     * @param currActiveItem String The current active item
     * @param items ArrayList<DvuSvItem>? The items list
     */
    fun updateItems(currActiveItem: String, items: ArrayList<DvuSvItem>?) {
        this.items = items ?: ArrayList()
        filterItems(currActiveItem, true)
    }

    /**
     * Filters the items based on current active item
     * @param currActiveItem String The current active item
     * @param showDefaultItems The show default items status
     */
    fun filterItems(currActiveItem: String, showDefaultItems: Boolean) {
        filterItems.clear()
        if (currActiveItem.isNotEmpty()) {
            val finalItems: ArrayList<DvuSvItem> = ArrayList()
            finalItems.addAll(defaultItems)
            finalItems.addAll(items)

            finalItems.forEach {
                if (it.title.toLowerCase(Locale.ROOT).contains(currActiveItem.toLowerCase(Locale.ROOT))) {
                    filterItems.add(it)
                }
            }
        } else if (defaultItems.size > 0 && showDefaultItems) {
            filterItems.addAll(defaultItems)
        }

        // Sending an update on the suggestions found status
        listener.onSuggestionsFound(filterItems.size > 0)

        notifyDataSetChanged()
    }

    /**
     * View holder class
     */
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val card: CardView = itemView.findViewById(R.id.cv_dvu_sv_suggestions_card)
        val leftIcon: ImageView = itemView.findViewById(R.id.iv_dvu_sv_suggestion_left_icon)
        val rightIcon: ImageView = itemView.findViewById(R.id.iv_dvu_sv_suggestion_right_icon)
        val title: TextView = itemView.findViewById(R.id.tv_dvu_sv_suggestion_title)
        val subTitle: TextView = itemView.findViewById(R.id.tv_dvu_sv_suggestion_sub_title)
        val divider: View = itemView.findViewById(R.id.dvu_sv_suggestion_item_divider)
    }
}