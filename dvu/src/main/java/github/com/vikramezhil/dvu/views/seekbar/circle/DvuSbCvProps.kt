package github.com.vikramezhil.dvu.views.seekbar.circle

import android.graphics.drawable.Drawable

/**
 * Droid View Utils - Seek Bar Circle View Props
 * @author vikramezhil
 */

abstract class DvuSbCvProps {

    abstract var progress: Int

    abstract var min: Int

    abstract var max: Int

    abstract var step: Int

    abstract var thumbIcon: Drawable

    abstract var progressColor: Int

    abstract var progressWidth: Int

    abstract var arcColor: Int

    abstract var arcWidth: Int

    abstract var textStyle: Int
}