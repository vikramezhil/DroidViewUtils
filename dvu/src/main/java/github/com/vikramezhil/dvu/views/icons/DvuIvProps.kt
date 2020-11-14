package github.com.vikramezhil.dvu.views.icons

import android.content.Context
import android.graphics.Typeface
import java.util.*

/**
 * Droid View Utils - Icons View Props
 * @author vikramezhil
 */

abstract class DvuIvProps {

    private val faFontBrands = "fa-brands-400.ttf"
    private val faFontSolid = "fa-solid-900.ttf"
    private val faFontRegular = "fa-regular-400.ttf"

    private val hashtable: Hashtable<String, Typeface> = Hashtable()

    abstract var isBrandsIcon: Boolean

    abstract var isSolidIcon: Boolean

    abstract var isRegularIcon: Boolean

    /**
     * Gets the brands typeface reference
     * @param context The view context
     * @return The brands typeface reference
     */
    fun getBrandsTypefaceReference(context: Context): Typeface? {
        return if (hashtable.contains(faFontBrands)) {
            hashtable[faFontBrands]
        } else {
            try {
                hashtable[faFontBrands] = Typeface.createFromAsset(context.assets, faFontBrands)
                hashtable[faFontBrands]
            } catch (e: Exception) {
                e.printStackTrace()

                null
            }
        }
    }

    /**
     * Gets the solid typeface reference
     * @param context The view context
     * @return The solid typeface reference
     */
    fun getSolidTypefaceReference(context: Context): Typeface?  {
        return if (hashtable.contains(faFontSolid)) {
            hashtable[faFontSolid]
        } else {
            try {
                hashtable[faFontSolid] = Typeface.createFromAsset(context.assets, faFontSolid)
                hashtable[faFontSolid]
            } catch (e: Exception) {
                e.printStackTrace()

                null
            }
        }
    }

    /**
     * Gets the regular typeface reference
     * @param context The view context
     * @return The regular typeface reference
     */
    fun getRegularTypefaceReference(context: Context): Typeface?  {
        return if (hashtable.contains(faFontRegular)) {
            hashtable[faFontRegular]
        } else {
            try {
                hashtable[faFontRegular] = Typeface.createFromAsset(context.assets, faFontRegular)
                hashtable[faFontRegular]
            } catch (e: Exception) {
                e.printStackTrace()

                null
            }
        }
    }
}