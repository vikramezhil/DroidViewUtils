package github.com.vikramezhil.dvu.views.search

import android.graphics.drawable.Drawable

data class DvuSvItem(var leftIcon: Int? = null, var rightIcon: Int? = null, var title: String = "", var subTitle: String? = null)

abstract class DvuSvProps {
    abstract var hintText: String?

    abstract var oldQuery: String?

    abstract var textStyle: Int

    abstract var suggestionTitleTextStyle: Int

    abstract var suggestionSubTitleTextStyle: Int

    abstract var bgColor: Int

    abstract var overlayBgColor: Int

    abstract var textColor: Int

    abstract var hintTextColor: Int

    abstract var dividerColor: Int

    abstract var placeHolderIcon: Drawable?

    abstract var closeIcon: Drawable?

    abstract var clearIcon: Drawable?

    abstract var micIcon: Drawable?

    abstract var actionIcon: Drawable?

    abstract var searchViewHeight: Int

    abstract var suggestionItemHeight: Int

    abstract var margin: Int

    abstract var cornerRadius: Float

    abstract var elevation: Float

    abstract var searchViewAlpha: Float

    abstract var overlayAlpha: Float

    abstract var continuousSearch: Boolean

    abstract var closeOnOverlayTouch: Boolean

    abstract var showMicIcon: Boolean

    abstract var showActionIcon: Boolean
}