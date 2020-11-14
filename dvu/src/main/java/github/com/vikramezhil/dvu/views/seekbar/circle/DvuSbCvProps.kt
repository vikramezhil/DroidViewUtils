package github.com.vikramezhil.dvu.views.seekbar.circle

/**
 * Droid View Utils - Seek Bar Circle View Props
 * @author vikramezhil
 */

abstract class DvuSbCvProps {

    abstract var units: String?

    abstract var separator: String?

    abstract var progress: Float

    abstract var min: Float

    abstract var max: Float

    abstract var progressColor: Int

    abstract var exceededColor: Int

    abstract var thickness: Float

    abstract var enableClick: Boolean
}