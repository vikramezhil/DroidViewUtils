package github.com.vikramezhil.dvu.views.seekbar.circle

/**
 * Droid View Utils - Seek Bar Circle View Props
 * @author vikramezhil
 */

abstract class DvuSbCvProps {

    abstract var units: String?

    abstract var separator: String?

    abstract var progress: Int

    abstract var min: Int

    abstract var max: Int

    abstract var progressColor: Int

    abstract var exceededColor: Int

    abstract var thickness: Float

    abstract var enableClick: Boolean
}