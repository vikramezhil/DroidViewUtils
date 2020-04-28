package github.com.vikramezhil.droidviewutils

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import github.com.vikramezhil.dvu.views.wheel.OnDvuWvListener
import kotlinx.android.synthetic.main.activity_wheel_view.*
import java.util.*

/**
 * Wheel picker activity
 * @author vikramezhil
 */

class WheelPickerActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_wheel_view)

        wv_horizontal.setOnDvuWvWheelListener(object: OnDvuWvListener {
            override fun onItemSelected(position: Int, value: String) {
                val liveValue = "Horizontal wheel - $value"
                tv_horizontal_val.text = liveValue
            }

            override fun onScrolling() {
                Log.d(application.packageName, "wv_horizontal is scrolling")
            }
        })
        wv_horizontal.setWheelItems(resources.getStringArray(R.array.dvu_wheel_view_example_items).toCollection(ArrayList()))
        wv_horizontal.scrollToPosition(3)

        wv_vertical.setOnDvuWvWheelListener(object: OnDvuWvListener {
            override fun onItemSelected(position: Int, value: String) {
                val liveValue = "Vertical wheel - $value"
                tv_vertical_val.text = liveValue
            }

            override fun onScrolling() {
                Log.d(application.packageName, "wv_vertical is scrolling")
            }
        })
    }
}