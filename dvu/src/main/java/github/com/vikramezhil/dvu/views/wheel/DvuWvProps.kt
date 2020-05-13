package github.com.vikramezhil.dvu.views.wheel

/**
 * Droid View Utils - Wheel View Props
 * @author vikramezhil
 */

abstract class DvuWvProps {
    abstract var itemsList: ArrayList<String>

    abstract var bgColor: Int

    abstract var selectedItemBgColor: Int

    abstract var selectedItemTxtColor: Int

    abstract var unselectedItemBgColor: Int

    abstract var unselectedItemTxtColor: Int

    abstract var dividerColor: Int

    abstract var enableItemHighlight: Boolean

    abstract var showDivider: Boolean

    abstract var itemTxtBold: Boolean

    abstract var itemTxtItalic: Boolean

    abstract var infiniteScrolling: Boolean

    abstract var scaleDownEnabled: Boolean

    abstract var scrolling: Boolean

    abstract var itemTxtSize: Float

    abstract var selectedItemTxtAlpha: Float

    abstract var unselectedItemTxtAlpha: Float

    abstract var height: Int

    abstract var vPadding: Int

    abstract var hPadding: Int

    abstract var itemTextStyle: Int

    abstract var selectedItemPos: Int

    abstract var orientation: Int
}