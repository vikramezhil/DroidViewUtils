package github.com.vikramezhil.dvu.views.wheel

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.LinearLayout.VERTICAL
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import github.com.vikramezhil.dvu.R

/**
 * Droid View Utils - Wheel View Adapter
 * @author vikramezhil
 */

class DvuWvAdapter(private var context: Context, private var props: DvuWvProps, private val listener: OnDvuWvListener): RecyclerView.Adapter<DvuWvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = if (props.orientation == VERTICAL) {
            R.layout.layout_dvuwv_vertical_item
        } else {
            R.layout.layout_dvuwv_horizontal_item
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return props.itemsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemSelected = (position == props.selectedItemPos && props.enableItemHighlight && !props.scrolling)

        holder.llPicker.tag = position
        holder.llPicker.setBackgroundColor(if (itemSelected) props.selectedItemBgColor else props.unselectedItemBgColor)
        holder.llPicker.setOnClickListener {
            listener.onItemSelected(it.tag as Int, props.itemsList[position])
        }

        holder.topBorder.visibility = if (itemSelected && props.showDivider) View.VISIBLE else View.INVISIBLE
        holder.topBorder.setBackgroundColor(props.dividerColor)

        holder.bottomBorder.visibility = if (itemSelected && props.showDivider) View.VISIBLE else View.INVISIBLE
        holder.bottomBorder.setBackgroundColor(props.dividerColor)

        val params = holder.itemVal.layoutParams
        if (props.orientation == VERTICAL) {
            params.width = props.height
        } else {
            params.height = props.height
        }
        holder.itemVal.layoutParams = params
        holder.itemVal.text = props.itemsList[position]

        holder.itemVal.setTextColor(if (itemSelected) props.selectedItemTxtColor else props.unselectedItemTxtColor)
        holder.itemVal.alpha = if (itemSelected) props.selectedItemTxtAlpha else props.unselectedItemTxtAlpha
        holder.itemVal.textSize = props.itemTxtSize
        if (props.itemTxtBold && !props.itemTxtItalic) holder.itemVal.setTypeface(holder.itemVal.typeface, Typeface.BOLD)
        if (props.itemTxtItalic && !props.itemTxtBold) holder.itemVal.setTypeface(holder.itemVal.typeface, Typeface.ITALIC)
        if (props.itemTxtBold && props.itemTxtItalic) holder.itemVal.setTypeface(holder.itemVal.typeface, Typeface.BOLD_ITALIC)
        if (props.itemTextStyle != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.itemVal.setTextAppearance(props.itemTextStyle)
            } else {
                @Suppress("DEPRECATION")
                holder.itemVal.setTextAppearance(context, props.itemTextStyle)
            }
        }
    }

    /**
     * Updates the wheel adapter
     * @param props DvuWheelProperties The wheel view properties
     */
    fun update(props: DvuWvProps) {
        this.props = props
        notifyDataSetChanged()
    }

    /**
     * View holder class
     */
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var llPicker: LinearLayout = itemView.findViewById(R.id.ll_dvu_wv_picker)
        val topBorder: View = itemView.findViewById(R.id.v_dvu_wv_top_border)
        val bottomBorder: View = itemView.findViewById(R.id.v_dvu_wv_bottom_border)
        val itemVal: TextView = itemView.findViewById(R.id.tv_dvu_wv_item_val)
    }
}